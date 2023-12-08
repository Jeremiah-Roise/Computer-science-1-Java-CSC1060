import java.util.Scanner;
public class Main {
    //public static void print2dArray(char[][] arr){
    //    String output = "";
    //    for (int i = 0; i < arr.length; i++) {
    //        for (int j = 0; j < arr.length; j++) {
    //            output += arr[j][i];
    //        }
    //        output += "\n";
    //    }
    //    System.out.println(output);
    //}

    public static void main(String[] args) {
        char[][] arr2d = new char[3][3];
        
        int userinput = 0;
        while (true) {
            System.out.print("Enter a number between 0 and 511: ");
            userinput = new Scanner(System.in).nextInt();
            if (userinput >= 0 && userinput <= 511) {
               break; 
            }
        }

        int row = 2;
        int column = 2;
        for (int i = 9; i > 0; i--) {
            arr2d[column][row] = 'H';
            if (((userinput >> i-1)& 1) == 1){
                arr2d[column][row] = 'T';
                System.out.println("T");
            }
            else{
                System.out.println("H");
            }

            column--;
            if (column == -1){
                System.out.println();
                column = 2;
                row--;
            }
        }
        print2dArray(arr2d);
    }
}
