package com.example.hjssapplication;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LessonTest {

    @Test
    void setLessonDetailsFromJSON() {
        Lesson lesson = new Lesson();
        Timetable tt = new Timetable();
        ReusableMethods rm = new ReusableMethods();

        JSONArray arrayOfLessons = rm.readFromJSONFile("src\\data\\", "LessonsData.json");
        JSONObject firstLesson = (JSONObject) arrayOfLessons.getFirst();

        tt.setTimetable(arrayOfLessons);

        JSONObject lessonDetails = lesson.setLessonDetailsFromJSON(tt);

        String expectedResult = lesson.getLessonCoach();
        String actualResult = lessonDetails.get("lessonCoach").toString();

        assertEquals(expectedResult, actualResult);

    }
}