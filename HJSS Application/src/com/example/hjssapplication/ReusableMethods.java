package com.example.hjssapplication;

import java.time.LocalDate;
import java.time.Period;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ReusableMethods {

    LocalDate currentDate = LocalDate.now();
    JSONParser jsonParser = new JSONParser();


    //Method: For Clearing The Screen
    public void clearConsole()
    {
    }

    //Method: Validating Name
    public String validateName(String name)
    {
        String regexName = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$";
        Pattern pattern = Pattern.compile(regexName);
        Matcher matcher = pattern.matcher(name);

        if(matcher.matches())
        {
            if (name.length()>=2 && name.length()<=60) return name;
            else System.out.println("Error: Name must contain minimum 2 alphabets!");
        }
        else System.out.println("ERROR: Please enter valid name!");
        return null;
    }

    //Method: Validating Number
    public boolean validateIsNumber(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //Method: Validating Gender
    public String validateGender(int genderMenuOption)
    {
        if(genderMenuOption == 1) return "male";
        else if(genderMenuOption == 2) return "female";
        else return "other";
    }

    //Method: Validating Age
    public String validateAge(String age)
    {
        int minAge = 4;
        int maxAge = 11;
        if(validateIsNumber(age))
        {
            if(Integer.parseInt(age) >= minAge && Integer.parseInt(age) <= maxAge) return age;
            else System.out.println("ERROR: Age must be between " + minAge + " and " + maxAge);
        }
        else System.out.println("ERROR: Age must be a number!");
        return null;
    }

    //Method: Validating Contact Number
    public String validateContact(String contact)
    {
        if(contact.length() == 10)
        {
            if(contact.startsWith("7"))
            {
                if(validateIsNumber(contact.substring(0,2)) && validateIsNumber(contact.substring(2))) return contact;
                else System.out.println("ERROR: Contact number is not correct!");
            }
            else System.out.println("ERROR: Contact number must start with 7!");
        }
        else System.out.println("ERROR: Contact number must be 10 digits long and cannot contain space!");

        return null;
    }

    // Method: Validating Learner ID
    public String validateLearnerID(String id)
    {
        JSONArray jsonArray = readFromJSONFile("src\\data\\", "PracticeLearners.json");
        String learnerID;
        String jsonLearnerID = "";
        if(validateIsNumber(id))
        {
            learnerID = "SWL" + id;
            for(int i=0; i<jsonArray.size(); i++)
            {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                if(jsonObject.get("learnerID").equals(learnerID)) jsonLearnerID = "SWL" + (i+1);
            }

            if (learnerID.equals(jsonLearnerID)) return learnerID;
            else System.out.println("ERROR: Not a valid Learner ID");
        }
        else System.out.println("ERROR: It must be a number");
        return null;
    }

    public Boolean validateGradeLevel(Lesson lesson, Learner learner)
    {
        int selectedLessonGrade = lesson.getLessonGrade();
        int learnerCurrentGrade = learner.getLearnerCurrentGradeLevel();

        if(learnerCurrentGrade == 0 && selectedLessonGrade == 1) return true;
        else if(learnerCurrentGrade != 0)
        {
            if(selectedLessonGrade == learnerCurrentGrade) return true;
            else if(selectedLessonGrade == (learnerCurrentGrade + 1)) return true;
            else System.out.println("ERROR: You can either select either Grade: " + learnerCurrentGrade + " or Grade: " + (learnerCurrentGrade + 1) + " lesson!");
        }
        else System.out.println("ERROR: You can select only Grade: " + 1 + " lesson!");
        return false;
    }

    public Boolean validatePreviousBooking(Lesson lesson, JSONObject learnerDetails)
    {
        String selectedLessonDate = lesson.getLessonDate();
        String selectedLessonStartTime = lesson.getLessonStartTime();
        String selectedLessonEndTime = lesson.getLessonEndTime();

        JSONObject learnerLessons = (JSONObject) learnerDetails.get("learnerLessons");
        JSONArray learnerBookedLessons = (JSONArray) learnerLessons.get("booked");

        for (int i=0; i < learnerBookedLessons.size(); i++)
        {
            JSONObject jsonObject = (JSONObject) learnerBookedLessons.get(i);
            if(jsonObject.get("lessonDate").equals(selectedLessonDate) && jsonObject.get("lessonStartTime").equals(selectedLessonStartTime) && jsonObject.get("lessonEndTime").equals(selectedLessonEndTime))
            {
                System.out.println("ERROR: You cannot book the same lesson twice!");
                return false;
            }
        }
        return true;
    }

    public Boolean validateLessonAvailableSlots(Lesson lesson)
    {
        int lessonAvailableSlots = lesson.getLessonSlots();
        if(lessonAvailableSlots > 0) return true;
        else System.out.println("ERROR: Sorry all lesson slots are full!");
        return false;
    }


    //Method: Read and Return Data From JSON
    public JSONArray readFromJSONFile(String filePath, String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(filePath + fileName);
            JSONArray jsonArray = (JSONArray) jsonParser.parse(fileReader);
            fileReader.close();
            return jsonArray;
        }
        catch (FileNotFoundException e) {
            System.out.println("ERROR: " + e);
            return null;
        }
        catch (IOException | ParseException e) {
            System.out.println("ERROR: " + e);
            return null;
        }
    }

    //Method: Write To JSON File
    public Boolean writeInJSONFile(String filePath, String fileName, JSONObject newObject)
    {
        JSONArray jsonArray = readFromJSONFile(filePath, fileName);
        jsonArray.addLast(newObject);
        try
        {
            FileWriter fileWriter = new FileWriter(filePath + fileName);
            fileWriter.write(jsonArray.toJSONString());
            fileWriter.flush();
            fileWriter.close();
            return true;
        }
        catch (FileNotFoundException e) {
            System.out.println("ERROR: " + e);
            return false;
        }
        catch (IOException e) {
            System.out.println("ERROR: " + e);
            return false;
        }
    }

    public Boolean updateInJSONFile(String filePath, String fileName, int previousIndex, JSONObject newObject)
    {
        JSONArray jsonArray = readFromJSONFile(filePath, fileName);
        jsonArray.remove(previousIndex);
        jsonArray.add(previousIndex, newObject);

        try {
            FileWriter fileWriter = new FileWriter(filePath + fileName);
            fileWriter.write(jsonArray.toJSONString());
            fileWriter.flush();
            fileWriter.close();
            return true;
        }
        catch (FileNotFoundException e) {
            System.out.println("ERROR: " + e);
            return false;
        }
        catch (IOException e) {
            System.out.println("ERROR: " + e);
            return false;
        }
    }

    public int getPreviousIndex(String filePath, String fileName, JSONObject previousObject)
    {
        JSONArray jsonArray = readFromJSONFile(filePath, fileName);
        int previousIndex = 0;
        for(int i=0; i < jsonArray.size(); i++)
        {
            if(jsonArray.get(i).equals(previousObject)) previousIndex = i;
        }
        return previousIndex;
    }
}
