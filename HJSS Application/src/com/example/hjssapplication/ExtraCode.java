package com.example.hjssapplication;

import java.time.LocalDate;
import java.time.Period;

public class ExtraCode {

//    // --> Getting DOB
//        System.out.println("\nDate of Birth: ");
//    // Year
//        do{
//        System.out.print("Enter Year: ");
//        learnerDOBYear = rm.validateYear(userInput.nextLine().replace(" ", ""));
//    }
//        while (learnerDOBYear==null);
//
//    // Month
//        System.out.println("Select Month: ");
//    int dobMonthOption = displayMenu(new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"}, "\t ");
//    learnerDOBMonth = Integer.toString(dobMonthOption);
//
//    // Day
//        do {
//        System.out.print("Enter Day [01-31]: ");
//        learnerDOBDay = rm.validateDOBDay(userInput.nextLine().replace(" ", ""), learnerDOBMonth);
//    }
//        while (learnerDOBDay==null);

//    //Method: Calculate Age From Current Date
//    public int methodCalculateAgeFromCurrentDate(String dd, String mm, String yyyy)
//    {
//        LocalDate dob = LocalDate.of(Integer.parseInt(yyyy), Integer.parseInt(mm), Integer.parseInt(dd));
//
//        return Period.between(dob, currentDate).getYears();
//    }

//    //Method: Validating DOB Day
//    public String validateDOBDay(String day, String m)
//    {
//        int month = Integer.parseInt(m);
//        if(validateIsNumber(day) && (day.length() == 1 || day.length() == 2))
//        {
//            if(Integer.parseInt(day) != 0)
//            {
//                if(Integer.parseInt(day) >= 1 && Integer.parseInt(day) <= 31)
//                {
//                    if(Integer.parseInt(day) >= 1 && Integer.parseInt(day) <= 29)
//                    {
//                        return day;
//                    }
//                    else if((Integer.parseInt(day) == 30 || Integer.parseInt(day) == 31) && month == 2)
//                    {
//                        System.out.println("ERROR: February cannot have 30 or 31 days");
//                        return null;
//                    }
//
//                    else if(Integer.parseInt(day) == 31 && (month == 4 || month == 6 || month == 9 || month == 11))
//                    {
//                        System.out.println("ERROR: April, June, September & November cannot have 31 days!");
//                        return null;
//                    }
//                    else return day;
//                }
//                else System.out.println("ERROR: Please enter correct day!");
//            }
//            else System.out.println("ERROR: Day cannot be a 0.");
//        }
//        else System.out.println("ERROR: Day must be either one or two digit number!");
//        return null;
//    }

//    //Method: Validate Year
//    public String validateYear(String year)
//    {
//        if (validateIsNumber(year) && year.length() == 4 && Integer.parseInt(year) != 0 && Integer.parseInt(year.substring(0,1)) != 0)
//        {
//            if(Integer.parseInt(year) >= (currentDate.getYear()-11) && Integer.parseInt(year) <= (currentDate.getYear()-4)) return year;
//            else System.out.println("ERROR: Year must be between " + (currentDate.getYear()-11) + " and " + (currentDate.getYear()-4));
//        }
//        else System.out.println("ERROR: Please enter a valid year!");
//        return null;
//    }

}
