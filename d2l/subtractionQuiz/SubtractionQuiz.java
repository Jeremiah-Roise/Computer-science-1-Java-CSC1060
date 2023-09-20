import java.util.Scanner;

class validnumberchecker {
}

/**
 * mathProblem
 * using abstract class to make it easy to add new types of math problems.
 * this could probably be done with a single method but thats not as much fun!
 * 
 * this abstract class used to be an interface but I wanted to add user input checking so changed to a class so as to have a number check function.
 */
abstract class mathProblem {
    abstract public void generateProblem(); // generate the problem here.
    abstract public int askUserToSolve(Scanner scanner); // print out the problem to the console and check answer here, return percent problem grade.
    
    // Takes a string and verifies that it represents a valid number ie no negetive numbers, and only number characters in the string.
    public boolean isValidNumber(String val){
        int length = val.length();
        if (val == null || length <= 0){
          return false;
        }
        // check if first character is minus sign.
        int i = 0;
        if (val.charAt(i) == '-'){
            // make sure string is not only a minus sign.
            if (length < 2){
                return false;
            }
            // ignore minus.
            i = 1;
        }

        // simple ascii range filter.
        for (; i < length; i++) {
          char c = val.charAt(i);

          // make sure current char is a number or a period.
          if ((c < '0' || c > '9') && c != '.'){
            return false;
          }
        }

        return true;
    }
}

// implements a subtraction problem.
class subtractionProblem extends mathProblem {
    int a = 0;
    int b = 0;
    int answer = -1;

    // generate subtraction problem.
    @Override
    public void generateProblem() {
        // generate two random numbers.
        a = (int)(Math.random() * 10);
        b = (int)(Math.random() * 10);

        // swap a and b if b is greater.
        if (a < b){
            a += b;
            b = a - b;
            a = a - b;
        }
        answer = a - b;
    }

    // do all user interactions here.
    @Override
    public int askUserToSolve(Scanner scanner) {
        String response = null;
        while (true) {
            // ask the user the math problem.
            System.out.printf("what is %d - %d?: ",a,b);
            response = scanner.nextLine();
            if (isValidNumber(response)){
                break;
            }
            else{
                System.out.println("That is not a valid number.");
            }
        }


        // check if answer is correct.
        if( Integer.toString(answer).equals(response)){
            // user was correct.
            System.out.println("You are correct!");
            return 100;
        }
        else{
            // user was incorrect.
            System.out.printf("Your answer is wrong.\n%d - %d should be %d\n", a, b, answer);
            return 0;
        }
    }
}

// essentially the same as the subtraction problem but without the number swap code.
class additionProblem extends mathProblem{
    int a = 0;
    int b = 0;
    int answer = -1;

    @Override
    public void generateProblem() {
        // generate two random numbers.
        a = (int)(Math.random() * 10);
        b = (int)(Math.random() * 10);
        answer = a + b;
    }

    @Override
    public int askUserToSolve(Scanner scanner) {
        String response = null;
        while (true) {
            // ask the user the math problem.
            System.out.printf("what is %d + %d?: ",a,b);
            response = scanner.nextLine();
            if (isValidNumber(response)){
                break;
            }
            else{
                System.out.println("That is not a valid number.");
            }
        }
        // ask the user the math problem.
        // check if answer is correct.
        if( Integer.toString(answer).equals(response)){
            // user was correct.
            System.out.println("You are correct!");
            return 100;
        }
        else{
            // user was incorrect.
            System.out.printf("Your answer is wrong.\n%d - %d should be %d\n", a, b, answer);
            return 0;
        }
    }
}


public class SubtractionQuiz {

    public static void main(String[] args) {
        // generate Scanner to use.
        Scanner scanner = new Scanner(System.in);

        // set number of problems to solve.
        mathProblem[] problems = new mathProblem[10];

        // for loop to do dynamic initialize for memory intensive problems.
        for (int i = problems.length-1; i > 0; --i) {

            // add subtraction problems and random addition problems.
            if (Math.random() > 0.5f) {
                problems[i] = new subtractionProblem();
            }
            else {
                problems[i] = new additionProblem();
            }
            // initialize problem.
            problems[i].generateProblem();
        }

        // go through list and ask user to solve problems.
        for (int i = problems.length-1; i > 0; --i) {
            problems[i].askUserToSolve(scanner);
        }
    }
}
