// Jeremiah Roise
// August 25, 2023
// CSC1060681 Computer Science I: (Java) (Karim Farah) FA23.
// A simple program to convert Fahrenheit to Celsius.

import java.util.Scanner;
import com.sun.jdi.DoubleType;

public class Main {
    public static void main(String args[]) {
        // instantiate Scanner class.
        Scanner scanner = new Scanner(System.in);

        // prompt user for input.
        System.out.print("Enter a degree Celsius;");

        // get input from user.
        float degreeincelsius = scanner.nextFloat();
        
        // perform calculation.
        float degreeinFahrenheit = (((9f/5f) * degreeincelsius) + 32);

        // output value to user.
        // I didn't see anything in the project description about rounding so I didn't do it.
        System.out.printf("%s Celsius is %s Fahrenheit",  degreeincelsius, degreeinFahrenheit);
        
        
    }
}
