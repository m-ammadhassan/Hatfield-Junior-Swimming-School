package com.example.hjssapplication;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
    LocalTime currentTime = LocalTime.now();
    DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("ha");

    //Method: For Clearing The Screen
    public void clearConsole()
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    //Method: Validating Name
    public boolean validateName(String name)
    {
        String regexOfName = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$";

        if(name.matches(regexOfName))
        {
            if (name.length()>=2 && name.length()<=60) return true;
            else System.out.println("Error: Name must contain minimum 2 alphabets!");
        }
        else System.out.println("ERROR: Please enter valid name!");
        return false;
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

    //Method: Validating Age
    public boolean validateAge(String age)
    {
        int minAge = 4;
        int maxAge = 11;
        if(validateIsNumber(age))
        {
            if(Integer.parseInt(age) >= minAge && Integer.parseInt(age) <= maxAge) return true;
            else System.out.println("ERROR: Age must be between " + minAge + " and " + maxAge);
        }
        else System.out.println("ERROR: Age must be a number!");
        return false;
    }

    //Method: Validating Contact Number
    public boolean validateContact(String contact)
    {
        if(contact.length() == 10)
        {
            if(contact.startsWith("7"))
            {
                if(validateIsNumber(contact.substring(0,2)) && validateIsNumber(contact.substring(2))) return true;
                else System.out.println("ERROR: Contact number is not correct!");
            }
            else System.out.println("ERROR: Contact number must start with 7!");
        }
        else System.out.println("ERROR: Contact number must be 10 digits long and cannot contain space!");

        return false;
    }

    // Method: Validating Learner ID
    public String validateLearnerID(String id)
    {
        JSONArray jsonArray = readFromJSONFile("src\\data\\", "LearnersData.json");
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

    public boolean validateBookLesson(Lesson lesson, Learner learner, JSONObject selectedLearner)
    {
        if(!validateGradeLevel(lesson, learner)) return false;

        if(!validatePreviousBooking(lesson, selectedLearner)) return false;

        if(!validateLessonAvailableSlots(lesson)) return false;

        return true;
    }

    public boolean validateGradeLevel(Lesson lesson, Learner learner)
    {
        if(learner.getLearnerCurrentGradeLevel() == 0 && lesson.getLessonGrade() == 1) return true;
        else if(learner.getLearnerCurrentGradeLevel() != 0)
        {
            if(lesson.getLessonGrade() == learner.getLearnerCurrentGradeLevel()) return true;
            else if(lesson.getLessonGrade() == (learner.getLearnerCurrentGradeLevel() + 1)) return true;
            else System.out.println("ERROR: You can either select either Grade: " + learner.getLearnerCurrentGradeLevel() + " or Grade: " + (learner.getLearnerCurrentGradeLevel() + 1) + " lesson!");
        }
        else System.out.println("ERROR: You can select only Grade: " + 1 + " lesson!");
        return false;
    }

    public boolean validatePreviousBooking(Lesson lesson, JSONObject selectedLearner)
    {
        JSONObject learnerLessons = (JSONObject) selectedLearner.get("learnerLessons");
        JSONArray learnerBookedLessons = (JSONArray) learnerLessons.get("booked");

        for (int i=0; i < learnerBookedLessons.size(); i++)
        {
            JSONObject jsonObject = (JSONObject) learnerBookedLessons.get(i);
            if(jsonObject.get("lessonDate").equals(lesson.getLessonDate()) && jsonObject.get("lessonStartTime").equals(lesson.getLessonStartTime()))
            {
                System.out.println("ERROR: You cannot book the same lesson twice!");
                return false;
            }
        }
        return true;
    }

    public boolean validateLessonAvailableSlots(Lesson lesson)
    {
        if(lesson.getLessonSlots() > 0 && lesson.getLessonSlots() < 5) return true;
        else System.out.println("ERROR: Sorry all lesson slots are full!");
        return false;
    }

    public boolean validateAttendLessonTime(Lesson lesson)
    {
        LocalDate lessonDate = LocalDate.parse(lesson.getLessonDate(), formatDate);
        LocalTime lessonEndTime = LocalTime.parse(lesson.getLessonEndTime(), formatTime);

        if((currentDate.isEqual(lessonDate) && currentTime.isAfter(lessonEndTime)) || currentDate.isAfter(lessonDate))
        {
            return true;
        }
        else System.out.println("ERROR: You cannot attend lesson before it's delivered!");
        return false;
    }

    public boolean validateChangeCancelLessonTime(Lesson lesson)
    {
        LocalDate lessonDate = LocalDate.parse(lesson.getLessonDate(), formatDate);
        LocalTime lessonStartTime = LocalTime.parse(lesson.getLessonStartTime(), formatTime);
        if((currentDate.isEqual(lessonDate) && currentTime.isBefore(lessonStartTime)) || currentDate.isBefore(lessonDate))
        {
            return true;
        }
        else System.out.println("ERROR: You cannot change or cancel a lesson after it's delivered!");
        return false;
    }


    //Method: Read and Return Data From JSON
    public JSONArray readFromJSONFile(String filePath, String fileName)
    {
        JSONParser jsonParser = new JSONParser();
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
    public boolean writeInJSONFile(String filePath, String fileName, JSONObject newObject)
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

    public boolean updateInJSONFile(String filePath, String fileName, int previousIndex, JSONObject newObject)
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

    public int getIndex(JSONArray array, JSONObject object)
    {
        for(int i=0; i<array.size(); i++)
        {
            if(array.get(i).equals(object)) return i;
        }
        return 0;
    }
}

