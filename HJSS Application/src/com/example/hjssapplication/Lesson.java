package com.example.hjssapplication;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.UUID;

public class Lesson {
    private String lessonDate;
    private String lessonDay;
    private String lessonStartTime;
    private String lessonEndTime;
    private int lessonGrade;
    private String lessonCoach;
    private int lessonSlots;

    Timetable tt = new Timetable();
    ReusableMethods rm = new ReusableMethods();

    Lesson()
    {}

    Lesson(String lessonDate, String lessonDay, String lessonStartTime, String lessonEndTime, int lessonGrade, String lessonCoach, int lessonSlots)
    {
        this.setLessonDate(lessonDate);
        this.setLessonDay(lessonDay);
        this.setLessonStartTime(lessonStartTime);
        this.setLessonEndTime(lessonEndTime);
        this.setLessonGrade(lessonGrade);
        this.setLessonCoach(lessonCoach);
        this.setLessonSlots(lessonSlots);
    }

    public String getLessonDate() {
        return lessonDate;
    }

    public void setLessonDate(String lessonDate) {
        this.lessonDate = lessonDate;
    }

    public String getLessonDay() {
        return lessonDay;
    }

    public void setLessonDay(String lessonDay) {
        this.lessonDay = lessonDay;
    }

    public String getLessonStartTime() {
        return lessonStartTime;
    }

    public void setLessonStartTime(String lessonStartTime) {
        this.lessonStartTime = lessonStartTime;
    }

    public String getLessonEndTime() {
        return lessonEndTime;
    }

    public void setLessonEndTime(String lessonEndTime) {
        this.lessonEndTime = lessonEndTime;
    }

    public int getLessonGrade() {
        return lessonGrade;
    }

    public void setLessonGrade(int lessonGrade) {
        this.lessonGrade = lessonGrade;
    }

    public String getLessonCoach() {
        return lessonCoach;
    }

    public void setLessonCoach(String lessonCoach) {
        this.lessonCoach = lessonCoach;
    }

    public int getLessonSlots() {
        return lessonSlots;
    }

    public void setLessonSlots(int lessonSlots) {
        this.lessonSlots = lessonSlots;
    }

    public void methodUpdateSelectedLessonSlots(JSONObject selectedLesson, Lesson lesson)
    {
        JSONObject lessonNewData = selectedLesson;
        int previousIndex = rm.getPreviousIndex("src\\data\\", "PracticeLessons.json", selectedLesson);

        lessonNewData.remove("lessonSlots");
        lessonNewData.put("lessonSlots", (lesson.getLessonSlots() - 1));

        rm.updateInJSONFile("src\\data\\", "PracticeLessons.json", previousIndex, lessonNewData);
    }

    public JSONObject methodAddLessonInLearnerBookedLesson(Lesson lesson, JSONObject selectedLearner)
    {
        JSONObject learnerNewData = selectedLearner;
        int previousIndex = rm.getPreviousIndex("src\\data\\", "PracticeLearners.json", selectedLearner);

        JSONObject learnerLessons = (JSONObject) learnerNewData.get("learnerLessons");
        JSONArray learnerBookedLessons = (JSONArray) learnerLessons.get("booked");

        JSONObject newBooking = new JSONObject();
        newBooking.put("bookingID", methodGenerateBookingID());
        newBooking.put("lessonDate", lesson.getLessonDate());
        newBooking.put("lessonDay", lesson.getLessonDay());
        newBooking.put("lessonStartTime", lesson.getLessonStartTime());
        newBooking.put("lessonEndTime", lesson.getLessonEndTime());
        newBooking.put("lessonGrade", lesson.getLessonGrade());
        newBooking.put("lessonCoach", lesson.getLessonCoach());

        learnerBookedLessons.add(newBooking);

        if(rm.updateInJSONFile("src\\data\\", "PracticeLearners.json", previousIndex, learnerNewData)) return newBooking;
        else System.out.println("ERROR: Sorry! some error occurred while booking.");
        return null;
    }

    public String methodGenerateBookingID()
    {
        UUID randomUUID = UUID.randomUUID();
        return randomUUID.toString().substring(0, 8);
    }

    public void getBookedLessonDetails(Learner learner, JSONObject newBooking)
    {
        System.out.println(
                "\n=====================================================================================" +
                "\n\t\t\t\t\t\t\tBooking Details" +
                "\n=====================================================================================" +
                "\nLearner Name: " + learner.getLearnerName() +
                "\t\t Booking ID: " + newBooking.get("bookingID") +
                "\t\t Lesson Date: " + getLessonDate() +
                "\nLearner Grade: " + learner.getLearnerCurrentGradeLevel() +
                "\t\t Lesson Grade: " + getLessonGrade() +
                "\t\t Coach Name: " + getLessonCoach() +
                "\nLesson Start Time: " + getLessonStartTime() +
                "\t\t Lesson End Time: " + getLessonEndTime() +
                "\n=====================================================================================");
    }

    public void setLessonDetails()
    {

    }
}
