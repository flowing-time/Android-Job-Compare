package edu.gatech.seclass.jobcompare6300;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PostJobNavActivity extends AppCompatActivity {

    AppDatabase db;

    private Job currentJob;
    private Job newJob;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job_nav);

        Intent i = getIntent();
        newJob = (Job) i.getSerializableExtra("newJob");

        db =  Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "job-offer-database").allowMainThreadQueries().build();
        JobComparisonAppDao JCAP = db.jobComparisonAppDao();
        currentJob = JCAP.getCurrentJob();

        Button btn = (Button) findViewById(R.id.btnCompareCurrent);
        if (currentJob == null) {
            btn.setEnabled(false);
        } else {
            btn.setEnabled(true);
        }
    }

    public void returnToMenu(View view) {
        Intent intent = new Intent(PostJobNavActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void AddOrViewJob(View view) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isCurrentJobView", false);
        Intent intent = new Intent(PostJobNavActivity.this, JobActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        this.finish();
    }

    public void compareTable(View view) {

        List<Job> jobsToCompare = new ArrayList<Job>();
        jobsToCompare.add(currentJob);
        jobsToCompare.add(newJob);

        Intent myIntent = new Intent(PostJobNavActivity.this, CompareTableActivity.class);
        myIntent.putExtra("jobsToCompare", (Serializable) jobsToCompare); //Optional parameters
        startActivity(myIntent);
    }
}