package com.example.hjssapplication;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ReusableMethods {

    JSONParser jsonParser = new JSONParser();

    //Method: For Clearing The Screen

    //Method: Validating Name

    //Method: Validating Number
    public boolean validateIsNumber(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //Method: Validating Gender
    public String validateGender(int genderMenuOption)
    {
        if(genderMenuOption == 1) return "male";
        else if(genderMenuOption == 2) return "female";
        else return "other";
    }

    //Method: Validating Age
    public String validateAge(String age)
    {
        int minAge = 4;
        int maxAge = 11;
        if(validateIsNumber(age))
        {
            if(Integer.parseInt(age) >= minAge && Integer.parseInt(age) <= maxAge) return age;
            else
            {
                System.out.println("ERROR: Age must be between " + minAge + " and " + maxAge);
                return null;
            }
        }
        else {
            System.out.println("ERROR: Age must be a number!");
            return null;
        }
    }

    //Method: Validating Contact Number
    public String validateContact(String contact)
    {
        if(contact.length() == 10)
        {
            if(contact.startsWith("7"))
            {
                if(validateIsNumber(contact.substring(1,2)) && validateIsNumber(contact.substring(2))) return contact;
                else System.out.println("ERROR: Number is not correct!");
            }
            else System.out.println("ERROR: Number must start with 7!");
        }
        else System.out.println("ERROR: Number must be 10 digits long and cannot contain space!");

        return null;
    }


    //Method: Read and Return Data From JSON
    public JSONArray readFromJSONFile(String filePath, String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(filePath + fileName);
            JSONArray jsonArray = (JSONArray) jsonParser.parse(fileReader);
            fileReader.close();
            return jsonArray;
        }
        catch (FileNotFoundException e) {
            System.out.println("ERROR: " + e);
            return null;
        }
        catch (IOException | ParseException e) {
            System.out.println("ERROR: " + e);
            return null;
        }
    }

    //Method: Write To JSON File
    public void writeInJSONFile(JSONObject jsonObject, String filePath, String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(filePath + fileName);
            JSONArray jsonArray = (JSONArray) jsonParser.parse(fileReader);

            jsonArray.add(jsonObject);

            FileWriter fileWriter = new FileWriter(filePath + fileName);
            fileWriter.write(jsonArray.toJSONString());
            fileWriter.flush();
            fileWriter.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("ERROR: " + e);
        }
        catch (IOException | ParseException e) {
            System.out.println("ERROR: " + e);
        }

    }
}
