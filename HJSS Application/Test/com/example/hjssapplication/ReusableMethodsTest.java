package com.example.hjssapplication;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class ReusableMethodsTest {

    LocalDate currentDate = LocalDate.now();
    LocalTime currentTime = LocalTime.now();
    DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("ha");
    @Test
    void validateName() {
        ReusableMethods rm = new ReusableMethods();

        // If name contain only two or more alphabets
        boolean expectedResult = true;
        boolean actualResult = rm.validateName("Learner Name");
        assertEquals(expectedResult, actualResult);

        // If name is empty
        boolean expectedSecondResult = false;
        boolean actualSecondResult = rm.validateName("");
        assertEquals(expectedSecondResult, actualSecondResult);

        // If name contains only single alphabet
        boolean expectedThirdResult = false;
        boolean actualThirdResult = rm.validateName("A");
        assertEquals(expectedThirdResult, actualThirdResult);

        // If name contain numbers
        boolean expectedFourthResult = false;
        boolean actualFourthResult = rm.validateName("Learner123");
        assertEquals(expectedFourthResult, actualFourthResult);
    }

    @Test
    void validateIsNumber() {
        ReusableMethods rm = new ReusableMethods();

        // If a number
        boolean expectedResult = true;
        boolean actualResult = rm.validateIsNumber("1");
        assertEquals(expectedResult, actualResult);

        // If not a number
        boolean expectedSecondResult = false;
        boolean actualSecondResult = rm.validateIsNumber("alpha");
        assertEquals(expectedSecondResult, actualSecondResult);
    }

    @Test
    void validateAge() {
        ReusableMethods rm = new ReusableMethods();

        // If age is a number and is greater than or equal to minimum value and is less than or equal to maximum value
        boolean expectedResult = true;
        boolean actualResult = rm.validateAge("7");
        assertEquals(expectedResult, actualResult);

        // If age is not a number
        boolean expectedSecondResult = false;
        boolean actualSecondResult = rm.validateAge("alpha");
        assertEquals(expectedSecondResult, actualSecondResult);

        // If age is less than minimum value
        boolean expectedThirdResult = false;
        boolean actualThirdResult = rm.validateAge("1");
        assertEquals(expectedThirdResult, actualThirdResult);

        // If age is greater than maximum value
        boolean expectedFourthResult = false;
        boolean actualFourthResult = rm.validateAge("14");
        assertEquals(expectedFourthResult, actualFourthResult);
    }

    @Test
    void validateContact() {
        ReusableMethods rm = new ReusableMethods();

        // If contact number is equal to 10 digits, start with number 7, and only contain numbers
        boolean expectedResult = true;
        boolean actualResult = rm.validateContact("7123123123");
        assertEquals(expectedResult, actualResult);

        // If contact number is less than 10 digits
        boolean expectedSecondResult = false;
        boolean actualSecondResult = rm.validateContact("7123123");
        assertEquals(expectedSecondResult, actualSecondResult);

        // If contact number does not start with 7
        boolean expectedThirdResult = false;
        boolean actualThirdResult = rm.validateContact("9123123123");
        assertEquals(expectedThirdResult, actualThirdResult);

        // If contact number contain any other characters
        boolean expectedFourthResult = false;
        boolean actualFourthResult = rm.validateContact("7123@abc45");
        assertEquals(expectedFourthResult, actualFourthResult);
    }

    @Test
    void validateGradeLevel() {
        ReusableMethods rm = new ReusableMethods();
        Learner learner = new Learner();
        Lesson lesson = new Lesson();

        // If leaner is new and do select grade 1 lesson
        learner.setLearnerCurrentGradeLevel(0);
        lesson.setLessonGrade(1);
        boolean expectedResult = true;
        boolean actualResult = rm.validateGradeLevel(lesson, learner);
        assertEquals(expectedResult, actualResult);

        // If learner grade is equal to the lesson grade
        learner.setLearnerCurrentGradeLevel(2);
        lesson.setLessonGrade(2);
        boolean expectedSecondResult = true;
        boolean actualSecondResult = rm.validateGradeLevel(lesson, learner);
        assertEquals(expectedSecondResult, actualSecondResult);

        // If lesson grade is one level higher than learner grade
        lesson.setLessonGrade(3);
        boolean expectedThirdResult = true;
        boolean actualThirdResult = rm.validateGradeLevel(lesson, learner);
        assertEquals(expectedThirdResult, actualThirdResult);

        // If lesson grade is lower than learner grade
        lesson.setLessonGrade(1);
        boolean expectedFourthResult = false;
        boolean actualFourthResult = rm.validateGradeLevel(lesson, learner);
        assertEquals(expectedFourthResult, actualFourthResult);

        // If lesson grade is two or more level higher than learner grade
        lesson.setLessonGrade(4);
        boolean expectedFifthResult = false;
        boolean actualFifthResult = rm.validateGradeLevel(lesson, learner);
        assertEquals(expectedFifthResult, actualFifthResult);
    }

    @Test
    void validateLessonAvailableSlots() {
        ReusableMethods rm = new ReusableMethods();
        Lesson lesson = new Lesson();

        // If lesson slots are available which means greater than 0
        lesson.setLessonSlots(2);
        boolean expectedResult = true;
        boolean actualResult = rm.validateLessonAvailableSlots(lesson);
        assertEquals(expectedResult, actualResult);

        // If lesson slots are not available which means equal to 0
        lesson.setLessonSlots(0);
        boolean expectedSecondResult = false;
        boolean actualSecondResult = rm.validateLessonAvailableSlots(lesson);
        assertEquals(expectedSecondResult, actualSecondResult);
    }

    @Test
    void validateAttendLessonTime() {
        ReusableMethods rm = new ReusableMethods();
        Lesson lesson = new Lesson();

        lesson.setLessonDate(currentDate.format(formatDate));
        lesson.setLessonEndTime(currentTime.minusHours(1).format(formatTime));
        boolean expectedResult = true;
        boolean actualResult = rm.validateAttendLessonTime(lesson);
        assertEquals(expectedResult, actualResult);


        lesson.setLessonDate(currentDate.minusDays(1).format(formatDate));
        lesson.setLessonEndTime(currentTime.format(formatTime));
        boolean expectedSecondResult = true;
        boolean actualSecondResult = rm.validateAttendLessonTime(lesson);
        assertEquals(expectedSecondResult, actualSecondResult);


        lesson.setLessonDate(currentDate.plusDays(2).format(formatDate));
        lesson.setLessonEndTime(currentTime.format(formatTime));
        boolean expectedThirdResult = false;
        boolean actualThirdResult = rm.validateAttendLessonTime(lesson);
        assertEquals(expectedThirdResult, actualThirdResult);

    }

    @Test    void validateChangeCancelLessonTime() {
        ReusableMethods rm = new ReusableMethods();
        Lesson lesson = new Lesson();

        lesson.setLessonDate(currentDate.plusDays(1).format(formatDate));
        lesson.setLessonStartTime(currentTime.format(formatTime));
        boolean expectedResult = true;
        boolean actualResult = rm.validateChangeCancelLessonTime(lesson);
        assertEquals(expectedResult, actualResult);



        lesson.setLessonDate(currentDate.format(formatDate));
        lesson.setLessonStartTime(currentTime.plusHours(1).format(formatTime));
        boolean expectedSecondResult = false;
        boolean actualSecondResult = rm.validateChangeCancelLessonTime(lesson);
        assertEquals(expectedSecondResult, actualSecondResult);
    }
}