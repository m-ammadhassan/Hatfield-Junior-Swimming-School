package com.example.hjssapplication;

import java.util.Scanner;

public class Learner {
    Scanner userInput = new Scanner(System.in);
    private String learnerID;
    private String learnerName;
    private String learnerGender;
    private String learnerEmergencyContactNumber;
    private int learnerCurrentGradeLevel;

//    Learner(String id, String name, String gender, String contact, int level)
//    {
//        this.setLearnerID(id);
//        this.setLearnerName(name);
//        this.setLearnerGender(gender);
//        this.setLearnerEmergencyContactNumber(contact);
//        this.setLearnerCurrentGradeLevel(level);
//    }

    Learner()
    {
        learnerID = "SWL0";
        learnerName = "Learner0";
        learnerGender = "other";
        learnerEmergencyContactNumber = "70000000000";
        learnerCurrentGradeLevel = 0;
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
}
