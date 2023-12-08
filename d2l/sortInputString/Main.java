// Jeremiah Roise.
// October 10, 2023
// CSC1060
// sort the characters of the input string alphabetically.
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    // sort the input string alphabetically.
    public static String sort(String s){
        // convert string to char array.
        char[] sorted = s.toCharArray();
        // sort.
        Arrays.sort(sorted);
        // return.
        return new String(sorted);
    }

    public static void main(String[] args) {

        // prompt user for input.
        System.out.println("please enter a few letters to sort.");

        // read input.
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        scanner.close();

        // sort and print input.
        System.out.println(sort(input));
        
    }
}
