package edu.gatech.seclass.jobcompare6300;

import android.app.Activity;
import android.content.Intent;
import android.icu.number.Precision;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CompareOffersActivity extends AppCompatActivity {

    AppDatabase db;
    // Get ListView object from xml
    Integer count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_offers);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "job-offer-database").allowMainThreadQueries().build();
        JobComparisonAppDao JCAP = db.jobComparisonAppDao();

        ListView listView = (ListView) findViewById(R.id.jobList);
//        TextView mTextView = (TextView) findViewById(R.id.tv);
        Activity mActivity = CompareOffersActivity.this;

//        String[] companies = new String[] {"Company 1", "Company 2", "Company 3"};

        // get list of all jobs
        List<Job> tj = JCAP.getAllJobs();
        List<String> jobTitleCompany = new ArrayList<>();

//        System.out.println("print jobs");
        for (Job j : tj )
        {
//            System.out.println(j.getTitle());
//            System.out.println("show whether current job");
//            System.out.println(j.getIsCurrentJob());

            if (j.getIsCurrentJob()) {
                jobTitleCompany.add("Current Job " + j.getTitle() + "-" + j.getCompany());
            }
            else {
                jobTitleCompany.add(j.getTitle() + "-" + j.getCompany());
            }

//            JCAP.deleteJob(j);
        }

        // calculate job score

        List<ComparisonEditor> check = JCAP.getComparisonSettings();

        Integer teleworkWeight;
        Integer salaryWeight;
        Integer bonusWeight;
        Integer retiremenBenefitsWeight;
        Integer leaveTimeWeight;

        if (check.size() > 0) {

            ComparisonEditor stored = check.get(0);

            teleworkWeight = stored.getRemoteWeight();
            salaryWeight = stored.getSalaryWeight();
            bonusWeight = stored.getBonusWeight();
            retiremenBenefitsWeight = stored.getBenefitsWeight();
            leaveTimeWeight = stored.getLeaveTimeWeight();
        }
        else {
            teleworkWeight = 1;
            salaryWeight = 1;
            bonusWeight = 1;
            retiremenBenefitsWeight = 1;
            leaveTimeWeight = 1;
        }

        HashMap<String, Double> jobScoreHMap = new HashMap<>();
        HashMap<String, Job> jobObjectHMap = new HashMap<>();

        List<Job> allJobs = JCAP.getAllJobs();
        for (Job j : allJobs )
        {
            Integer costOfLiving = j.getCostOfLiving();
            Double AYS = j.getYearlySalary() * (1.0 * 100 / costOfLiving);
            Double AYB = j.getYearlyBonus() * (1.0 * 100 / costOfLiving);
            Double RBP = j.getRetirementBenefits() * 1.0 / 100.0;
            Integer LT = j.getLeaveTime();
            Integer RWT = j.getTeleworkDays();


            Integer denominator = teleworkWeight + salaryWeight + bonusWeight + retiremenBenefitsWeight + leaveTimeWeight;

            Double jobScore = (salaryWeight * 1.0/denominator * AYS)
                            + (bonusWeight * 1.0/denominator * AYB)
                            + (retiremenBenefitsWeight * 1.0/denominator * (RBP * AYS))
                            + (leaveTimeWeight * 1.0/denominator * (LT * AYS / 260))
                            - (teleworkWeight * 1.0/denominator * ((260 - 52 * RWT) * (AYS / 260) / 8));

            // if division by zero occurs
            if (jobScore.isNaN()){
                jobScore = 0.0;
            }

            String key;
            if (j.getIsCurrentJob()) {
                key = "(Current Job) " + j.getTitle() + "-" + j.getCompany();
            }
            else {
                key = j.getTitle() + "-" + j.getCompany();
            }

            jobScoreHMap.put(key, jobScore);
            jobObjectHMap.put(key, j);
        }

        LinkedHashMap<String, Double> jobScoreSorted = new LinkedHashMap<>();

        //Use Comparator.reverseOrder() for reverse ordering: https://howtodoinjava.com/java/sort/java-sort-map-by-values/
        jobScoreHMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> jobScoreSorted.put(x.getKey(), x.getValue()));

        // convert keys to array for display. Android can print array but list required code change
        String[] companies = jobScoreSorted.keySet().toArray(new String[0]);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                mActivity
                , android.R.layout.simple_list_item_multiple_choice
                , companies);

        // Assign adapter to ListView
        listView.setAdapter(adapter);

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        // Set an item click listener for the ListView


        Button compareButton = (Button) findViewById(R.id.compareOffersButton);
        compareButton.setEnabled(false);
        List<Job> jobsToCompare = new ArrayList<Job>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Integer checkedCount = 0;
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SparseBooleanArray clickedItemPositions = listView.getCheckedItemPositions();
                checkedCount = getCheckedItemCount(clickedItemPositions);
                if (checkedCount != 2) {
                    Toast.makeText(mActivity, "Please select only 2 jobs to compare", Toast.LENGTH_LONG).show();
                    compareButton.setEnabled(false);
                }
                else if (checkedCount == 2) {
                    addTwoJobsToCompare(clickedItemPositions);
                }
            }

            public void addTwoJobsToCompare(SparseBooleanArray clickedItemPositions){
                compareButton.setEnabled(true);
                jobsToCompare.clear(); // clear previous selections

                for (int index = 0; index < clickedItemPositions.size(); index++) {

                    // Get the checked status of the current item
                    boolean checked = clickedItemPositions.valueAt(index);

                    if (checked) {
                        // If the current item is checked
                        int key = clickedItemPositions.keyAt(index);
                        String item = (String) listView.getItemAtPosition(key);

                        // add selection to jobsToCompare
                        jobsToCompare.add(jobObjectHMap.get(item));
                    }
                }

                System.out.println(jobsToCompare);
            }

            public Integer getCheckedItemCount(SparseBooleanArray clickedItemPositions){
                Integer checkedCount=0;
                for (int index = 0; index < clickedItemPositions.size(); index++) {

                    // Get the checked status of the current item
                    boolean checked = clickedItemPositions.valueAt(index);
                    if (checked) {
                        checkedCount++;
                    }
                }
                return checkedCount;
            }
        });

        compareButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(CompareOffersActivity.this, CompareTableActivity.class);
                myIntent.putExtra("jobsToCompare", (Serializable) jobsToCompare); //Optional parameters
                CompareOffersActivity.this.startActivity(myIntent);
            }
        });


    }

    public void returnToMenu(View view) {
        Intent intent = new Intent(CompareOffersActivity.this, MainActivity.class);
        startActivity(intent);
    }

}