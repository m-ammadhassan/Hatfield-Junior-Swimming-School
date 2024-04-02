package com.example.hjssapplication;

public class HJSSApplication {
    public static void main(String[] args) {

        Welcome wc = new Welcome();
        //wc.printWelcomeMessage();

        MainMenu mm = new MainMenu();
        mm.displayMainMenu();

        PracticeJSON pj = new PracticeJSON();
        //pj.registerInJSON();



    }
}
