# Test Plan v2 - Updated 3/19/2021

## Changelog 
v4: Added a table for our non-functional tests and how to perform them, as well as their expected and actual output.
<br>v3: Added a table to conveniently view the unit tests. As well as a description of where to find our test suite, and how to run it in code.
<br>v2: Updated manual test cases to include whether the tests pass or fail. Added additional details on the manual tests. 
<br>v1: Initial document submitted.

**Author**: Team 138

## 1 Testing Strategy

### 1.1 Overall strategy


As a team, we're hoping to develop unit tests before and during development. 

That way we can verify that our functionality and logic is 
being implemented correctly. 

This will help us make sure that we do not have any regressions as we add more code/features to the application. Initially, we plan to start with unit testing to cover functionality and regressions, and then we plan to look at integration testing as we divide the project up into different parts. Additionally, we may decide to do some UI testing with Espresso to ensure that our UI components are working as expected. 

We're hoping that a bulk of the testing will come from unit tests, since the application we have to develop isn't too complex.

### 1.2 Test Selection


Looking at a mix of black-box and white-box testing techniques. For white-box testing, we plan to look at path testing, loop testing, condition testing, for now.

For black-box testing, we plan to look at having a mix of functional and non-functional tests. For the functional tests, we will make sure that the requirements of the system are met, and that the system does what the requirements are asking for. For non-functional tests, we will take a look at performance and usability.

### 1.3 Adequacy Criterion

The plan is to have a thorough unit test suite that will reach 100% code coverage. This will ensure that there is no dead/extraneous code checked into source and that each individual piece of the system is tested.

### 1.4 Bug Tracking

We will handle bugs or enhancement requests using GitHub.

### 1.5 Technology

At the moment we plan to use JUnit as our testing framework. If we do decide to do UI testing, we will take a look at using Espresso.

## 2 Test Cases
| Test Case                                                                  | Steps                                                                                                                                                                                        | Expected Result                                                                    | Actual Result                                            | Additional Notes                                         |
|----------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------|----------------------------------------------------------|----------------------------------------------------------|
| User should be able to enter their current job                             | 1. Launch the application 2. Press the 'Current Job' button 3. Fill in required fields based on tooltip 4.Save                                                                               | The users current job is set                                                       | Pass. The users current job is correctly set.            | This assume the user does not yet have a current job set |
| User should be able to enter job offers                                    | 1. Launch the application 2. Press the 'Enter or edit job offers' button 3. Enter the job offer details 4. Press 'Save'                                                                      | The users job offer is stored                                                      | Pass. The users job offer is correctly saved.            | Users should be able to enter any number of job offers.  |
| User should be able to adjust comparison settings for jobs                 | 1. Launch the application 2. Press the 'Comparison Settings' button 3. Adjust the weights for the comparison settings (bonus, salary, telework days, retirement, leave time) 4. Press 'Save' | The job comparison weights are correctly saved                                     | Pass. The comparison weights are correctly stored.       |                                                          |
| User should be shown a list of their job offers, in order of best to worst | 1. Press 'Add Job Offers'. Fill in required details and add multiple offers. 2. Navigate to the 'Main Menu' 3. Press the 'Compare job Offers' button                                         | A list of job offers ranked in order of best to worst.                             | Pass. The jobs are ranked in order of best to worst.     | This assume the user has job offers already saved.       |
| Users should be be able to compare two jobs directly                       | 1. Select two job offers 2. Press 'Compare Jobs' 3. View a side-by-side comparison of the jobs. 4. Be able to perform another comparison or quit to 'Main Menu'                              | Side-by-side comparison table of the two jobs is presented, comparing each detail. | Pass. The two jobs are correctly displayed side-by-side. |                                                          |
| User should be able to retrieve stored Current Job.                        | 1. On the main menu, press 'Current Job' 2. Fill in the required details. Press 'Save'. 3. Relaunch the application. Press 'Current Job'. Verify that you see the previously saved details.  | Previously saved details populate the textboxes.                                   | Pass. The stored information is correctly retrieved.     |                                                          |

### Unit Test Suite

The source code contains a suite of unit tests that can be ran automatically. To find and run this test suite, navigate to<br>```6300Spring21Team137/GroupProject/JobCompare6300/app/src/test/java/edu/gatech/seclass/jobcompare6300/JobCompareUnitTests.java```<br>
From there, you can run the file and it will run a set of tests that take a look at our core app logic. Currently, we verify that getters/setters are working appropriately, the comparison calculations are correct, and job offer comparisons are correctly being computed. Below, a table is provided for convenience.

| Unit Test                     | Description                                                                                                                                                      | Expected Result                                                                                                                           | Actual Result (Pass/Fail) |
|-------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------|---------------------------|
| weight_computation_is_correct | Verifies that the job scoring function/code is correct.  We initialize two different scores and verify that the one with greater benefits is the better option.  | The second job score, which has better values, is chosen as the better one.                                                               | Pass.                     |
| verify_job_construction       | Verifies that the Job class is correctly created and all getters/setters are working appropriately.                                                              | Job class is correctly created and values are set and returned correctly.                                                                 | Pass.                     |
| compare_two_jobs              | Verifies that we can compare two jobs directly, and that the better job is selected/return.                                                                      | Two jobs are created with different values and equal weights. The second job  is correctly identified and selected as the better offer.   | Pass.                     |
| set_comparison_settings       | Verifies that comparison settings/weights are correctly set/saved.                                                                                               | The Comparison Editor class is instantiated and the appropriate setters are  called. The values are correctly saved and can be retrieved. | Pass.                     |

### Non-functional tests
Below are some non-functional tests that we have along with the manual steps to perform them. Since this application is single users and not too complex, we've decided to focus on performance, reliability, and scalability.

| Type         | Test                                                                                                                                                                             | Expected Result                                                                                                                              | Actual Result |
|--------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------|---------------|
| Performance  | Navigate between the various screens by pressing buttons in the UI.                                                                                                              | Screens should transition with little to no hanging/lag.                                                                                     | Pass.         |
| Performance  | Add and/or edit current job and job offers. Press 'Save'. Reload the application. Verify that the data is correctly pulled.                                                      | Adding or editing jobs and pulling previously saved ones should not have any lag or noticeable wait time.                                    | Pass.         |
| Scalability  | Add hundreds of job offers. Navigate to the 'Compare Offers' screen.                                                                                                             | The job offers should be sorted and displayed quickly and the table they are displayed in should be able to handle any amount of job offers. | Pass.         |
| Reliability  | Navigate to the 'Current Job' screen. Add your Current Job. Press 'Save'. Immediately shutdown the phone or application. Reload the application. Navigate back to 'Current Job'. | The originally saved details are still there and can be updated.                                                                             | Pass.         |
