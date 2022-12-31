# Design Document

## 1 Introduction

This android product is meant to help people find a job. It is a rudimentary, single-user job offer comparison app that takes into account many aspects of a job.

### 1.1 Assumptions

The assumption the team is going into the project is that the software focuses on a single user application and runs on Android OS devices. Since it will be written in Java, there could potentially be gradle problems with dependencies. Other project issues could stem from the development environment, such as merge conflicts with git.

### 1.2 Constraints

Constraints that the team has thought of are that certain input fields will be numerical (double and integer) or string types only. These are the folowing constraints we agreed upon in D3:

- For the current job and adding new job forms, none of the fields can be empty, or the job form will not be saved to the database.
  The field for the user's state (location) must be in the form of the common US-based two letter state abbreviation, such as GA and CA.
- Retirement benefits must be between 0 and 100, since the user is expected to enter a percentage, and we agreed that the percentage should not exceed 100%.
- The number of telework days must be between 0 and 5.
- Compare with current job button must be disabled in both main activity and post job navigation activity.
- Cost of living must also not be 0, since we are using this value as a divisor and we cannot divide by 0.
- For the job comparison functionality, the user can only select two jobs to compare or the compare button would be disabled; in addition, the user can only have one `CurrentJob`, though the user can update it. The compare offers button in the main menu will also be disabled unless the `CurrentJob` is not null. This functionality will be enabled only if there are either at least two job offers, in case there is no current job, or at least one job offer, in case there is a current job.

### 1.3 System Environment

The system is in Java (Android Studio) so users must have Android OS. The minimum compatible version is API 28, Android 9.0 (Pie). For optimal experience that reflects the original design intention, we require the system to be run on a Pixel 3 (larger screen surface).

## 2 Architectural Design

### 2.1 Component Diagram

We decided to have a diagram for this even though our application is quite simple. The purpose for this program is to help us see how the pieces fit together and how the data will flow.

![Component Diagram](../images/component.jpg)

### 2.2 Deployment Diagram

For this simple project, this diagram is unnecessary. There aren't many moving pieces, and we are not planning to deploy the application to a live server, to the cloud, or to any physical android devices. So the Android application will entireby be run in a local environment and live locally in Android Studio. Since there aren't any mainframes, any dockerization, or in general any devOps pipelines, this diagram is not necessary for this project.

## 3 Low-Level Design

### 3.1 Class Diagram

We had to update the UML class diagram since more methods, fields, and functionalities were thought of during D3 and D4. We decided to get rid of the save and cancel methods from Job class since we realized that it would be more optimal and cleaner code practice for the UI/Android Activity to take care of this functionality. The getter and setter methods are also removed since it's redundant to include them in an UML diagram (default is to assume getters and setters are already there for all declared fields).

We also decided to get rid of the main menu representation since all of the methods in that Activity are UI-based, such as Intents, Bundles, etc; instead, the only relevant class connecting the pieces together is the JobActivity class. Other Activity classes are excluded because the functions are quite UI-based.

This updated UML Class diagram describes all of the classes and functions the application will have.
![UML Class Diagram](../images/uml-v2.jpeg)

### 3.2 Other Diagrams

*`<u>`Optionally `</u>`, you can decide to describe some dynamic aspects of your system using one or more behavioral diagrams, such as sequence and state diagrams.* -- N/A

## 4 User Interface Design

During D3 and D4 we also made significant effort to refactor the design to be simpler and more intuitive. In the original wireframes, there is a checkbox that allows the user to indicate if this was a current job or not being saved to DB. This was highly inefficient since this means that the user can also add new jobs while on the same screen as viewing current job after clicking "CURRENT JOB" button.

We decided to have a separation of concern, meaning that each view is only allowed to do what the name of the button that led to the screen states.

![Main Menu and Current Job Input Screen](../images/wireframe-v2.png)
