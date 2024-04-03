package com.example.hjssapplication;

import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class MainMenu extends Menu {
    Scanner userInput = new Scanner(System.in);
    ReusableMethods rm = new ReusableMethods();

    public void displayMainMenu()
    {
        String[] arrayMainMenu = {"Book a Swimming Lesson", "Change / Cancel Booking", "Attend a Swimming Lesson", "Monthly Reports", "Register a New Learner", "Exit"};

        int menuOption = displayMenu(arrayMainMenu, "\n");

        actionOnMainMenuOption(menuOption);
    }

    public void actionOnMainMenuOption(int menuOption)
    {
        switch(menuOption)
        {
            case 1:
                System.out.println("Book a Swimming Lesson");
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
                //System.out.println("Register a New Learner");
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


    // Main Menu Function 5: Register a Learner
    public void functionRegisterLearner()
    {
        rm.clearConsole();
        // Function Title
        System.out.println("||===== Register a Learner =====||\n");

        // Declaring Local Variables
        String learnerID, learnerName, learnerGender, learnerDOBDay, learnerDOBMonth, learnerDOBYear, learnerContact;
        int learnerAge, learnerGrade;

        // --> Getting Name
        do{
            System.out.print("Enter Name: ");
            learnerName = rm.validateName(userInput.nextLine());
        }
        while (learnerName==null);

        // --> Getting Gender
        System.out.println("\nSelect Gender: ");
        learnerGender = rm.validateGender(displayMenu(new String[] {"Male", "Female", "Other"}, "\t\t"));

        // --> Getting DOB
        System.out.println("\nDate of Birth: ");
        // Year
        do{
            System.out.print("Enter Year: ");
            learnerDOBYear = rm.validateYear(userInput.nextLine().replace(" ", ""));
        }
        while (learnerDOBYear==null);

        // Month
        System.out.println("Select Month: ");
        int dobMonthOption = displayMenu(new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"}, "\t ");
        learnerDOBMonth = Integer.toString(dobMonthOption);

        // Day
        do {
            System.out.print("Enter Day [01-31]: ");
            learnerDOBDay = rm.validateDOBDay(userInput.nextLine().replace(" ", ""), learnerDOBMonth);
        }
        while (learnerDOBDay==null);

        // --> Getting Age
        learnerAge = rm.methodCalculateAgeFromCurrentDate(learnerDOBDay, learnerDOBMonth, learnerDOBYear);

        // --> Getting Emergency Contact Number
        do {
            System.out.print("\nEnter Emergency Contact Number: ");
            learnerContact = rm.validateContact(userInput.nextLine().replace(" ", ""));
        }
        while (learnerContact==null);

        // Setting New Learner Grade Automatic
        learnerGrade = 0;

        // Final checking: Is learner eligible to Register?
        if(learnerAge >= 4 && learnerAge<=11)
        {
            learnerID = generateLearnerID();

            System.out.println("\nID: " + learnerID +
                    " Name: " + learnerName +
                    " Gender: " + learnerGender +
                    " DOB: \"" + learnerDOBYear + "-" + learnerDOBMonth + "-" + learnerDOBDay + "\"" +
                    " Age: " + learnerAge +
                    " Contact: " + learnerContact +
                    " Grade: " + learnerGrade);
        }
        else
        {
            System.out.println("\nERROR: Learner is not eligible to register! \nREASON: Age must be between 4 and 11");
            System.out.println("\nDo you want to Try Again?");
            int menuTryAgainOption = displayMenu(new String[] {"Yes", "No"}, "\t\t");
            if(menuTryAgainOption == 1) functionRegisterLearner();
            else displayMainMenu();
        }

    }

    public String generateLearnerID()
    {
        JSONArray jsonArray = rm.readFromJSONFile("src\\data\\", "LearnersData.json");
        JSONObject lastObj = (JSONObject) jsonArray.getLast();
        String lastLearnerID = lastObj.get("learnerID").toString();
        int lastLearnerIDNum = Integer.parseInt(lastLearnerID.substring(3));
        return "SWL" + (lastLearnerIDNum + 1);
    }

}
