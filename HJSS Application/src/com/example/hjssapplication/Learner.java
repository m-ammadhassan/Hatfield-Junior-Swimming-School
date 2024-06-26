package com.example.hjssapplication;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Learner {
    Scanner userInput = new Scanner(System.in);
    Menu m = new Menu();
    ReusableMethods rm = new ReusableMethods();
    private String learnerID;
    private String learnerName;
    private String learnerGender;
    private int learnerAge;
    private String learnerEmergencyContactNumber;
    private int learnerCurrentGradeLevel;

    Learner()
    {
        this.setLearnerID("");
    }
    Learner(String id, String name, String gender, int age, String contact, int level)
    {
        this.setLearnerID(id);
        this.setLearnerName(name);
        this.setLearnerGender(gender);
        this.setLearnerAge(age);
        this.setLearnerEmergencyContactNumber(contact);
        this.setLearnerCurrentGradeLevel(level);
    }

    public String getLearnerID() {
        return learnerID;
    }

    public void setLearnerID(String id) {
        this.learnerID = id;
    }

    public String getLearnerName() {
        return learnerName;
    }

    public void setLearnerName(String name) {this.learnerName = name;}

    public String getLearnerGender() {
        return learnerGender;
    }

    public void setLearnerGender(String gender) {
        this.learnerGender = gender;
    }

    public int getLearnerAge() {
        return learnerAge;
    }

    public void setLearnerAge(int learnerAge) {
        this.learnerAge = learnerAge;
    }

    public String getLearnerEmergencyContactNumber() {
        return learnerEmergencyContactNumber;
    }

    public void setLearnerEmergencyContactNumber(String contact) {
        this.learnerEmergencyContactNumber = contact;
    }

    public int getLearnerCurrentGradeLevel() {
        return learnerCurrentGradeLevel;
    }

    public void setLearnerCurrentGradeLevel(int level) {
        this.learnerCurrentGradeLevel = level;
    }

    public String generateLearnerID()
    {
        JSONArray arrayOfLearners = rm.readFromJSONFile("src\\data\\", "PracticeLearners.json");
        JSONObject lastLearner = (JSONObject) arrayOfLearners.getLast();
        String lastLearnerID = lastLearner.get("learnerID").toString();
        int lastLearnerIDNum = Integer.parseInt(lastLearnerID.substring(3));
        return "SWL" + (lastLearnerIDNum + 1);
    }

    public void setNewLearnerDetails()
    {
        // Declaring Local Variables
        String learnerName, learnerGender, learnerAge, learnerContact;
        int learnerGrade;

        // --> Name
        do{
            System.out.print("Enter Name: ");
            learnerName = rm.validateName(userInput.nextLine());
        }
        while (learnerName==null);
        setLearnerName(learnerName);

        // --> Gender
        System.out.println("\nSelect Gender: ");
        learnerGender = rm.validateGender(m.displayMenu(new String[] {"Male", "Female", "Other"}, "\t\t"));
        setLearnerGender(learnerGender);

        // --> Age
        do{
            System.out.print("\nEnter Age: ");
            learnerAge = rm.validateAge(userInput.nextLine().replace(" ", ""));
        }
        while (learnerAge==null);
        setLearnerAge(Integer.parseInt(learnerAge));

        // --> Emergency Contact Number
        do {
            System.out.print("\nEnter Emergency Contact Number: ");
            learnerContact = rm.validateContact(userInput.nextLine().replace(" ", ""));
        }
        while (learnerContact==null);
        setLearnerEmergencyContactNumber(learnerContact);

        // Setting New Learner Grade Automatically
        learnerGrade = 0;
        setLearnerCurrentGradeLevel(learnerGrade);

        // Setting New Learner ID Automatically
        String learnerID = generateLearnerID();
        setLearnerID(learnerID);
    }

    public JSONObject methodAddNewLearner()
    {
        JSONObject newLearnerObject = new JSONObject();
        JSONObject newLearnerLessonsObject = new JSONObject();
        JSONArray newLearnerBookedLessonsArray = new JSONArray();
        JSONArray newLearnerAttendedLessonsArray = new JSONArray();
        JSONArray newLearnerCancelledLessonsArray = new JSONArray();

        newLearnerObject.put("learnerID", getLearnerID());
        newLearnerObject.put("learnerName", getLearnerName());
        newLearnerObject.put("learnerGender", getLearnerGender());
        newLearnerObject.put("learnerAge", getLearnerAge());
        newLearnerObject.put("learnerEmergencyContact", getLearnerEmergencyContactNumber());
        newLearnerObject.put("learnerCurrentGrade", getLearnerCurrentGradeLevel());
        newLearnerObject.put("learnerLessons", newLearnerLessonsObject);

        newLearnerLessonsObject.put("booked", newLearnerBookedLessonsArray);
        newLearnerLessonsObject.put("attended", newLearnerAttendedLessonsArray);
        newLearnerLessonsObject.put("cancelled", newLearnerCancelledLessonsArray);

        if(rm.writeInJSONFile( "src\\data\\", "PracticeLearners.json", newLearnerObject)) return newLearnerObject;
        else System.out.println("ERROR: Sorry! some error occurred while registering a new learner.");
        return null;
    }

    public void getLearnerDetails()
    {
        System.out.println(
                "\n=====================================================================================" +
                "\n\t\t\t\t\t\t\tLearner Details" +
                "\n=====================================================================================" +
                "\nLearner ID: " + getLearnerID() +
                "\t\t Learner Name: " + getLearnerName() +
                "\t\t Learner Age: " + getLearnerAge() +
                "\nLearner Gender: " + getLearnerGender() +
                "\t\t Learner Grade: " + getLearnerCurrentGradeLevel() +
                "\t\t Learner Emergency No.: " + getLearnerEmergencyContactNumber() +
                "\n=====================================================================================");
    }

    public JSONObject setLearnerDetailsFromJSON()
    {
        String learnerID = getLearnerID();
        JSONArray arrayOfLearners = rm.readFromJSONFile("src\\data\\", "PracticeLearners.json");
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
        // Search in array of Learners to get the details of the Learner
        for(int i=0; i < arrayOfLearners.size(); i++)
        {
            JSONObject jsonObject = (JSONObject) arrayOfLearners.get(i);
            if(jsonObject.get("learnerID").equals(learnerID)) learnerDetails = (JSONObject) arrayOfLearners.get(i);
        }

        setLearnerID(learnerDetails.get("learnerID").toString());
        setLearnerName(learnerDetails.get("learnerName").toString());
        setLearnerGender(learnerDetails.get("learnerGender").toString());
        setLearnerAge(Integer.parseInt(learnerDetails.get("learnerAge").toString()));
        setLearnerEmergencyContactNumber(learnerDetails.get("learnerEmergencyContact").toString());
        setLearnerCurrentGradeLevel(Integer.parseInt(learnerDetails.get("learnerCurrentGrade").toString()));

        return learnerDetails;
    }

    public Boolean methodUpdateLearnerGradeLevel(JSONObject lessonAttended, JSONObject selectedLearner)
    {
        JSONArray arrayOfLearners = rm.readFromJSONFile("src\\data\\", "PracticeLearners.json");
        int indexOfLearner = rm.getIndex(arrayOfLearners, selectedLearner);

        if(Integer.parseInt(lessonAttended.get("lessonGrade").toString()) > getLearnerCurrentGradeLevel())
        {
            setLearnerCurrentGradeLevel(getLearnerCurrentGradeLevel() + 1);
        }

        selectedLearner.remove("learnerCurrentGrade");
        selectedLearner.put("learnerCurrentGrade", getLearnerCurrentGradeLevel());

        if(rm.updateInJSONFile("src\\data\\", "PracticeLearners.json", indexOfLearner, selectedLearner)) return true;
        else System.out.println("ERROR: Sorry! some error occurred while updating learner grade.");
        return false;
    }

}
