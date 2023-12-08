import Stock.java;
public class Main {
    //Run the program in the main method.
    public static void main(String[] args) {
        //Create a new Stock object with the specified data using the constructor.
        Stock ORCLStock = new Stock("ORCL", "Oracle Corporation", 34.5, 34.35);
        Stock YAHOOStock = new Stock("YAHOO", "Yahoo", 34.5, 34.35);
        //Print the change in percent of the stock using the getChangePercent method.
        System.out.println("The percent change is " + ORCLStock.getChangePercent() + "%");
    }
}
