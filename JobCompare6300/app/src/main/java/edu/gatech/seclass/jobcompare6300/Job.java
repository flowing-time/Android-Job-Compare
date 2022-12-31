package edu.gatech.seclass.jobcompare6300;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Job implements Serializable {

    @PrimaryKey(autoGenerate = true) // Every job will have primary key, this is necessary for the database to work, please don't remove.
    public int uid;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "company")
    private String company;

    @ColumnInfo(name = "city")
    private String city;

    @ColumnInfo(name = "state")
    private String state;

    @ColumnInfo(name = "cost_of_living")
    private int costOfLiving;

    @ColumnInfo(name = "leave_time")
    private int leaveTime;

    @ColumnInfo(name = "yearly_salary")
    private double yearlySalary;

    @ColumnInfo(name = "yearly_bonus")
    private double yearlyBonus;

    @ColumnInfo(name = "retirement_benefits")
    private double retirementBenefits;

    @ColumnInfo(name = "telework_days")
    private int teleworkDays;

    @ColumnInfo(name = "is_current_job")
    private boolean isCurrentJob;

//    public Job(String title, String company, String city, String state, int costOfLiving,
//               int leaveTime, double yearlySalary, double yearlyBonus, double retirementBenefits,
//               int teleworkDays, Boolean isCurrentJob) {
//        this.title = title;
//        this.company = company;
//        this.city = city;
//        this.state = state;
//        this.costOfLiving = costOfLiving;
//        this.leaveTime = leaveTime;
//        this.yearlySalary = yearlySalary;
//        this.yearlyBonus = yearlyBonus;
//        this.retirementBenefits = retirementBenefits;
//        this.teleworkDays = teleworkDays;
//        this.isCurrentJob = isCurrentJob;
//    }
    public String getTitle() {
        return title;
    }

    public String getCompany() {
        return company;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public int getCostOfLiving() {
        return costOfLiving;
    }

    public int getLeaveTime() {
        return leaveTime;
    }

    public double getYearlySalary() {
        return yearlySalary;
    }

    public double getYearlyBonus() {
        return yearlyBonus;
    }

    public double getRetirementBenefits() {
        return retirementBenefits;
    }

    public int getTeleworkDays() { return teleworkDays; }


    public boolean getIsCurrentJob() { return isCurrentJob; }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCostOfLiving(int costOfLiving) {
        this.costOfLiving = costOfLiving;
    }

    public void setLeaveTime(int leaveTime) {
        this.leaveTime = leaveTime;
    }

    public void setYearlySalary(double yearlySalary) {
        this.yearlySalary = yearlySalary;
    }

    public void setYearlyBonus(double yearlyBonus) {
        this.yearlyBonus = yearlyBonus;
    }

    public void setRetirementBenefits(double retirementBenefits) {
        this.retirementBenefits = retirementBenefits;
    }

    public void setIsCurrentJob(boolean isCurrentJob) {
        this.isCurrentJob = isCurrentJob;
    }


    public void setTeleworkDays(int teleworkDays) {
        this.teleworkDays = teleworkDays;
    }
}
