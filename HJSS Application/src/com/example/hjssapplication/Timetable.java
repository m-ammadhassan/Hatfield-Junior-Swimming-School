package com.example.hjssapplication;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Timetable {

    Menu m = new Menu();
    ReusableMethods rm = new ReusableMethods();
    private JSONArray timetable = new JSONArray();

    LocalDate currentDate = LocalDate.now();
    DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public JSONArray getTimetable() {
        return timetable;
    }

    public void setTimetable(JSONArray timetable) {
        this.timetable = timetable;
    }

    // Method: Display and Ask user for the Type of Timetable
    public int displayMenuTimetableType()
    {
        System.out.println("Select Option To Display Timetable: ");
        String[] arrayTimetableTypeMenu = {"By Day", "By Grade", "By Coach"};
        int optionTimetableType = m.displayMenu(arrayTimetableTypeMenu, "\n");
        return optionTimetableType;
    }

    public void actionOnMenuTimetableType(int optionTimetableType)
    {
        switch(optionTimetableType)
        {
            case 1:
                displayMenuTimetableTypeDay();
                break;
            case 2:
                displayMenuTimetableTypeGrade();
                break;
            case 3:
                displayMenuTimetableTypeCoach();
                break;
            default:
                System.out.println("ERROR: Please select correct option!");
        }
    }

    // Display sub-options of selected Timetable Type: DAY
    public void displayMenuTimetableTypeDay()
    {
        String timetableDay = "";
        System.out.println("Select a Day:");
        String[] arrayTimetableDays = {"Monday", "Wednesday", "Friday", "Saturday"};
        int optionTimetableDay = m.displayMenu(arrayTimetableDays, "\t\t");
        for(int i=0; i<arrayTimetableDays.length; i++)
        {
            if(optionTimetableDay == (i+1)) timetableDay = arrayTimetableDays[i];
        }

        setTimetable(generateTimetable("lessonDay", timetableDay));
    }

    // Display sub-options of selected Timetable Type: GRADE
    public void displayMenuTimetableTypeGrade()
    {
        System.out.println("\nSelect a Grade:");
        String[] arrayTimetableGrade = {"Grade 1", "Grade 2", "Grade 3", "Grade 4", "Grade 5"};
        int timetableGrade = m.displayMenu(arrayTimetableGrade, "\t\t");
        setTimetable(generateTimetable("lessonGrade", Integer.toString(timetableGrade)));
    }

    // Display sub-options of selected Timetable Type: COACH
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

        String timetableCoach = "";
        for(int i=0; i<coachList.size(); i++)
        {
            if(optionTimetableCoach == (i+1)) timetableCoach = coachList.get(i);
        }

        setTimetable(generateTimetable("lessonCoach", timetableCoach));
    }

    public JSONArray generateTimetable(String key, String value)
    {
        JSONArray jsonArray = rm.readFromJSONFile("src\\data\\", "PracticeLessons.json");
        JSONArray timetable = new JSONArray();
        // For Loop To Select All Lessons Which Are Equal To Selected Day / Grade / Coach
        for(int i=0; i<jsonArray.size(); i++)
        {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            if(jsonObject.get(key).equals(value))
            {
                // Inner For Loop To Select The Lessons Coming In Seven Days
                for(int x=0; x<7; x++)
                {
                    String getDate = currentDate.plusDays(x).format(formatDate);
                    if(jsonObject.get("lessonDate").equals(getDate))
                    {
                        timetable.add(jsonObject);
                    }
                }

            }
        }
        if(!timetable.isEmpty()) return timetable;
        else System.out.println("ERROR: No timetable found!");
        return null;
    }

    public void displayTimetable(JSONArray timetable)
    {
        System.out.println("\n\n||======||======||======|| TIMETABLE ||======||======||======||\n");
        for(int i=0; i<timetable.size(); i++)
        {
            JSONObject jsonObject = (JSONObject) timetable.get(i);
            System.out.println((i+1) + " - DATE --> " + jsonObject.get("lessonDay") +
                    "\t\t DAY --> " + jsonObject.get("lessonDate") +
                    "\t\t TIME --> " + jsonObject.get("lessonStartTime") + "-" + jsonObject.get("lessonEndTime") +
                    "\n\tGRADE --> " + jsonObject.get("lessonGrade") +
                    "\t\t COACH --> " + jsonObject.get("lessonCoach") +
                    "\n\tAVAILABLE SLOTS --> " + jsonObject.get("lessonSlots") + "\n");
        }
    }
}
