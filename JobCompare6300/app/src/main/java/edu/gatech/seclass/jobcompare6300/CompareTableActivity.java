package edu.gatech.seclass.jobcompare6300;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;


public class CompareTableActivity extends AppCompatActivity {

    private TextView titleA;
    private TextView titleB;
    private TextView companyA;
    private TextView companyB;
    private TextView locationA;
    private TextView locationB;
    private TextView salaryA;
    private TextView salaryB;
    private TextView bonusA;
    private TextView bonusB;
    private TextView teleA;
    private TextView teleB;
    private TextView beneA;
    private TextView beneB;
    private TextView leaveA;
    private TextView leaveB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparison_table);

        Intent compareJobs = getIntent();
        List<Job> jobsToCompare = (List<Job>) compareJobs.getSerializableExtra("jobsToCompare");

        Job jobA = jobsToCompare.get(0);
        Job jobB = jobsToCompare.get(1);

        titleA = (TextView) findViewById(R.id.titleAid);
        titleB = (TextView) findViewById(R.id.titleBid);
        companyA = (TextView) findViewById(R.id.companyAid);
        companyB = (TextView) findViewById(R.id.companyBid);
        locationA = (TextView) findViewById(R.id.locAid);
        locationB = (TextView) findViewById(R.id.locBid);
        salaryA = (TextView) findViewById(R.id.salaryAid);
        salaryB = (TextView) findViewById(R.id.salaryBid);
        bonusA = (TextView) findViewById(R.id.bonusAid);
        bonusB = (TextView) findViewById(R.id.bonusBid);
        teleA = (TextView) findViewById(R.id.teleAid);
        teleB = (TextView) findViewById(R.id.teleBid);
        beneA = (TextView) findViewById(R.id.beneAid);
        beneB = (TextView) findViewById(R.id.beneBid);
        leaveA = (TextView) findViewById(R.id.leaveAid);
        leaveB = (TextView) findViewById(R.id.leaveBid);

        titleA.setText(jobA.getTitle());
        titleB.setText(jobB.getTitle());
        companyA.setText(jobA.getCompany());
        companyB.setText(jobB.getCompany());
        locationA.setText(jobA.getCity() + "\n" + jobA.getState());
        locationB.setText(jobB.getCity() + "\n" + jobB.getState());
        salaryA.setText(Double.toString(jobA.getYearlySalary()));
        salaryB.setText(Double.toString(jobB.getYearlySalary()));
        bonusA.setText(Double.toString(jobA.getYearlyBonus()));
        bonusB.setText(Double.toString(jobB.getYearlyBonus()));
        teleA.setText(Integer.toString(jobA.getTeleworkDays()));
        teleB.setText(Integer.toString(jobB.getTeleworkDays()));
        beneA.setText(Double.toString(jobA.getRetirementBenefits()));
        beneB.setText(Double.toString(jobB.getRetirementBenefits()));
        leaveA.setText(Integer.toString(jobA.getLeaveTime()));
        leaveB.setText(Integer.toString(jobB.getLeaveTime()));

        jobsToCompare.clear();
    }


    public void returnToMenu(View view) {
        Intent intent = new Intent(CompareTableActivity.this, MainActivity.class);
        startActivity(intent);
    }



    public void compareAnother(View view) {
        Intent intent = new Intent(CompareTableActivity.this, CompareOffersActivity.class);
        startActivity(intent);

    }
}