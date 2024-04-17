package com.example.hjssapplication;

public class YesNoMenu extends Menu {
    public Boolean displayYesNoMenu()
    {
        String[] arrayYesNoMenu = {"Yes", "No"};
        int optionYesNoMenu = displayMenu(arrayYesNoMenu, "\t\t");

        if(optionYesNoMenu == 1) return true;
        else return false;
    }

}
