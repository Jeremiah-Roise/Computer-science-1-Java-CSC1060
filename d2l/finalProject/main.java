// Written by Jeremiah Roise
// for CSC1060
// November 10th, 2023.
// A simple game of chance where players selectnumbers to reach a score of 30.
//
// This project is a single file for your convienience.

import java.util.Scanner;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class main {

    public static void main(String[] args) {
        Die die = new Die();
        // initialize two players.
        player p1 = new player();
        player p2 = new player();
        clearConsole();

// print out introduction text.
        typeWritePrint("\nThe goal of this game is to accumulate a player score of exactly 30.\n");
        typeWritePrint("The first player to score exactly 30 is the Winner!\n");

        typeWritePrint("You will roll a pair of dice, then you must choose the score of one of the dice or the total of the two dice.\n");
        typeWritePrint("This value is added to your player score.\n");
        typeWritePrint("If your score goes over 30, your score is reset to zero.\n");
        typeWritePrint("Player turn changes after each roll of the dice.\n");
        typeWritePrint("You win when you accumulate a score of exactly 30.\n");

        // have players set up their characters.
        typeWritePrint("Hello, what is the name of the first player?\n");
        p1.setupPlayer();
        clearConsole();

        typeWritePrint("Hello, what is the name of the second player?\n");
        p2.setupPlayer();
        clearConsole();

        // main game loop.
        for (int i = 0; i < 20; i++) {

            if(p1.takeTurn(die) == 1){
                break;
            }

            if(p2.takeTurn(die) == 1){
                break;
            }
            
        }
        // if we got out of the loop then someone has won,
        // and we should print win message in blue!
        System.out.println("\u001B[34mYour total: 30! Congratulations, you WIN!!\u001B[0m");
    }
    // clear the console.
    static void clearConsole(){
        final String ANSI_CLS = "\u001b[2J";
        final String ANSI_HOME = "\u001b[H";
        System.out.print(ANSI_CLS + ANSI_HOME);
        System.out.flush();
    }

    // type input string with delays between each letter.
    static void typeWritePrint(String string, int millisecondToDelay){
        Random r = new Random();
        int fastletters = 0;

        for (char letter : string.toCharArray()) {

            System.out.print(letter);
            try {
                // delay every n letters.
                if (fastletters == 0) {
                    fastletters = r.nextInt(3,10);
                    TimeUnit.MILLISECONDS.sleep(r.nextInt(70, 150));
                }
                // delay between each letter.
                TimeUnit.MILLISECONDS.sleep(millisecondToDelay + r.nextInt(0,11));
            } catch (Exception e) {
                System.out.println("well thats strange the application failed to delay");
                return;
            }
            if(letter == ' '){
                fastletters--;
            }
        }
    }
    // just sets a default delay time for typeWritePrint.
    static void typeWritePrint(String string){
        typeWritePrint(string, 25);
    }
}


/**
 * die
 * handles little things like generating the correct range of random numbers,
 * as well as generating the unicode characters for dice.
 * also handy for testing because players take a Die object to use for their turn.
 */
class Die {
    private Random generator = new Random();
    private String lastRollString = "This is not a valid String if you are seeing this something is very wrong!";
    private int lastRollValue = 0;

    // make sure a Die object is always initialized.
    Die() {
        Roll();
    }

    // roll the Die.
    void Roll() {
        lastRollValue = generator.nextInt(1, 7);

        // generate proper character for rolled number.
        // I started with a switch statement but this was so much more compact. (plus it's branchless!).
        lastRollString = String.valueOf((char)((lastRollValue - 1) + '⚀'));
    }

    // only getters/setters here.
    int getLastRollValue() {
        return lastRollValue;
    }

    String getLastRollString() {
        return lastRollString;
    }
}

/**
 * player
 * holds personalized info for different players.
 */
class player {

    private String playerColor = "\u001B[33m";
    private String name = "john doe";
    private int score = 0;
    // initialize user input system.
    private Scanner input = new Scanner(System.in);

    // print information with this players color.
    void playerPrint(String string){
        System.out.println(this.playerColor + string + "\u001B[0m");
    }

    // this function should be called when you want your player to setup their character.
    void setupPlayer(){
        while (true) {
            System.out.print("name: ");
            try {
                name = input.nextLine();
                break;
            } catch (Exception e) {
                System.out.println("please enter a valid input.");
                input.nextLine();
            }
        }


        int colorCode = -1;
        while (true) {
            System.out.println("please choose a color!");
            System.out.println("\u001B[31m(1: Red)");
            System.out.println("\u001B[32m(2: Green)");
            System.out.println("\u001B[34m(3: Blue)\u001B[0m");
            System.out.println("\u001B[33m(4: yellow)\u001B[0m");
            try {
                colorCode = input.nextInt();
                if (colorCode > 0 && colorCode < 5) {
                    break;
                }
            } catch (Exception e) {
                System.out.println("please enter a valid input.");
                input.nextLine();
            }
        }

        switch (colorCode) {
            case 1:
                playerColor = "\u001B[31m";

                
                break;
            case 2:
                
                playerColor = "\u001B[32m";
                break;
            case 3:
                
                playerColor = "\u001B[34m";
                break;
        }
    }

    // call when it's time for a players turn.
    // returns 1 if this player has won, otherwise returns 0.
    int takeTurn(Die die) {
        playerPrint("\nPlayer " + name + " it is your turn!");
        playerPrint("Your score: " + score);
        playerPrint("Your Roll:");

        // roll the die a first time.
        die.Roll();
        int roll1 = die.getLastRollValue();

        // print user info.
        playerPrint("die 1: " + roll1 + " " + die.getLastRollString());

        // roll the die for a second time.
        die.Roll();
        int roll2 = die.getLastRollValue();

        // print user info.
        playerPrint("die 2: " + roll2 + " " + die.getLastRollString());


        int option = -1;
        while (true) {
            playerPrint("Do you wish to (1) Keep die 1, (2) Keep die 2, (3) Keep the total? (Respond with 1 or 2 or 3):");
            try {
                option = input.nextInt();
                if (option > 0 && option < 4) {
                    break;
                }
            }finally{
                // still don't have to do anything.
            }
            System.out.println("please enter a valid input.");
        }

        switch (option) {

            // keep die 1
            case 1:
                score += roll1;
                break;

            // keep die 2
            case 2:
                score += roll2;
                break;

            // keep both die
            case 3:
                score += (roll1 + roll2);
                break;
        }



        // reset score if greater than 30.
        if (score > 30) {
            score = 0;
        }
        // if score is equal to 30 then you won!
        if (score == 30) {
            return 1;
        }
        return 0;
    }

    // only getters/setters down here.
    void setName(String name) {
        this.name = name;
    }

    String getName() {
        return this.name;
    }
}
