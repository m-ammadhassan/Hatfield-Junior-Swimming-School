package com.example.hjssapplication;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LearnerTest {


    @Test
    void generateLearnerID() {
        Learner learner = new Learner();
        ReusableMethods rm = new ReusableMethods();
        JSONArray arrayOfLearners = rm.readFromJSONFile("src\\data\\", "LearnersData.json");
        JSONObject lastLearner = (JSONObject) arrayOfLearners.getLast();
        String lastLearnerID = lastLearner.get("learnerID").toString();
        int lastLearnerIDNum = Integer.parseInt(lastLearnerID.substring(3));

        String expectedResult = "SWL" + (lastLearnerIDNum + 1);
        String actualResult = learner.generateLearnerID();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void setLearnerDetailsFromJSON() {
        Learner learner = new Learner();
        learner.setLearnerID("SWL1");

        String expectedResult = "SWL1";
        JSONObject learnerDetails = learner.setLearnerDetailsFromJSON();
        String actualResult = learnerDetails.get("learnerID").toString();

//        String actualResult = ((JSONObject) learner.setLearnerDetailsFromJSON()).get("learnerID").toString();

        assertEquals(expectedResult, actualResult);
    }

}