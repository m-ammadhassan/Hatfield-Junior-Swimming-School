package com.example.hjssapplication;

public class Review {
    private String reviewMessage;
    private int reviewRating;

    Review() {}

    Review(String message, int rating)
    {
        setReviewMessage(message);
        setReviewRating(rating);
    }

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
}
