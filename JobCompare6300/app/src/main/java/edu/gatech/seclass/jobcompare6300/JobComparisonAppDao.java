package edu.gatech.seclass.jobcompare6300;

import android.widget.CheckBox;
import android.widget.EditText;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface JobComparisonAppDao {

    @Query("SELECT * FROM ComparisonEditor where uid == 1")
    List<ComparisonEditor> getComparisonSettings();

    @Insert
    void addComparisonSettings(ComparisonEditor ce);

    @Query("UPDATE ComparisonEditor SET salary_weight=:salaryWeight, bonus_weight=:bonusWeight, remote_weight=:remoteWeight, benefits_weight=:benefitsWeight, leave_time_weight=:leaveTimeWeight WHERE uid == 1 ")
    void updateComparisonSettings(int salaryWeight, int bonusWeight, int remoteWeight, int benefitsWeight, int leaveTimeWeight);

    @Query("SELECT * FROM Job")
    List<Job> getAllJobs();

    @Query("SELECT * FROM Job WHERE is_current_job == 1 ")
    Job getCurrentJob();

    @Query("UPDATE job SET title=:title, company=:company, city=:city, state=:state, cost_of_living=:costOfLiving, leave_time=:leaveTime, yearly_salary=:yearlySalary, yearly_bonus=:yearlyBonus, retirement_benefits=:retirementBenefits, telework_days=:teleworkDays WHERE is_current_job == 1 ")
    void updateCurrentJob(String title, String company, String city, String state,
                          int costOfLiving, int leaveTime, double yearlySalary, double yearlyBonus,
                          double retirementBenefits, int teleworkDays);

    @Insert
    void addJob(Job j);

    @Delete
    void deleteJob(Job j);
}