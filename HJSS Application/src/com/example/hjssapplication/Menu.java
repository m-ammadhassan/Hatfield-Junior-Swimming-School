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

        int menuOption = selectMenuOption(array.length - (array.length - 1), array.length);

        while(menuOption == 0)
        {
            menuOption = selectMenuOption(array.length - (array.length - 1), array.length);
        }
        return menuOption;
    }

    public int selectMenuOption(int min, int max) {
        System.out.print("\nEnter your choice [" + min + "-" + max + "] : ");
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
