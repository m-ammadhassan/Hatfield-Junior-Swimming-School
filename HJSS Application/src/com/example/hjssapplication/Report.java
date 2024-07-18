package com.example.hjssapplication;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.LocalDate;

public class Report {
    Menu m = new Menu();
    ReusableMethods rm = new ReusableMethods();
    LocalDate currentDate = LocalDate.now();
    private String reportMonthName;
    private String reportMonthNumber;
    private String reportYearNumber;
    private String reportDate;

    public String getReportMonthName() { return reportMonthName; }

    public void setReportMonthName(String reportMonthName) { this.reportMonthName = reportMonthName; }

    public String getReportMonthNumber() { return reportMonthNumber; }

    public void setReportMonthNumber(String reportMonthNumber) { this.reportMonthNumber = reportMonthNumber; }

    public String getReportYearNumber() { return reportYearNumber; }

    public void setReportYearNumber(String reportYearNumber) { this.reportYearNumber = reportYearNumber; }

    public String getReportDate() { return reportDate; }

    public void setReportDate(String reportDate) { this.reportDate = reportDate; }

    public void setReportDateDetails()
    {
        System.out.println("\nSelect a Month: ");
        String[] arrayOfMonths = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        int optionSelectedMonth = m.displayMenu(arrayOfMonths, "\t");

        setReportMonthName(arrayOfMonths[optionSelectedMonth-1]);

        if(Integer.toString(optionSelectedMonth).length() == 1) setReportMonthNumber("0"+optionSelectedMonth);
        else setReportMonthNumber(Integer.toString(optionSelectedMonth));

        setReportYearNumber(String.valueOf(currentDate.getYear()));

        setReportDate(getReportYearNumber()+"-"+getReportMonthNumber());
    }

    public void methodDisplayReportHeader(String reportTitle)
    {
        System.out.println("\n=====================================================================================" +
                "\n\t\t\t\t\t\t\t" + reportTitle +
                "\n=====================================================================================" +
                "\nMonth Name: " + getReportMonthName() +
                "\t\t\t\t\t\t Year: " + getReportYearNumber() +
                "\n====================================================================================="
        );
    }

    public void methodDisplayReportOfLearners()
    {
        JSONArray arrayOfLearners = rm.readFromJSONFile("src\\data\\", "LearnersData.json");
        for(int i=0; i<arrayOfLearners.size(); i++)
        {
            JSONObject learnerData = (JSONObject) arrayOfLearners.get(i);
            JSONObject learnerLessons = (JSONObject) learnerData.get("learnerLessons");

            System.out.println("-------------------------------------------------------------------------------------" +
                    "\nLearner ID: " + learnerData.get("learnerID") +
                    "\t\t Name: " + learnerData.get("learnerName") +
                    "\t\t Gender: " + learnerData.get("learnerGender") +
                    "\t\t Current Grade: " + learnerData.get("learnerCurrentGrade"));

            System.out.println("\nBooked Lesson Details: ");
            int numberOfBookedLessons = methodDisplayLessonsOfSelectedMonth((JSONArray) learnerLessons.get("booked"), "Booked");

            System.out.println("\nAttended Lesson Details: ");
            int numberOfAttendedLessons = methodDisplayLessonsOfSelectedMonth((JSONArray) learnerLessons.get("attended"), "Attended");

            System.out.println("\nCancelled Lesson Details: ");
            int numberOfCancelledLessons = methodDisplayLessonsOfSelectedMonth((JSONArray) learnerLessons.get("cancelled"), "Cancelled");

            System.out.println("\nBooked Lessons = " + numberOfBookedLessons +
                    "\t\t\t Attended Lessons = " + numberOfAttendedLessons +
                    "\t\t\t Cancelled Lessons = " + numberOfCancelledLessons +
                    "\n-------------------------------------------------------------------------------------");
        }
    }

    public void methodDisplayReportOfCoaches()
    {
        JSONArray arrayOfCoaches = rm.readFromJSONFile("src\\data\\", "CoachesData.json");
        for(int i=0; i<arrayOfCoaches.size(); i++)
        {
            JSONObject coachData = (JSONObject) arrayOfCoaches.get(i);
            System.out.println("-------------------------------------------------------------------------------------" +
                    "\nCoach ID: " + coachData.get("coachID") +
                    "\t\tCoach Name: " + coachData.get("coachName"));

            System.out.println("\nCoach Reviews: ");
            methodDisplayReviewsOfSelectedMonth((JSONArray) coachData.get("coachReviews"));
            System.out.println("\n-------------------------------------------------------------------------------------");
        }
    }

    public int methodDisplayLessonsOfSelectedMonth(JSONArray lessonsArray, String lessonType)
    {
        JSONArray lessonsOfSelectedMonth = generateDataOfSelectedMonth(lessonsArray);
        if(!lessonsOfSelectedMonth.isEmpty())
        {
            for(int i=0; i<lessonsOfSelectedMonth.size(); i++)
            {
                JSONObject lesson = (JSONObject) lessonsOfSelectedMonth.get(i);
                System.out.println((i+1) + " - Lesson Date: " + lesson.get("lessonDate") +
                        "\t\t Lesson Time: " + lesson.get("lessonStartTime") + "-" + lesson.get("lessonEndTime") +
                        "\t\t Booking ID: " + lesson.get("bookingID") +
                        "\n\tLesson Grade: " + lesson.get("lessonGrade") +
                        "\t\t Coach Name: " + lesson.get("coachName")
                );
            }
        }
        else System.out.println("No lessons " + lessonType.toLowerCase() + " in this month");

        return lessonsOfSelectedMonth.size();
    }

    public void methodDisplayReviewsOfSelectedMonth(JSONArray reviewsArray)
    {
        JSONArray reviewsOfSelectedMonth = generateDataOfSelectedMonth(reviewsArray);
        float reviewTotalRating = 0F;
        float reviewAverageRating = 0F;
        if(!reviewsOfSelectedMonth.isEmpty())
        {
            for(int i=0; i<reviewsOfSelectedMonth.size(); i++)
            {
                JSONObject review = (JSONObject) reviewsOfSelectedMonth.get(i);
                System.out.println((i+1) + " - Lesson Date: " + review.get("lessonDate") +
                        "\t\t Lesson Time: " + review.get("lessonStartTime") + "-" + review.get("lessonEndTime") +
                        "\t\t Booking ID: " + review.get("bookingID") +
                        "\n\tLesson Grade: " + review.get("lessonGrade") +
                        "\t\t Review By: " + review.get("learnerName") +
                        "\t\tReview Rating: " + review.get("lessonReviewRating") +
                        "\n\tReview Message: " + review.get("lessonReviewMessage")
                );
                reviewTotalRating += Float.parseFloat(review.get("lessonReviewRating").toString());
            }
            reviewAverageRating = reviewTotalRating / reviewsOfSelectedMonth.size();
        }
        else
        {
            System.out.println("No reviews in this month");
            reviewAverageRating = 0F;
        }

        System.out.println("\nNo. of Reviews = " + reviewsOfSelectedMonth.size() + "\t\t Average Rating = " + reviewAverageRating);
    }

    public JSONArray generateDataOfSelectedMonth(JSONArray dataArray)
    {
        JSONArray dataOfSelectedMonth = new JSONArray();
        for(int i=0; i<dataArray.size(); i++)
        {
            JSONObject data = (JSONObject) dataArray.get(i);
            if(data.get("lessonDate").toString().startsWith(getReportDate())) dataOfSelectedMonth.add(data);
        }
        return dataOfSelectedMonth;
    }

}
