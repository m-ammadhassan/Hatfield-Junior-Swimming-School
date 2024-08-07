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

    Menu m = new Menu();
    ReusableMethods rm = new ReusableMethods();

    Lesson() {}

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

    public void methodUpdateSelectedLessonSlots(JSONObject selectedLesson, String lessonType)
    {
        JSONArray arrayOfLessons = rm.readFromJSONFile("src\\data\\", "LessonsData.json");
        int indexOfLesson = 0;

        if(lessonType.equals("book"))
        {
            indexOfLesson = rm.getIndex(arrayOfLessons, selectedLesson);
            selectedLesson.remove("lessonGrade");
            selectedLesson.remove("lessonSlots");
            selectedLesson.put("lessonGrade", Integer.toString(getLessonGrade()));
            selectedLesson.put("lessonSlots", (getLessonSlots() - 1));
        }
        else if(lessonType.equals("cancel") || lessonType.equals("change"))
        {
            selectedLesson.remove("bookingID");
            selectedLesson.remove("lessonGrade");
            int previousSlots = 0;
            for(int i=0; i<arrayOfLessons.size(); i++)
            {
                JSONObject jsonObject = (JSONObject) arrayOfLessons.get(i);
                if(jsonObject.get("lessonDate").equals(selectedLesson.get("lessonDate")) && jsonObject.get("lessonStartTime").equals(selectedLesson.get("lessonStartTime")))
                {
                    previousSlots = Integer.parseInt(jsonObject.get("lessonSlots").toString());
                    indexOfLesson = i;
                }
            }
            selectedLesson.put("lessonGrade", Integer.toString(getLessonGrade()));
            selectedLesson.put("lessonSlots", previousSlots + 1);
        }

        rm.updateInJSONFile("src\\data\\", "LessonsData.json", indexOfLesson, selectedLesson);
    }

    public JSONObject methodAddLessonInLearnerBookedLessons(JSONObject selectedLesson, JSONObject selectedLearner)
    {
        JSONArray arrayOfLearners = rm.readFromJSONFile("src\\data\\", "LearnersData.json");
        int indexOfLearner = rm.getIndex(arrayOfLearners, selectedLearner);

        JSONObject learnerLessons = (JSONObject) selectedLearner.get("learnerLessons");
        JSONArray learnerBookedLessons = (JSONArray) learnerLessons.get("booked");

        JSONObject lessonBooked = new JSONObject();
        lessonBooked.put("bookingID", methodGenerateBookingID());
        lessonBooked.put("lessonDate", getLessonDate());
        lessonBooked.put("lessonDay", getLessonDay());
        lessonBooked.put("lessonStartTime", getLessonStartTime());
        lessonBooked.put("lessonEndTime", getLessonEndTime());
        lessonBooked.put("lessonGrade", getLessonGrade());
        lessonBooked.put("coachName", getLessonCoach());
        lessonBooked.put("coachID", selectedLesson.get("coachID"));

        learnerBookedLessons.add(lessonBooked);

        if(rm.updateInJSONFile("src\\data\\", "LearnersData.json", indexOfLearner, selectedLearner)) return lessonBooked;
        else System.out.println("ERROR: Sorry! some error occurred while booking.");
        return null;
    }

    public JSONObject methodAddLessonInLearnerAttendedLessons(JSONObject selectedLesson, JSONObject selectedLearner, Review review)
    {
        JSONArray arrayOfLearners = rm.readFromJSONFile("src\\data\\", "LearnersData.json");
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

        if(rm.updateInJSONFile("src\\data\\", "LearnersData.json", indexOfLearner, selectedLearner)) return lessonAttended;
        else System.out.println("ERROR: Sorry! some error occurred while marking lesson attended.");
        return null;
    }

    public JSONObject methodAddLessonInLearnerCancelledLessons(JSONObject selectedLesson, JSONObject selectedLearner)
    {
        JSONArray arrayOfLearners = rm.readFromJSONFile("src\\data\\", "LearnersData.json");
        int indexOfLearner = rm.getIndex(arrayOfLearners, selectedLearner);

        JSONObject learnerLessons = (JSONObject) selectedLearner.get("learnerLessons");
        JSONArray learnerBookedLessons = (JSONArray) learnerLessons.get("booked");
        JSONArray learnerCancelledLessons = (JSONArray) learnerLessons.get("cancelled");
        int indexOfLesson = rm.getIndex(learnerBookedLessons, selectedLesson);
        String previousBookingID = selectedLesson.get("bookingID").toString();

        JSONObject lessonCancelled = (JSONObject) learnerBookedLessons.get(indexOfLesson);

        methodUpdateSelectedLessonSlots(selectedLesson, "cancel");

        lessonCancelled.put("bookingID", previousBookingID);
        learnerCancelledLessons.add(lessonCancelled);
        learnerBookedLessons.remove(indexOfLesson);

        if(rm.updateInJSONFile("src\\data\\", "LearnersData.json", indexOfLearner, selectedLearner)) return lessonCancelled;
        else System.out.println("ERROR: Sorry! some error occurred while cancelling lesson.");
        return null;
    }

    public JSONObject methodUpdateLessonInLearnerBookedLessons(JSONObject previousLesson, JSONObject newLesson, JSONObject selectedLearner)
    {
        JSONArray arrayOfLearners = rm.readFromJSONFile("src\\data\\", "LearnersData.json");
        int indexOfLearner = rm.getIndex(arrayOfLearners, selectedLearner);

        JSONObject learnerLessons = (JSONObject) selectedLearner.get("learnerLessons");
        JSONArray learnerBookedLessons = (JSONArray) learnerLessons.get("booked");
        int indexOfPreviousLesson = rm.getIndex(learnerBookedLessons, previousLesson);
        String previousBookingID = previousLesson.get("bookingID").toString();

        methodUpdateSelectedLessonSlots(previousLesson, "change");
        methodUpdateSelectedLessonSlots(newLesson, "book");

        newLesson.remove("lessonSlots");
        newLesson.put("bookingID", previousBookingID);

        learnerBookedLessons.add(newLesson);
        learnerBookedLessons.remove(indexOfPreviousLesson);

        if(rm.updateInJSONFile("src\\data\\", "LearnersData.json", indexOfLearner, selectedLearner)) return newLesson;
        else System.out.println("ERROR: Sorry! some error occurred while changing lesson.");
        return null;
    }

    public void methodUpdateBookedLessonsOnGradeUpgrade(JSONObject selectedLearner)
    {
        JSONArray arrayOfLearners = rm.readFromJSONFile("src\\data\\", "LearnersData.json");
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

        if(!rm.updateInJSONFile("src\\data\\", "LearnersData.json", indexOfLearner, selectedLearner)) {
            System.out.println("ERROR: Sorry! some error occurred while deleting lessons.");
        }
    }

    public String methodGenerateBookingID()
    {
        UUID randomUUID = UUID.randomUUID();
        return randomUUID.toString().substring(0, 8);
    }

    public void methodDisplayLessonDetails(Learner learner, JSONObject lesson, String lessonType)
    {
        System.out.println("\n=====================================================================================" +
                "\n\t\t\t\t\t\t\t" + lessonType + " Lesson Details" +
                "\n=====================================================================================" +
                "\nBooking ID: " + lesson.get("bookingID") +
                "\t\t Learner Name: " + learner.getLearnerName() +
                "\t\t Learner Grade: " + learner.getLearnerCurrentGradeLevel() +
                "\nLesson Date: " + getLessonDate() +
                "\t\t Lesson Time: " + getLessonStartTime() + "-" + getLessonEndTime() +
                "\t\t Lesson Grade : " + getLessonGrade());
        if(lessonType.equalsIgnoreCase("booked") || lessonType.equalsIgnoreCase("cancelled") || lessonType.equalsIgnoreCase("changed"))
        {
            System.out.println("\n=====================================================================================");
        }
        else if(lessonType.equalsIgnoreCase("attended"))
        {
            System.out.println("\nCoach Name: " + getLessonCoach() +
                    "\t\tLesson Rating: " + lesson.get("lessonReviewRating") +
                    "\nLesson Review: " + lesson.get("lessonReviewMessage") +
                    "\n=====================================================================================");
        }
    }

    public JSONObject setLessonDetailsFromJSON(Timetable timetable)
    {
        int optionSelectLesson = m.selectMenuOption(1, timetable.getTimetable().size(), "Select a Lesson");
        while(optionSelectLesson == 0) { optionSelectLesson = m.selectMenuOption(1, timetable.getTimetable().size(), "Select a Lesson"); }
        JSONObject lessonDetails = (JSONObject) timetable.getTimetable().get(optionSelectLesson-1);

        setLessonDate(lessonDetails.get("lessonDate").toString());
        setLessonDay(lessonDetails.get("lessonDay").toString());
        setLessonStartTime(lessonDetails.get("lessonStartTime").toString());
        setLessonEndTime(lessonDetails.get("lessonEndTime").toString());
        setLessonGrade(Integer.parseInt(lessonDetails.get("lessonGrade").toString()));
        setLessonCoach(lessonDetails.get("coachName").toString());
        if(lessonDetails.get("lessonSlots") != null) setLessonSlots(Integer.parseInt(lessonDetails.get("lessonSlots").toString()));
        else setLessonSlots(0);

        return lessonDetails;
    }
}
