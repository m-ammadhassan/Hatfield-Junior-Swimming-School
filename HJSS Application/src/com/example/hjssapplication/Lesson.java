package com.example.hjssapplication;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.LocalDate;
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

    public void methodUpdateSelectedLessonSlots(JSONObject selectedLesson, Lesson lesson, String lessonType)
    {
        JSONArray arrayOfLessons = rm.readFromJSONFile("src\\data\\", "PracticeLessons.json");
        int indexOfLesson = rm.getIndex(arrayOfLessons, selectedLesson);

        selectedLesson.remove("lessonSlots");
        if(lessonType.equals("booking")) selectedLesson.put("lessonSlots", (lesson.getLessonSlots() - 1));
        else if(lessonType.equals("cancel") || lessonType.equals("change")) selectedLesson.put("lessonSlots", (lesson.getLessonSlots() + 1));

        rm.updateInJSONFile("src\\data\\", "PracticeLessons.json", indexOfLesson, selectedLesson);
    }

    public JSONObject methodAddLessonInLearnerBookedLessons(Lesson lesson, JSONObject selectedLesson, JSONObject selectedLearner)
    {
        JSONArray arrayOfLearners = rm.readFromJSONFile("src\\data\\", "PracticeLearners.json");
        int indexOfLearner = rm.getIndex(arrayOfLearners, selectedLearner);

        JSONObject learnerLessons = (JSONObject) selectedLearner.get("learnerLessons");
        JSONArray learnerBookedLessons = (JSONArray) learnerLessons.get("booked");

        JSONObject lessonBooked = new JSONObject();
        lessonBooked.put("bookingID", methodGenerateBookingID());
        lessonBooked.put("lessonDate", lesson.getLessonDate());
        lessonBooked.put("lessonDay", lesson.getLessonDay());
        lessonBooked.put("lessonStartTime", lesson.getLessonStartTime());
        lessonBooked.put("lessonEndTime", lesson.getLessonEndTime());
        lessonBooked.put("lessonGrade", lesson.getLessonGrade());
        lessonBooked.put("coachName", lesson.getLessonCoach());
        lessonBooked.put("coachID", selectedLesson.get("coachID"));

        learnerBookedLessons.add(lessonBooked);

        if(rm.updateInJSONFile("src\\data\\", "PracticeLearners.json", indexOfLearner, selectedLearner)) return lessonBooked;
        else System.out.println("ERROR: Sorry! some error occurred while booking.");
        return null;
    }

    public JSONObject methodAddLessonInLearnerAttendedLessons(JSONObject selectedLesson, JSONObject selectedLearner, Review review)
    {
        JSONArray arrayOfLearners = rm.readFromJSONFile("src\\data\\", "PracticeLearners.json");
        int indexOfLearner = rm.getIndex(arrayOfLearners, selectedLearner);

        JSONObject learnerLessons = (JSONObject) selectedLearner.get("learnerLessons");
        JSONArray learnerBookedLessons = (JSONArray) learnerLessons.get("booked");
        JSONArray learnerAttendedLessons = (JSONArray) learnerLessons.get("attended");
        int indexOfLesson = rm.getIndex(learnerBookedLessons, selectedLesson);

        JSONObject lessonAttended = (JSONObject) learnerBookedLessons.get(indexOfLesson);

        lessonAttended.put("lessonReviewRating", review.getReviewRating());
        lessonAttended.put("lessonReviewMessage", review.getReviewMessage());

        learnerAttendedLessons.add(lessonAttended);
        learnerBookedLessons.remove(indexOfLesson);

        if(rm.updateInJSONFile("src\\data\\", "PracticeLearners.json", indexOfLearner, selectedLearner)) return lessonAttended;
        else System.out.println("ERROR: Sorry! some error occurred while making lesson attended.");
        return null;
    }

    public void methodUpdateBookedLessonsOnGradeUpgrade(JSONObject selectedLearner)
    {
        JSONArray arrayOfLearners = rm.readFromJSONFile("src\\data\\", "PracticeLearners.json");
        int indexOfLearner = rm.getIndex(arrayOfLearners, selectedLearner);

        JSONObject learnerLessons = (JSONObject) selectedLearner.get("learnerLessons");
        JSONArray learnerBookedLessons = (JSONArray) learnerLessons.get("booked");

        JSONArray arrayOfLessonsToDelete = new JSONArray();

        for(int i=0; i<learnerBookedLessons.size(); i++)
        {
            JSONObject jsonObject = (JSONObject) learnerBookedLessons.get(i);
            LocalDate lessonDate = LocalDate.parse(jsonObject.get("lessonDate").toString(), rm.formatDate);
            if(rm.currentDate.isBefore(lessonDate) && Integer.parseInt(jsonObject.get("lessonGrade").toString()) < Integer.parseInt(selectedLearner.get("learnerCurrentGrade").toString()))
            {
                arrayOfLessonsToDelete.add(jsonObject);
            }
        }

        for(int i=0; i<arrayOfLessonsToDelete.size(); i++)
        {
            learnerBookedLessons.remove(arrayOfLessonsToDelete.get(i));
        }

        if(rm.updateInJSONFile("src\\data\\", "PracticeLearners.json", indexOfLearner, selectedLearner)) {
            System.out.println("ERROR: Sorry! some error occurred while deleting lessons.");
        }
    }

    public String methodGenerateBookingID()
    {
        UUID randomUUID = UUID.randomUUID();
        return randomUUID.toString().substring(0, 8);
    }

    public void getBookedLessonDetails(Learner learner, JSONObject lessonBooked)
    {
        System.out.println(
                "\n=====================================================================================" +
                "\n\t\t\t\t\t\t\tBooking Details" +
                "\n=====================================================================================" +
                "\nLearner Name: " + learner.getLearnerName() +
                "\t\t Booking ID: " + lessonBooked.get("bookingID") +
                "\t\t Lesson Date: " + getLessonDate() +
                "\nLearner Grade: " + learner.getLearnerCurrentGradeLevel() +
                "\t\t Lesson Grade: " + getLessonGrade() +
                "\t\t Coach Name: " + getLessonCoach() +
                "\nLesson Start Time: " + getLessonStartTime() +
                "\t\t Lesson End Time: " + getLessonEndTime() +
                "\n=====================================================================================");
    }

    public void getAttendedLessonDetails(Learner learner, JSONObject lessonAttended)
    {
        System.out.println(
                "\n=====================================================================================" +
                        "\n\t\t\t\t\t\t\tAttended Lesson Details" +
                        "\n=====================================================================================" +
                        "\nLearner Name: " + learner.getLearnerName() +
                        "\t\t Booking ID: " + lessonAttended.get("bookingID") +
                        "\t\t Lesson Date: " + getLessonDate() +
                        "\nLearner Grade: " + learner.getLearnerCurrentGradeLevel() +
                        "\t\t Lesson Grade: " + getLessonGrade() +
                        "\t\t Coach Name: " + getLessonCoach() +
                        "\nLesson Rating: " + lessonAttended.get("lessonReviewRating") +
                        "\nLesson Review: " + lessonAttended.get("lessonReviewMessage") +
                        "\n=====================================================================================");
    }

    public void setLessonDetails()
    {

    }
}
