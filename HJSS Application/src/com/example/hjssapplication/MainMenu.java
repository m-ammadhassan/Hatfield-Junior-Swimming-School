package com.example.hjssapplication;

import java.util.Scanner;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class MainMenu extends Menu {
    Scanner userInput = new Scanner(System.in);
    ReusableMethods rm = new ReusableMethods();
    Timetable tt = new Timetable();
    Learner learner = new Learner();
    YesNoMenu ynm = new YesNoMenu();

    public void displayMainMenu()
    {
        String[] arrayMainMenu = {"Book a Swimming Lesson", "Change / Cancel Booking", "Attend a Swimming Lesson", "Monthly Reports", "Register a New Learner", "Exit"};
        int optionMainMenu = displayMenu(arrayMainMenu, "\n");
        actionOnMainMenuOption(optionMainMenu);
    }

    public void actionOnMainMenuOption(int optionMainMenu)
    {
        switch(optionMainMenu)
        {
            case 1:
                functionBookSwimmingLesson();
                break;
            case 2:
                System.out.println("Change / Cancel Booking");
                break;
            case 3:
                System.out.println("Attend a Swimming Lesson");
                break;
            case 4:
                System.out.println("Generate Monthly Reports");
                break;
            case 5:
                functionRegisterLearner();
                break;
            case 6:
                System.out.println("Exit from System");
                System.exit(0);
                break;
            default:
                System.out.println("ERROR: Please select correct option!");
//                selectMenuOption(min, max);
        }
    }

    // Main Menu Function 1: Book a Swimming Lesson
    public void functionBookSwimmingLesson()
    {
        // Function Title
        System.out.println("||===== Book a Swimming Lesson =====||\n");

        JSONObject selectedLesson = new JSONObject();
        JSONObject learnerDetails = new JSONObject();

        int optionTimetableType = tt.displayMenuTimetableType();

        tt.actionOnMenuTimetableType(optionTimetableType);

        if(tt.getTimetable() != null) tt.displayTimetable(tt.getTimetable());
        else displayTryAgainMenu("Do you want to try again?", 1);

        System.out.println("Do you want to select a Lesson?");
        if(ynm.displayYesNoMenu()) selectedLesson = methodSelectALesson(); else displayMainMenu();

        Lesson lesson = new Lesson(
                selectedLesson.get("lessonDate").toString(),
                selectedLesson.get("lessonDay").toString(),
                selectedLesson.get("lessonStartTime").toString(),
                selectedLesson.get("lessonEndTime").toString(),
                Integer.parseInt(selectedLesson.get("lessonGrade").toString()),
                selectedLesson.get("lessonCoach").toString(),
                Integer.parseInt(selectedLesson.get("lessonSlots").toString())
        );

        learnerDetails = methodGetALearner();

        learner = new Learner(
                learnerDetails.get("learnerID").toString(),
                learnerDetails.get("learnerName").toString(),
                learnerDetails.get("learnerGender").toString(),
                Integer.parseInt(learnerDetails.get("learnerAge").toString()),
                learnerDetails.get("learnerEmergencyContact").toString(),
                Integer.parseInt(learnerDetails.get("learnerCurrentGrade").toString())
        );

        if(!rm.validateGradeLevel(lesson, learner)) displayTryAgainMenu("Do you want to try again?", 1);

        if(!rm.validatePreviousBooking(lesson, learnerDetails)) displayTryAgainMenu("Do you want to try again?", 1);

        if(!rm.validateLessonAvailableSlots(lesson)) displayTryAgainMenu("Do you want to try again?", 1);

        lesson.methodUpdateSelectedLessonSlots(selectedLesson, lesson);

        JSONObject newBooking = lesson.methodAddLessonInLearnerBookedLesson(lesson, learnerDetails);

        if(newBooking!=null) {
            System.out.println("\nDear " + learner.getLearnerName() + " you have successfully booked a lesson. Details are below:");
            lesson.getBookedLessonDetails(learner, newBooking);
        }
        else displayTryAgainMenu("\nDo you want to try again?", 1);

        displayTryAgainMenu("\nDo you want to book a new lesson?", 1);
    }

    public JSONObject methodSelectALesson(){
        int optionSelectLesson = selectMenuOption(1, tt.getTimetable().size(), "Select a Lesson");
        while(optionSelectLesson == 0) { optionSelectLesson = selectMenuOption(1, tt.getTimetable().size(), "Select a Lesson"); }
        JSONObject selectedLesson = (JSONObject) tt.getTimetable().get(optionSelectLesson-1);
        return selectedLesson;
    }

    public JSONObject methodGetALearner(){
        String learnerID = learner.getLearnerID();
        JSONArray jsonArray = rm.readFromJSONFile("src\\data\\", "PracticeLearners.json");
        JSONObject learnerDetails = new JSONObject();

        // Get a learner ID first time
        if(learnerID.isEmpty())
        {
            // Repeat until Learner enters his/her valid ID number
            do {
                System.out.print("\nEnter your Learner ID: SWL");
                learnerID = rm.validateLearnerID(userInput.nextLine().replace(" ", ""));
            }
            while(learnerID == null);
        }

        // Search in jsonArray to get the details of the Learner
        for(int i=0; i < jsonArray.size(); i++)
        {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            if(jsonObject.get("learnerID").equals(learnerID)) learnerDetails = (JSONObject) jsonArray.get(i);
        }

        return learnerDetails;
    }

    // Main Menu Function 5: Register a Learner
    public void functionRegisterLearner()
    {
        // Function Title
        System.out.println("\n||===== Register a Learner =====||\n");

        Learner newLearner = new Learner();

        do {
            newLearner.setNewLearnerDetails();
            System.out.println("\nAre you sure all the details are correct?");
        }
        while (!ynm.displayYesNoMenu());

        String learnerID = newLearner.generateLearnerID();
        newLearner.setLearnerID(learnerID);

        JSONObject newLearnerObject = newLearner.createLearnerObject();

        if(rm.writeInJSONFile( "src\\data\\", "PracticeLearners.json", newLearnerObject))
        {
            System.out.println(newLearner.getLearnerName() + " is successfully Registered. Details are below:");
            newLearner.getLearnerDetails();
        }
        else displayTryAgainMenu("\nDo you want to try again?", 5);

        displayTryAgainMenu("\nDo you want to register another Learner?", 5);
    }

    public void displayTryAgainMenu(String message, int optionMainMenu)
    {
        System.out.println(message);
        if(ynm.displayYesNoMenu()) actionOnMainMenuOption(optionMainMenu);
        else displayMainMenu();
    }

}
