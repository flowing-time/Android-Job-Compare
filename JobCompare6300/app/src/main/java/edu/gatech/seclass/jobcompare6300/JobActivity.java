package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class JobActivity extends AppCompatActivity {

    AppDatabase db;

    private EditText jobTitle;
    private EditText companyName;
    private EditText costOfLiving;
    private EditText yearlySalary;
    private EditText yearlyBonus;
    private EditText retirementBenefits;
    private EditText leaveTime;
    private EditText weeklyTeleworkDays;
    private EditText city;
    private EditText state;
    private Job currentJob;
    private JobComparisonAppDao JCAP;
    private Bundle bundle;
    private TextView jobViewTitle;
    private Boolean isCurrentJobView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,
                "job-offer-database").allowMainThreadQueries().build();
        weeklyTeleworkDays = (EditText) findViewById(R.id.allowedTeleworkDaysInput);
        city = (EditText) findViewById(R.id.cityInput);
        state = (EditText) findViewById(R.id.stateInput);
        jobTitle = (EditText) findViewById(R.id.titleInput);
        companyName = (EditText) findViewById(R.id.companyInput);
        costOfLiving = (EditText) findViewById(R.id.costOfLivingInput);
        yearlySalary = (EditText) findViewById(R.id.yearlySalaryInput);
        yearlyBonus = (EditText) findViewById(R.id.yearlyBonusInput);
        retirementBenefits = (EditText) findViewById(R.id.retirementBenefitInput);
        leaveTime = (EditText) findViewById(R.id.leaveTimeInput);
        JCAP = db.jobComparisonAppDao();
        currentJob = JCAP.getCurrentJob();
        bundle = getIntent().getExtras();
        isCurrentJobView = bundle.getBoolean("isCurrentJobView");
        jobViewTitle = (TextView) findViewById(R.id.jobFormTitle);

        if (currentJob != null && isCurrentJobView) { // There is a current job in DB already
            weeklyTeleworkDays.setText(Integer.toString(currentJob.getTeleworkDays()));
            city.setText(currentJob.getCity());
            state.setText(currentJob.getState());
            jobTitle.setText(currentJob.getTitle());
            companyName.setText(currentJob.getCompany());
            costOfLiving.setText(Integer.toString(currentJob.getCostOfLiving()));
            yearlySalary.setText(Double.toString(currentJob.getYearlySalary()));
            yearlyBonus.setText(Double.toString(currentJob.getYearlyBonus()));
            retirementBenefits.setText(Double.toString(currentJob.getRetirementBenefits()));
            leaveTime.setText(Integer.toString(currentJob.getLeaveTime()));
            jobViewTitle.setText("View or Edit Current Job");
        }
        else if (!isCurrentJobView) {
            jobViewTitle.setText("Add a New Job");
        }
        else if (currentJob == null && isCurrentJobView) { // There is a current job in DB already
            jobViewTitle.setText("Add Current Job");
        }
    }

    private void checkTeleWorkDaysError() {
        String weeklyTeleworkDaysStr = weeklyTeleworkDays.getText().toString();
        if (TextUtils.isEmpty(weeklyTeleworkDaysStr)) {
            weeklyTeleworkDays.setError("Field cannot be empty");
            return;
        }
        int numTeleworkDays = Integer.parseInt(weeklyTeleworkDaysStr);

        if (numTeleworkDays > 5 || numTeleworkDays < 0) {
            weeklyTeleworkDays.setError("Number of telework days must be between 0 and 5");
        }
    }

    private void checkRetirementPercentage() {
        String retirementBenefitPercentage = retirementBenefits.getText().toString();

        if (TextUtils.isEmpty(retirementBenefitPercentage)) {
            retirementBenefits.setError("Field cannot be empty");
        } else if (Double.parseDouble(retirementBenefitPercentage) > 100) {
            retirementBenefits.setError("Percentage must be between 0 and 100%");
        }
    }

    private void checkStateError() {
        String stateStr = state.getText().toString();

        if (TextUtils.isEmpty(stateStr)) {
            state.setError("Field cannot be empty");
        } else if (stateStr.length() != 2) {
            state.setError("Must be in format of state abbreviation, ie CA");
        }
    }

    private void checkCostOfLiving() {
        String costOfLivingStr = costOfLiving.getText().toString();

        if (TextUtils.isEmpty(costOfLivingStr)) {
            costOfLiving.setError("Field cannot be empty");
        } else if (Integer.parseInt(costOfLivingStr) == 0) {
            costOfLiving.setError("Cost of living cannot be 0");
        }
    }

    private void checkAllFields() {

        String jobTitleStr = jobTitle.getText().toString();
        String companyNameStr = companyName.getText().toString();
        String cityStr = city.getText().toString();
        String yearlySalaryStr = yearlySalary.getText().toString();
        String yearlyBonusStr = yearlyBonus.getText().toString();
        String leaveTimeStr = leaveTime.getText().toString();

        if (TextUtils.isEmpty(jobTitleStr)) {
            jobTitle.setError("Field cannot be empty");
        }
        if (TextUtils.isEmpty(companyNameStr)) {
            companyName.setError("Field cannot be empty");
        }
        if (TextUtils.isEmpty(yearlySalaryStr)) {
            yearlySalary.setError("Field cannot be empty");
        }
        if (TextUtils.isEmpty(yearlyBonusStr)) {
            yearlyBonus.setError("Field cannot be empty");
        }
        if (TextUtils.isEmpty(cityStr)) {
            city.setError("Field cannot be empty");
        }
        if (TextUtils.isEmpty(leaveTimeStr)) {
            leaveTime.setError("Field cannot be empty");
        }
    }

    public void returnToMenu(View view) {
        Intent intent = new Intent(JobActivity.this, MainActivity.class);
        startActivity(intent);
    }


    public void saveJobToDB(View view) {

        checkAllFields();
        checkTeleWorkDaysError();
        checkStateError();
        checkRetirementPercentage();
        checkCostOfLiving();

        if (weeklyTeleworkDays.getError() == null && jobTitle.getError() == null &&
                city.getError() == null &&  state.getError() == null &&
                companyName.getError() == null && costOfLiving.getError() == null
                && retirementBenefits.getError() == null &&
                leaveTime.getError() == null && yearlySalary.getError() == null &&
                yearlyBonus.getError() == null) {

            Job newJob = new Job();
            newJob.setTitle(jobTitle.getText().toString());
            newJob.setCompany(companyName.getText().toString());
            newJob.setCity(city.getText().toString());
            newJob.setState(state.getText().toString());
            newJob.setCostOfLiving(Integer.parseInt(costOfLiving.getText().toString()));
            newJob.setLeaveTime(Integer.parseInt(leaveTime.getText().toString()));
            newJob.setYearlySalary(Double.parseDouble(yearlySalary.getText().toString()));
            newJob.setYearlyBonus(Double.parseDouble(yearlyBonus.getText().toString()));
            newJob.setRetirementBenefits(Double.parseDouble(retirementBenefits.getText().toString()));
            newJob.setTeleworkDays(Integer.parseInt(weeklyTeleworkDays.getText().toString()));

            // CURRENT JOB DB LOGIC
            if (isCurrentJobView) {
                if (currentJob == null) { // first time adding current job
                    newJob.setIsCurrentJob(true);
                    JCAP.addJob(newJob);
                } else { // replacing current job with a new current job
                    JCAP.updateCurrentJob(jobTitle.getText().toString(), companyName.getText().toString(), city.getText().toString(),
                            state.getText().toString(),Integer.parseInt(costOfLiving.getText().toString()),
                            Integer.parseInt(leaveTime.getText().toString()), Double.parseDouble(yearlySalary.getText().toString()),
                            Double.parseDouble(yearlyBonus.getText().toString()), Double.parseDouble(retirementBenefits.getText().toString()),
                            Integer.parseInt(weeklyTeleworkDays.getText().toString()));
                }
                // NON-CURRENT JOB DB LOGIC
            } else {
                newJob.setIsCurrentJob(false);
                JCAP.addJob(newJob);
            }
            Toast.makeText(JobActivity.this, "Job saved!", Toast.LENGTH_LONG).show();

            if (!isCurrentJobView) {
                Intent intent = new Intent(JobActivity.this, PostJobNavActivity.class);
                intent.putExtra("newJob", newJob);
                startActivity(intent);
                this.finish();
            } else {
                Intent intent = new Intent(JobActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }

}