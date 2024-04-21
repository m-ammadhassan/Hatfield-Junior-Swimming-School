package com.example.hjssapplication;

import java.util.Scanner;

public class Review {
    Menu m = new Menu();
    Scanner userInput = new Scanner(System.in);
    private String reviewMessage;
    private int reviewRating;

    public String getReviewMessage() {
        return reviewMessage;
    }

    public void setReviewMessage(String message) {
        this.reviewMessage = message;
    }

    public int getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(int rating) {
        this.reviewRating = rating;
    }

    public void methodSetNewReviewDetails()
    {
        System.out.println("\nGive rating about the lesson: ");
        String[] arrayLessonRating = {"Very Dissatisfied", "Dissatisfied", "Ok", "Satisfied", "Very Satisfied"};
        int lessonReviewRating = m.displayMenu(arrayLessonRating, "\t\t");
        setReviewRating(lessonReviewRating);

        System.out.println("\nWrite a review about the lesson: ");
        String lessonReviewMessage = userInput.nextLine();
        setReviewMessage(lessonReviewMessage);
    }
}
