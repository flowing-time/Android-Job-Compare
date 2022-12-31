package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db =  Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "job-offer-database").allowMainThreadQueries().build();

        Button btn = (Button) findViewById(R.id.btnCompareOffers);

        JobComparisonAppDao JCAP = db.jobComparisonAppDao();

        /*
        List<Job> tj = JCAP.getAllJobs();

        for (Job j : tj )
        {
            JCAP.deleteJob(j);
        } */


        List<Job> stored = JCAP.getAllJobs();

        if (stored.size() == 0)
        {
            btn.setEnabled(false);
        }
        else {
            btn.setEnabled(true);
        }

    }

    public void AddOrViewCurrentJob(View view) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isCurrentJobView", true);
        Intent intent = new Intent(MainActivity.this, JobActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void addJobOffer(View view) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isCurrentJobView", false);
        Intent intent = new Intent(MainActivity.this, JobActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    public void adjustComparisonSetting(View view) {
        Intent intent = new Intent(MainActivity.this,
                ComparisonEditorActivity.class);
        startActivity(intent);
    }

    public void CompareOffers(View view) {
        Intent intent = new Intent(MainActivity.this, CompareOffersActivity.class);
        startActivity(intent);

    }
}