package com.example.hjssapplication;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class PracticeDate {
    DateTimeFormatter format = DateTimeFormatter.ofPattern("EEEE-dd/MM/yyyy");
    DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("h a");

    LocalDate currentDate = LocalDate.now();

    LocalTime currentTime = LocalTime.now();

    public void currentDate()
    {
        System.out.println(currentDate);
    }

    public void currentTime()
    {
        System.out.println(currentTime.format(timeFormat));
    }

    public void getRequiredDays()
    {
        LocalDate currentDate = LocalDate.now();


        ArrayList<String> requiredDays = new ArrayList<>();

//        for(int i=0; i<7; i++)
//        {
//            String getDay = currentDate.plusDays(i).format(format);
//            if(getDay.contains("Monday") || getDay.contains("Wednesday") || getDay.contains("Friday") || getDay.contains("Saturday"))
//            {
//                requiredDays.add(getDay);
//            }
//        }
//
//        for (String requiredDay : requiredDays) {
//            System.out.println(requiredDay);
//        }

        for(int i=0; i<7; i++)
        {
            String getDate = currentDate.plusDays(i).format(format);
            if(getDate.contains("Monday"))
            {
                String[] arr = getDate.split("-");
                System.out.println(arr[1]);
            }
        }


    }
}
