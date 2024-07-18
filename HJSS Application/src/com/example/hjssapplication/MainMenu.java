package com.example.hjssapplication;

import org.json.simple.JSONObject;

import java.awt.*;

public class MainMenu extends Menu {
    ReusableMethods rm = new ReusableMethods();
    Timetable tt = new Timetable();
    Learner learner = new Learner();
    Lesson lesson = new Lesson();
    Coach coach = new Coach();
    Report report = new Report();
    Review review = new Review();

    // Display Main Menu
    public void displayMainMenu()
    {
        System.out.println("\n");
        String[] arrayMainMenu = {"Book a Swimming Lesson", "Change / Cancel Booking", "Attend a Swimming Lesson", "Monthly Reports", "Register a New Learner", "Exit"};
        int optionMainMenu = displayMenu(arrayMainMenu, "\n");
        actionOnMainMenuOption(optionMainMenu);
    }

    // Trigger function on the basis of selected option
    public void actionOnMainMenuOption(int optionMainMenu)
    {
        switch(optionMainMenu)
        {
            case 1: functionBookSwimmingLesson(); break;
            case 2: functionChangeCancelSwimmingLesson(); break;
            case 3: functionAttendSwimmingLesson(); break;
            case 4: functionGenerateMonthlyReports(); break;
            case 5: functionRegisterLearner(); break;
            case 6: System.out.println("Exit from Application"); System.exit(0); break;
            default: System.out.println("ERROR: Please select correct option!");
        }
    }

    // Main Menu Function 1: Book a Swimming Lesson
    public void functionBookSwimmingLesson()
    {
        // Function Title
        System.out.println("||===== Book a Swimming Lesson =====||\n");

        JSONObject selectedLesson = new JSONObject();

        // Display timetable if available. Otherwise, Try Again
        if(!tt.displayTimetable()) displayTryAgainMenu("Do you want to try again?", 1);

        System.out.println("Do you want to select a Lesson?");
        if(displayYesNoMenu()) selectedLesson = lesson.setLessonDetailsFromJSON(tt); else functionBookSwimmingLesson();

        JSONObject selectedLearner = learner.setLearnerDetailsFromJSON();

        if(!rm.validateBookLesson(lesson, learner, selectedLearner)) displayTryAgainMenu("Do you want to try again?", 1);

        lesson.methodUpdateSelectedLessonSlots(selectedLesson, "book");

        JSONObject lessonBooked = lesson.methodAddLessonInLearnerBookedLessons(selectedLesson, selectedLearner);

        if(lessonBooked!=null) {
            System.out.println("\nDear " + learner.getLearnerName() + ", you have successfully booked a lesson. Details are below:");
            lesson.methodDisplayLessonDetails(learner, lessonBooked, "Booked");
        }
        else displayTryAgainMenu("\nDo you want to try again?", 1);

        displayTryAgainMenu("\nDo you want to book a new lesson?", 1);
    }

    // Main Menu Function 2: Change or Cancel a Swimming Lesson
    public void functionChangeCancelSwimmingLesson()
    {
        // Function Title
        System.out.println("\n||===== Change / Cancel a Swimming Lesson =====||\n");

        System.out.println("Select from below\n");
        String[] arrayChangeCancelMenu = {"Change a Swimming Lesson", "Cancel a Swimming Lesson"};
        int optionChangeCancelMenu = displayMenu(arrayChangeCancelMenu, "\n");

        if(optionChangeCancelMenu == 1) functionChangeSwimmingLesson();
        else if(optionChangeCancelMenu == 2) functionCancelSwimmingLesson();
    }

    // Sub Menu Function 2-1: Change a Swimming Lesson
    public void functionChangeSwimmingLesson()
    {
        JSONObject selectedLearner = learner.setLearnerDetailsFromJSON();

        tt.displayTimetableOfLeanerBookedLessons(selectedLearner);

        JSONObject previousLesson = new JSONObject();
        System.out.println("Do you want to select a Lesson?");
        if(displayYesNoMenu()) previousLesson = lesson.setLessonDetailsFromJSON(tt); else displayMainMenu();

        if(!rm.validateChangeCancelLessonTime(lesson)) displayTryAgainMenu("Do you want to try again?", 6);

        System.out.println("\n\nSelect a New Lesson: \n");

        if(!tt.displayTimetable()) displayTryAgainMenu("Do you want to try again?", 6);

        JSONObject newLesson = new JSONObject();
        System.out.println("Do you want to select a Lesson?");
        if(displayYesNoMenu()) newLesson = lesson.setLessonDetailsFromJSON(tt); else displayMainMenu();

        if(!rm.validateBookLesson(lesson, learner, selectedLearner)) displayTryAgainMenu("Do you want to try again?", 6);

        JSONObject lessonChanged = lesson.methodUpdateLessonInLearnerBookedLessons(previousLesson, newLesson, selectedLearner);

        if(lessonChanged!=null) {
            System.out.println("\nDear " + learner.getLearnerName() + ", you have successfully changed a lesson. Details are below:");
            lesson.methodDisplayLessonDetails(learner, lessonChanged, "Changed");
        }
        else displayTryAgainMenu("\nDo you want to try again?", 6);

        displaySystemMenu("\nDo you want to change another lesson?", 2);
    }

    // Sub Menu Function 2-2: Cancel a Swimming Lesson
    public void functionCancelSwimmingLesson()
    {
        JSONObject selectedLearner = learner.setLearnerDetailsFromJSON();

        tt.displayTimetableOfLeanerBookedLessons(selectedLearner);

        JSONObject selectedLesson = new JSONObject();
        System.out.println("Do you want to select a Lesson?");
        if(displayYesNoMenu()) selectedLesson = lesson.setLessonDetailsFromJSON(tt); else displayMainMenu();

        if(!rm.validateChangeCancelLessonTime(lesson)) displayTryAgainMenu("Do you want to try again?", 7);

        JSONObject lessonCancelled = lesson.methodAddLessonInLearnerCancelledLessons(selectedLesson, selectedLearner);

        if(lessonCancelled!=null)
        {
            System.out.println("\nDear " + learner.getLearnerName() + ", your lesson is cancelled. Details are below:");
            lesson.methodDisplayLessonDetails(learner, selectedLesson, "Cancelled");
        }
        else displayTryAgainMenu("\nDo you want to try again?", 7);

        displaySystemMenu("\nDo you want to cancel another lesson?", 2);
    }

    // Main Menu Function 3: Attend a Swimming Lesson
    public void functionAttendSwimmingLesson()
    {
        // Function Title
        System.out.println("\n||===== Attend a Swimming Lesson =====||\n");

        JSONObject selectedLearner = learner.setLearnerDetailsFromJSON();

        tt.displayTimetableOfLeanerBookedLessons(selectedLearner);

        JSONObject selectedLesson = new JSONObject();
        System.out.println("Do you want to select a Lesson?");
        if(displayYesNoMenu()) selectedLesson = lesson.setLessonDetailsFromJSON(tt); else displayMainMenu();

        if(!rm.validateAttendLessonTime(lesson)) displayTryAgainMenu("Do you want to try again?", 3);

        review.methodSetNewReviewDetails();

        JSONObject lessonAttended = lesson.methodAddLessonInLearnerAttendedLessons(selectedLesson, selectedLearner, review);

        if(lessonAttended!=null) {
            System.out.println("\nDear " + learner.getLearnerName() + ", your lesson is marked attended. Details are below:");
            lesson.methodDisplayLessonDetails(learner, lessonAttended, "Attended");
        }
        else displayTryAgainMenu("\nDo you want to try again?", 3);

        if(learner.methodUpdateLearnerGradeLevel(lessonAttended, selectedLearner)) lesson.methodUpdateBookedLessonsOnGradeUpgrade(selectedLearner);

        coach.methodAddCoachReview(learner, lessonAttended);

        displaySystemMenu("\nDo you want to mark another lesson attended?", 3);
    }

    // Mani Menu Function 4: Generate Monthly Reports
    public void functionGenerateMonthlyReports()
    {
        // Function Title
        System.out.println("\n||===== Generate Monthly Reports =====||\n");

        System.out.println("Select from Below\n");
        String[] arrayMonthlyReports = {"Monthly Report of Learners", "Monthly Report of Coaches"};
        int optionMonthlyReports = displayMenu(arrayMonthlyReports, "\n");

        report.setReportDateDetails();

        if(optionMonthlyReports == 1) functionGenerateMonthlyLearnersReport();
        else if(optionMonthlyReports == 2) functionGenerateMonthlyCoachesReport();
    }

    // Sub Menu Function 4-1: Generate Monthly Report of Learners
    public void functionGenerateMonthlyLearnersReport()
    {
        report.methodDisplayReportHeader("Monthly Report of Learners");
        report.methodDisplayReportOfLearners();
        displaySystemMenu("Select an option", 4);
    }

    // Sub Menu Function 4-2: Generate Monthly Report of Coaches
    public void functionGenerateMonthlyCoachesReport()
    {
        report.methodDisplayReportHeader("Monthly Report of Coaches");
        report.methodDisplayReportOfCoaches();
        displaySystemMenu("Select an option", 4);
    }

    // Main Menu Function 5: Register a Learner
    public void functionRegisterLearner()
    {
        // Function Title
        System.out.println("\n||===== Register a Learner =====||\n");

        do {
            learner.setNewLearnerDetails();
            System.out.println("\nAre you sure all the details are correct?");
        }
        while (!displayYesNoMenu());

        JSONObject registeredLearner = learner.methodAddNewLearner();

        if(registeredLearner != null)
        {
            System.out.println(learner.getLearnerName() + " is successfully Registered. Details are below:");
            learner.getLearnerDetails();
        }
        else displayTryAgainMenu("\nDo you want to try again?", 5);

        displayTryAgainMenu("\nDo you want to register another Learner?", 5);
    }

    public void displayTryAgainMenu(String message, int optionMainMenu)
    {
        System.out.println(message);
        if(displayYesNoMenu())
        {
            if(optionMainMenu >=1 && optionMainMenu <=5) actionOnMainMenuOption(optionMainMenu);
            else if(optionMainMenu == 6) functionChangeSwimmingLesson();
            else if(optionMainMenu == 7) functionCancelSwimmingLesson();
        }
        else displayMainMenu();
    }

    public void displaySystemMenu(String systemMessage, int optionMainMenu)
    {
        System.out.println("\n" + systemMessage);
        String[] arraySystemMenu = {"Repeat", "Main Menu", "Exit"};
        int optionSystemMenu = displayMenu(arraySystemMenu, "\t\t");
        if(optionSystemMenu == 1) actionOnMainMenuOption(optionMainMenu);
        else if(optionSystemMenu == 2) displayMainMenu();
        else actionOnMainMenuOption(6);
    }

    public Boolean displayYesNoMenu()
    {
        String[] arrayYesNoMenu = {"Yes", "No"};
        int optionYesNoMenu = displayMenu(arrayYesNoMenu, "\t\t");

        if(optionYesNoMenu == 1) return true;
        else return false;
    }

}
