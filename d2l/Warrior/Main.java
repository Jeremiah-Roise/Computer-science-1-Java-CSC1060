//package edu.arapahoe.csc160;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.Random;

public class Main {

	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        WarriorFred[] warriors = new WarriorFred[5];
        for (int i = 0; i < warriors.length; i++) {
            warriors[i] = new WarriorFred();
        }
        warriors[0].setNickname("Fezzik");
        warriors[1].setNickname("Princess Buttercup");
        warriors[2].setNickname("Westley");
        warriors[3].setNickname("Vizzini");
        warriors[4].setNickname("Montoya");
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            System.out.println("      |===|");
            System.out.println("Round | " + i +" |");
            System.out.println("      |===|");
            for (WarriorFred j : warriors) {
                j.takeDamage(rand.nextInt(11));
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    System.out.println("well thats strange the application failed to delay");
                    return;
                }
            }
            for (WarriorFred j : warriors) {
                j.printInfo();
            }
            System.out.print("Please press Enter to see the next Round\033[1;31m!\033[1;39m");
            scanner.nextLine();
            System.out.println();
        }
	}
}
