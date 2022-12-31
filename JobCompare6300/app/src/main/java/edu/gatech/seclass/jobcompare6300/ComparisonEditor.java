package edu.gatech.seclass.jobcompare6300;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ComparisonEditor {

    @PrimaryKey// Must have a primary key for the database to work, however we will only have one ComparisonEditor class ever
    public int uid = 1;

    @ColumnInfo(name = "salary_weight")
    private int salaryWeight = 1;

    @ColumnInfo(name = "bonus_weight")
    private int bonusWeight = 1;

    @ColumnInfo(name = "remote_weight")
    private int remoteWeight = 1;

    @ColumnInfo(name = "benefits_weight")
    private int benefitsWeight = 1;

    @ColumnInfo(name = "leave_time_weight")
    private int leaveTimeWeight = 1;

    public void setSalaryWeight(int w) {
        salaryWeight = w;
    }

    public void setBonusWeight(int w) {
        bonusWeight = w;
    }

    public void setRemoteWeight(int w) {
        remoteWeight = w;
    }

    public void setBenefitsWeight(int w) {
        benefitsWeight = w;
    }

    public void setLeaveTimeWeight(int w) {
        leaveTimeWeight = w;
    }

    public int getSalaryWeight() {
        return salaryWeight;
    }

    public int getBonusWeight() {
        return bonusWeight;
    }

    public int getRemoteWeight() {
        return remoteWeight;
    }

    public int getBenefitsWeight() {
        return benefitsWeight;
    }

    public int getLeaveTimeWeight() {
        return leaveTimeWeight;
    }
}