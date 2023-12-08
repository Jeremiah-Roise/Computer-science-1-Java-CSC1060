// Jeremiah Roise.
// September 25, 2023
// CSC1060
// count the vowels and consonants in a string.
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {

        // list of vowels to check against.
        String vowels = "AEIOU";

        // asking user for string.
        System.out.print("Please enter some words to check: ");

        // get string from user.
        String userInput =  new Scanner(System.in).nextLine().toUpperCase();

        // counts for vowels and consonants.
        int vowelCount = 0;
        int consonantCount = 0;

        // iterate over letters in the string
        for (char letter : userInput.toCharArray()) {
            
            // if it is in the vowels list increment vowelCount.
            if ( vowels.contains(String.valueOf(letter))) {
                vowelCount++;
            }
            // if it's not a vowel it must be a consonant or a special char.
            else{
                // if it is in the alphabet and not a vowel it must be a consonant!
                if ( letter > 64 && letter < 91 ) {
                    consonantCount++;
                }
            }
        }
        // tell the user what we found.
        System.out.printf("Number of Vowels: %d Number of Consonants: %d", vowelCount, consonantCount);
        
    }
}
