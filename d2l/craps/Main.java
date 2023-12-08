// Jeremiah Roise.
// CSC1060
// October 3, 2023
// simulates a game of craps.
import java.util.Random;

// create a Dice class to handle number generation.
class Dice {
    // keep the last roll stored in the dice object
    public int last = -1;
    Random generator = new Random();

    public Dice(){
        // make sure the Dice object is never used unitialized.
        roll();
    }

    // create random number.
    public int roll(){
        int randint = -1;
        // if we tried to generate a random number between 0 and 7 1000 times and failed something is probably wrong.
        for (int i = 0; i < 1000; i++) {
            randint = generator.nextInt(7);
            if (randint > 0){
                last = randint;
                return randint;
            }
        }
        return -1;
    }
}

public class Main {

    // I learned Rust a little while back and that taught me the value of enums.
    enum turn{
        Win,
        Lose,
        Point,
        error;
    }

    // when there is no point this value will be negative one.
    static int point = -1;

    // keep track of the score.
    static int wins = 0;
    static int losses = 0;

    // Called when the player loses.
    public static void Lose(){
        losses++;
        point = -1;
        System.out.println("\u001B[31mLOSE\u001B[0m");
    }

    // Called when the player wins.
    public static void Win(){
        wins++;
        point = -1;
        System.out.println("\u001B[32mWIN\u001B[0m");
    }

    public static void main(String[] args) {
        // initialize two dice objects.
        Dice dice1 = new Dice();
        Dice dice2 = new Dice();

        // this is a sort of pseudo table for the initial game.
        turn[] winning = {turn.error, turn.error, turn.Win, turn.Win, turn.Point, turn.Point, turn.Point, turn.Lose, turn.Point, turn.Point, turn.Point, turn.Lose, turn.Win};


        // run the sim 100 times.
        for (int i = 0; i < 100; i++) {

            int sum = dice1.roll() + dice2.roll();

            // print dice values.
            if ( point == -1){
                System.out.println("Dice1: " + dice1.last + " Dice2: " + dice2.last + " sum ---> " + sum);
            }
            else{
                System.out.println("    Dice1: " + dice1.last + " Dice2: " + dice2.last + " sum ---> " + sum);
            }
            
            // non point rules.
            if ( point == -1) {
                if (winning[sum] == turn.Point) {
                        point = sum;
                        System.out.println("\nPoint: " + point);
                }
                // player wins.
                if (winning[sum] == turn.Win) {
                    Win();
                    
                }
                // player loses.
                if (winning[sum] == turn.Lose) {
                    Lose();
                }
            }


            // if we have a point the rules are almost completely different.
            else {
                // player loses.
                if (sum == 7) {
                    Lose();
                }
                // player wins.
                else if (point == sum){
                    Win();
                }
            }



        }
        // read win loss totals.
        System.out.println("\n\u001B[32mWins:\u001B[0m " + wins);
        System.out.println("\u001B[31mLosses:\u001B[0m " + losses);
        
    }
}
