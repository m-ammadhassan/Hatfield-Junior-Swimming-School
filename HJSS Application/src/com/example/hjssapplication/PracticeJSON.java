package com.example.hjssapplication;

import java.util.Scanner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PracticeJSON {

    JSONParser jsonParser = new JSONParser();
    Scanner scanner = new Scanner(System.in);

    public void registerInJSON()
    {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter age: ");
        int age = scanner.nextInt();

        System.out.print("Enter gender");
        String gender = scanner.next();

        JSONObject obj = new JSONObject();
        obj.put("name", name);
        obj.put("age", age);
        obj.put("gender", gender);

        writeToJSON(obj);
    }
    public void writeToJSON(JSONObject obj)
    {

        try
        {
            FileReader fileReader = new FileReader("src\\data\\hello.json");
            JSONArray jsonArray = (JSONArray) jsonParser.parse(fileReader);

            jsonArray.add(obj);

            System.out.println(jsonArray);

            FileWriter fileWriter = new FileWriter("src\\data\\hello.json");
            fileWriter.write(jsonArray.toJSONString());
            fileWriter.flush();
            fileWriter.close();

        }
        catch (FileNotFoundException e) {
            System.out.println("ERROR" + e);
        }
        catch (IOException | ParseException e) {
            System.out.println("ERROR: " + e);
        }
    }


    public void updateInJSON()
    {
        try
        {
            FileReader fileReader = new FileReader("src\\data\\hello.json");
            JSONArray jsonArray = (JSONArray) jsonParser.parse(fileReader);
            JSONObject previousData = new JSONObject();
            JSONObject newData = new JSONObject();
            int previousDataIndex = 0;

            System.out.println(jsonArray);

            for (int i=0; i<jsonArray.size(); i++)
            {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                if(jsonObject.get("name").equals("B"))
                {
                    previousData = jsonObject;
                    previousDataIndex = i;
                }
            }

            System.out.println(previousData);

            newData = previousData;

            newData.remove("age");
            newData.put("age", 10);

//            jsonArray.add(requiredData);

            System.out.println(newData);

            jsonArray.remove(previousData);

            jsonArray.add(previousDataIndex, newData);

            System.out.println(jsonArray);

            FileWriter fileWriter = new FileWriter("src\\data\\hello.json");

            fileWriter.write(jsonArray.toJSONString());

            fileWriter.flush();

            fileWriter.close();

        }
        catch (FileNotFoundException e) {
            System.out.println("ERROR" + e);
        }
        catch (IOException | ParseException e) {
            System.out.println("ERROR: " + e);
        }
    }
}
