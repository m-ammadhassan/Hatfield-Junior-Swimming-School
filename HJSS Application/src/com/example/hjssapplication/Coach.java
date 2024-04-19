package com.example.hjssapplication;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class Coach {
    ReusableMethods rm = new ReusableMethods();
    private String coachID;
    private String coachName;
    private int coachGrade;

    Coach() {}

    Coach(String id, String name, int grade)
    {
        setCoachID(id);
        setCoachName(name);
        setCoachGrade(grade);
    }

    public String getCoachID() {
        return coachID;
    }

    public void setCoachID(String coachID) {
        this.coachID = coachID;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public int getCoachGrade() {
        return coachGrade;
    }

    public void setCoachGrade(int coachGrade) {
        this.coachGrade = coachGrade;
    }

    public void methodAddCoachReview(Review review, Learner learner, JSONObject selectedLesson)
    {
        JSONArray arrayOfCoaches = rm.readFromJSONFile("src\\data\\", "PracticeCoaches.json");
        int indexOfCoach = 0;

        for(int i=0; i<arrayOfCoaches.size(); i++)
        {
            if(((JSONObject) arrayOfCoaches.get(i)).get("coachID").equals(selectedLesson.get("coachID"))) indexOfCoach = i;
        }

        JSONObject selectedCoach = (JSONObject) arrayOfCoaches.get(indexOfCoach);
        JSONArray coachReviews = (JSONArray) selectedCoach.get("coachReviews");

        JSONObject newReview = new JSONObject();
        newReview.put("bookingID", selectedLesson.get("bookingID"));
        newReview.put("lessonDate", selectedLesson.get("lessonDate"));
        newReview.put("lessonGrade", selectedLesson.get("lessonGrade"));
        newReview.put("learnerID", learner.getLearnerID());
        newReview.put("learnerName", learner.getLearnerName());
        newReview.put("lessonReviewRating", review.getReviewRating());
        newReview.put("lessonReviewMessage", review.getReviewMessage());

        coachReviews.add(newReview);

        if(!rm.updateInJSONFile("src\\data\\", "PracticeCoaches.json", indexOfCoach, selectedCoach)) {
            System.out.println("ERROR: Sorry! some error occurred while making lesson attended.");
        }
    }
}
