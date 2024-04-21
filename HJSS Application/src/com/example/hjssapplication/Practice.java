package com.example.hjssapplication;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//public class Practice {
//    Menu m = new Menu();
//    ReusableMethods rm = new ReusableMethods();
//    Timetable tt = new Timetable();
//    MainMenu mm = new MainMenu();
//    YesNoMenu ynm = new YesNoMenu();
//    Scanner userInput = new Scanner(System.in);
//    JSONObject selectedLesson = new JSONObject();
//    JSONObject learnerDetails = new JSONObject();
//    Learner learner = new Learner();
//    // Main Menu Function 1: Book a Swimming Lesson
//    public void functionBookASwimmingLesson() {
//        // Function Title
//        System.out.println("||===== Book a Swimming Lesson =====||\n");
//    }
//
//}

public class Practice {
    ReusableMethods rm = new ReusableMethods();
    Menu m = new Menu();
    public void displayMenuTimetableTypeCoach()
    {

        System.out.println("\nSelect a Coach:");
        JSONArray jsonArray = rm.readFromJSONFile("src\\data\\", "CoachesData.json");
        ArrayList<String> coachList = new ArrayList<>();
        for(int i=0; i<jsonArray.size(); i++)
        {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            coachList.add(jsonObject.get("coachName").toString());
        }
        int optionTimetableCoach = m.displayMenu(coachList.toArray(String[]::new), "\t\t");


        System.out.print(coachList.get(optionTimetableCoach-1));
    }
}
