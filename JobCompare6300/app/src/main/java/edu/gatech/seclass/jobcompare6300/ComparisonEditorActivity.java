package edu.gatech.seclass.jobcompare6300;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.List;

public class ComparisonEditorActivity extends AppCompatActivity {

    AppDatabase db;

    private EditText teleworkWeight;
    private EditText salaryWeight;
    private EditText bonusWeight;
    private EditText retiremenBenefitsWeight;
    private EditText leaveTimeWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparison_setting);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,
                "job-offer-database").allowMainThreadQueries().build();

        teleworkWeight = (EditText) findViewById(R.id.weeklyTeleworkWeightInput);
        salaryWeight = (EditText) findViewById(R.id.yearSalaryWeightInput);
        bonusWeight = (EditText) findViewById(R.id.yearlyBonusWeightInput);
        retiremenBenefitsWeight = (EditText) findViewById(R.id.retirementBenefitWeightInput);
        leaveTimeWeight = (EditText) findViewById(R.id.leaveTimeWeightInput);

        JobComparisonAppDao JCAP = db.jobComparisonAppDao();

        List<ComparisonEditor> check = JCAP.getComparisonSettings();

        if(check.size() > 0) {

            ComparisonEditor stored = check.get(0); // Grab the saved settings, if they exist
            teleworkWeight.setText(Integer.toString(stored.getRemoteWeight()));
            salaryWeight.setText(Integer.toString(stored.getSalaryWeight()));
            bonusWeight.setText(Integer.toString(stored.getBonusWeight()));
            retiremenBenefitsWeight.setText(Integer.toString(stored.getBenefitsWeight()));
            leaveTimeWeight.setText(Integer.toString(stored.getLeaveTimeWeight()));
        }
    }

    private void checkAllFields() {

        int teleworkWeightNum = Integer.parseInt(teleworkWeight.getText().toString());
        int salaryWeightNum = Integer.parseInt(salaryWeight.getText().toString());
        int bonusWeightNum = Integer.parseInt(bonusWeight.getText().toString());
        int retiremenBenefitsWeightNum = Integer.parseInt(retiremenBenefitsWeight.getText().toString());
        int leaveTimeWeightNum = Integer.parseInt(leaveTimeWeight.getText().toString());

        if (teleworkWeightNum < 1 || teleworkWeightNum > 9) {
            teleworkWeight.setError("Field must be between 1 and 9");
        }

        if (salaryWeightNum < 1 || salaryWeightNum > 9) {
            salaryWeight.setError("Field must be between 1 and 9");
        }

        if (bonusWeightNum < 1 || bonusWeightNum > 9) {
            bonusWeight.setError("Field must be between 1 and 9");
        }

        if (leaveTimeWeightNum < 1 || leaveTimeWeightNum > 9) {
            leaveTimeWeight.setError("Field must be between 1 and 9");
        }

        if (retiremenBenefitsWeightNum < 1 || retiremenBenefitsWeightNum > 9) {
            retiremenBenefitsWeight.setError("Field must be between 1 and 9");
        }

    }

    public void saveToDB(View view) {
        checkAllFields();
        if (teleworkWeight.getError() == null &&
                salaryWeight.getError() == null && bonusWeight.getError() == null &&
                retiremenBenefitsWeight.getError() == null && leaveTimeWeight.getError() == null) {

            JobComparisonAppDao JCAP = db.jobComparisonAppDao();
            List<ComparisonEditor> check = JCAP.getComparisonSettings();

            if (check.size() > 0) {

                JCAP.updateComparisonSettings(Integer.parseInt(salaryWeight.getText().toString()), Integer.parseInt(bonusWeight.getText().toString()), Integer.parseInt(teleworkWeight.getText().toString()), Integer.parseInt(retiremenBenefitsWeight.getText().toString()), Integer.parseInt(leaveTimeWeight.getText().toString()));

                List<ComparisonEditor> updated = JCAP.getComparisonSettings(); //grab the updated settings to update the text fields
                ComparisonEditor stored = updated.get(0);

                teleworkWeight.setText(Integer.toString(stored.getRemoteWeight()));
                salaryWeight.setText(Integer.toString(stored.getSalaryWeight()));
                bonusWeight.setText(Integer.toString(stored.getBonusWeight()));
                retiremenBenefitsWeight.setText(Integer.toString(stored.getBenefitsWeight()));
                leaveTimeWeight.setText(Integer.toString(stored.getLeaveTimeWeight()));

            } else { //else, create them for the first time.

                ComparisonEditor init = new ComparisonEditor();

                init.setRemoteWeight(Integer.parseInt(teleworkWeight.getText().toString()));
                init.setSalaryWeight(Integer.parseInt(salaryWeight.getText().toString()));
                init.setBonusWeight(Integer.parseInt(bonusWeight.getText().toString()));
                init.setBenefitsWeight(Integer.parseInt(retiremenBenefitsWeight.getText().toString()));
                init.setLeaveTimeWeight(Integer.parseInt(leaveTimeWeight.getText().toString()));
                init.uid = 1;

                JCAP.addComparisonSettings(init); // Add this to the database
            }
            Toast.makeText(ComparisonEditorActivity.this, "Weight setting saved!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(ComparisonEditorActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}