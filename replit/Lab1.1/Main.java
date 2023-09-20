import java.util.Scanner;

class Main {
    public static boolean isInteger(String val){
        int length = val.length();
        if (val == null || length <= 0){
            return false;
        }

        int i = 0;
        if (val.charAt(0) == '-'){
            i=1;
        }

        for (; i < length; i++) {
            char c = val.charAt(i);
            if (c < '0' && c > '9'){
                return false;
            }
        }

        return true;
    }
    public static void main(String args[]) {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Name?");
            String name = scanner.nextLine();
            
            System.out.println("How many classes?");
            String numClasses = scanner.nextLine();

            if (isInteger(numClasses)){
                System.out.printf("Your name is %s and you are in %d classes.", name, (int)Integer.parseInt(numClasses));
                break;
            }
        }
    }
}
