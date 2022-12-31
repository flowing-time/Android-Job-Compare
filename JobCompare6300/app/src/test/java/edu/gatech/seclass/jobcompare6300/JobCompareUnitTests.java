package edu.gatech.seclass.jobcompare6300;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class JobCompareUnitTests {

    @Test
    public void weight_computation_isCorrect() {


        // Job 2 should have a higher score, and should therefore be the better job.

        int salaryWeight2 = 5;
        int AYS2 = 4;
        int bonusWeight2 = 8;
        int AYB2 = 4;
        double retiremenBenefitsWeight2 = 2;
        int RBP2 = 2;
        double leaveTimeWeight2 = 20;
        int teleworkWeight2 = 4;
        int LT2 = 6;
        int RWT2 = 5;



        double jobscore2 = (salaryWeight2 * 1.0/7 * AYS2)
                + (bonusWeight2 * 1.0/7 * AYB2)
                + (retiremenBenefitsWeight2 * 1.0/7 * (RBP2 * AYS2))
                + (leaveTimeWeight2 * 1.0/7 * (LT2 * AYS2 / 260))
                - (teleworkWeight2 * 1.0/7 * ((260 - 52 * RWT2) * (AYS2 / 260) / 8));


        int salaryWeight = 1;
        int AYS = 1;
        int bonusWeight = 1;
        int AYB = 1;
        double retiremenBenefitsWeight = 1;
        int RBP = 1;
        double leaveTimeWeight = 1;
        int teleworkWeight = 1;
        int LT = 1;
        int RWT = 1;


        double jobScore1 = (salaryWeight * 1.0/7 * AYS)
                + (bonusWeight * 1.0/7 * AYB)
                + (retiremenBenefitsWeight * 1.0/7 * (RBP * AYS))
                + (leaveTimeWeight * 1.0/7 * (LT * AYS / 260))
                - (teleworkWeight * 1.0/7 * ((260 - 52 * RWT) * (AYS / 260) / 8));


        boolean computed;

        computed = jobscore2 > jobScore1 ? true : false;

        assertEquals(computed, true);

    }

    @Test
    public void verify_job_construction() {

        Job TestJob = new Job();

        TestJob.setTitle("TEST-TITLE");
        TestJob.setCompany("TEST-COMPANY");
        TestJob.setCity("TEST-CITY");
        TestJob.setState("TEST-CA");
        TestJob.setCostOfLiving(1);
        TestJob.setIsCurrentJob(false);
        TestJob.setLeaveTime(1);
        TestJob.setYearlyBonus(100);
        TestJob.setYearlySalary(1000);
        TestJob.setTeleworkDays(1);
        TestJob.setRetirementBenefits(5);



        assertEquals("TEST-TITLE", TestJob.getTitle());
        assertEquals("TEST-COMPANY", TestJob.getCompany());
        assertEquals("TEST-CITY", TestJob.getCity());
        assertEquals("TEST-CA", TestJob.getState());
        assertEquals(1, TestJob.getCostOfLiving());
        assertEquals(false, TestJob.getIsCurrentJob());
        assertEquals(100, TestJob.getYearlyBonus(), 5) ;
        assertEquals(1000, TestJob.getYearlySalary(), 5);
        assertEquals(1, TestJob.getTeleworkDays());
        assertEquals(5, TestJob.getRetirementBenefits(),5 );

    }

    @Test
    public void compare_two_jobs () {

        Job A = new Job();
        Job B = new Job();


        A.setTitle("TEST-TITLE");
        A.setCompany("TEST-COMPANY");
        A.setCity("TEST-CITY");
        A.setState("TEST-CA");
        A.setCostOfLiving(1);
        A.setIsCurrentJob(false);
        A.setLeaveTime(1);
        A.setYearlyBonus(1);
        A.setYearlySalary(1);
        A.setTeleworkDays(1);
        A.setRetirementBenefits(1);

        B.setTitle("TEST-TITLE-2");
        B.setCompany("TEST-COMPANY-2");
        B.setCity("TEST-CITY-2");
        B.setState("TEST-CA-2");
        B.setCostOfLiving(20);
        B.setIsCurrentJob(false);
        B.setLeaveTime(20);
        B.setYearlyBonus(20);
        B.setYearlySalary(20);
        B.setTeleworkDays(20);
        B.setRetirementBenefits(20);

        ComparisonEditor test = new ComparisonEditor();
        test.setLeaveTimeWeight(5);
        test.setBenefitsWeight(4);
        test.setSalaryWeight(3);
        test.setRemoteWeight(2);
        test.setBonusWeight(1);


        int salaryWeight1 = test.getSalaryWeight();
        int bonusWeight1 = test.getBonusWeight();
        int leaveTimeWeight1 = test.getLeaveTimeWeight();
        int teleworkWeight1 = test.getRemoteWeight();
        int retiremenBenefitsWeight1 = test.getBenefitsWeight();

        double AYS1 = A.getYearlySalary();
        double AYB1 = A.getYearlyBonus();
        int RWT1 = A.getTeleworkDays();
        int LT1 = A.getLeaveTime();
        double RBP1 = A.getRetirementBenefits();

        double AYS2 = B.getYearlySalary();
        double AYB2 = B.getYearlyBonus();
        int RWT2 = B.getTeleworkDays();
        int LT2 = B.getLeaveTime();
        double RBP2 = B.getRetirementBenefits();







      double jobScore1 = (salaryWeight1 * 1.0/7 * AYS1)
                + (bonusWeight1 * 1.0/7 * AYB1)
                + (retiremenBenefitsWeight1 * 1.0/7 * (RBP1 * AYS1))
                + (leaveTimeWeight1 * 1.0/7 * (LT1 * AYS1 / 260))
                - (teleworkWeight1 * 1.0/7 * ((260 - 52 * RWT1) * (AYS1 / 260) / 8));

        double jobScore2 = (salaryWeight1 * 1.0/7 * AYS2)
                + (bonusWeight1 * 1.0/7 * AYB2)
                + (retiremenBenefitsWeight1 * 1.0/7 * (RBP2 * AYS2))
                + (leaveTimeWeight1 * 1.0/7 * (LT2 * AYS1 / 260))
                - (teleworkWeight1 * 1.0/7 * ((260 - 52 * RWT2) * (AYS1 / 260) / 8));

        boolean comparison = jobScore2 > jobScore1 ? true : false;

        assertEquals(true, comparison);


    }

    @Test
    public void set_comparison_settings () {


        ComparisonEditor test = new ComparisonEditor();
        test.setLeaveTimeWeight(5);
        test.setBenefitsWeight(4);
        test.setSalaryWeight(3);
        test.setRemoteWeight(2);
        test.setBonusWeight(1);


        assertEquals(5, test.getLeaveTimeWeight());
        assertEquals(4, test.getBenefitsWeight());
        assertEquals(3, test.getSalaryWeight());
        assertEquals(2, test.getRemoteWeight());
        assertEquals(1, test.getBonusWeight());




    }





}