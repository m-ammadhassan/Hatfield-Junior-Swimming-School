package com.example.hjssapplication;

import java.util.Scanner;
import org.json.simple.JSONArray;

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

    }

    public String generateLearnerID()
    {
        JSONArray jsonArray = rm.readFromJSONFile("src\\data\\", "LearnersData.json");
        return "SWL" + (jsonArray.size()+1);
    }

}
