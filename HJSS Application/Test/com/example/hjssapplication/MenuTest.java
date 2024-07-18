package com.example.hjssapplication;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MenuTest {

    @Test
    void validateMenuOption() {
        Menu menu = new Menu();

        // If menu option is a number and is greater than or equal to minimum value and is less than or equal to maximum value!
        boolean expectedResult = true;
        boolean actualResult = menu.validateMenuOption("2", 1, 5);
        assertEquals(expectedResult, actualResult);

        // If menu option is not a number
        boolean expectedSecondResult = false;
        boolean actualSecondResult = menu.validateMenuOption("alpha", 1, 5);
        assertEquals(expectedSecondResult, actualSecondResult);

        // If menu option is less than minimum value
        boolean expectedThirdResult = false;
        boolean actualThirdResult = menu.validateMenuOption("-1", 1, 5);
        assertEquals(expectedThirdResult, actualThirdResult);

        // If menu option is greater than maximum value
        boolean expectedFourthResult = false;
        boolean actualFourthResult = menu.validateMenuOption("7", 1, 5);
        assertEquals(expectedFourthResult, actualFourthResult);
    }
}