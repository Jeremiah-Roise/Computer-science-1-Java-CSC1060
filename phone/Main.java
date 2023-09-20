// Jeremiah Roise.
// Sept 16, 2023
// for CSC1060 homework 4 programing exercise 4.15
// convert char to digit on keypad.

import java.util.Scanner;
public class Main {

    // call to handle bad input.
    public static void invalidInput(String input) {
            System.out.println(input + " is an invalid Input input.");
    }

    // convert a character to a number on a phone pad.
    public static double convertCharToNumpad(char inputChar) {

        // encode the keypad as an array where the index is the keypad numbers.
        String[] alpha = {"abc","def","ghi","jkl","mno","pqrs","tuv","wxyz"};

        // sort through array until correct index is found.
        for (int i = 0; i < 9; i++) {
            if(alpha[i].contains(String.valueOf(inputChar))){
                return i+2;
            }
        }

        // if it gets to this point theres a big problem.
        System.out.println("this function can only take alphabet characters!");
        return -1;
    }
    public static void main(String[] args) {

        // prompt user and get input.
        System.out.println("Enter a letter: ");
        String input = new Scanner(System.in).nextLine();

        // this program will accept one and only one char.
        if (input.length() != 1){
            invalidInput(input);
            return;
        }

        // cache the char.
        int inputChar = input.toLowerCase().charAt(0);

        // if char is not between a and z it is not a valid input.
        if (inputChar <= 'a' || inputChar >= 'z'){
            invalidInput(input);
            return;
        }

        // answer the user with the keypad number.
        System.out.println("the corrosponding number is " + convertCharToNumpad((char)inputChar));
    }
}
