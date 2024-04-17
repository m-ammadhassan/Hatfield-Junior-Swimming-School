package com.example.hjssapplication;

import java.util.Scanner;

public class Menu {
    Scanner userInput = new Scanner(System.in);
    ReusableMethods rm = new ReusableMethods();
    public int displayMenu(String[] array, String lineStyle)
    {
        for(int i=0; i<array.length; i++)
        {
            System.out.print((i+1) + ") " + array[i] + lineStyle);
        }

        int menuOption = selectMenuOption(1, array.length, "Enter your choice");

        while(menuOption == 0)
        {
            menuOption = selectMenuOption(1, array.length, "Enter your choice");
        }
        return menuOption;
    }

    public int selectMenuOption(int min, int max, String message) {
        System.out.print("\n" + message + " [" + min + "-" + max + "] : ");
        String menuOption = userInput.nextLine();
        if(validateMenuOption(menuOption, min, max))
        {
            return Integer.parseInt(menuOption);
        }
        else
        {
            return 0;
        }
    }

    public boolean validateMenuOption(String menuOption, int min, int max)
    {
        if (rm.validateIsNumber(menuOption) && Integer.parseInt(menuOption) >= min && Integer.parseInt(menuOption) <= max)
        {
            return true;
        }
        else
        {
            System.out.println("ERROR: Please select correct option!");
            return false;
        }
    }

}
