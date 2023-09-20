import java.util.Scanner;

public class Main {
    public static boolean isValidNumber(String val){
    int length = val.length();
    if (val == null || length <= 0){
      return false;
    }

    int i = 0;
    if(val.charAt(0) == '-'){
        i = 1;
    }

    // simple ascii range filter.
    for (; i < length; i++) {
      char c = val.charAt(i);

      // make sure current char is a number.
      if (c < '0' && c > '9'){
        return false;
      }
    }

    return true;
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Integer? ");
        String val = scanner.nextLine();
        if (isValidNumber(val)){
            int number = Integer.parseInt(val);
            System.out.printf("The integer you entered is %d.\n", number);
            System.out.printf("The next integer is %d.", (++number));
        }
    }
}
