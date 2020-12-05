package Guess;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class GuessNum {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        Random random = new Random();
        String userName = "";

        do {
            int myNum = random.nextInt(100) + 1;
            System.out.println("(" + myNum + ")");    // вывод на экран (УБРАТЬ)
            System.out.println("Hello! What is your name?");
            userName = scanner.next();
            System.out.println("Try to guess my number (from 1 to 100)!");
            boolean userWin = false;

            for (int attempt = 1; attempt <= 10; attempt++) {
                System.out.printf("Attempt #%d. ", attempt);
                int userNum = askNumber("Your guess", 1, 100);

                if (myNum > userNum) {
                    System.out.println("Too low");
                } else if (myNum < userNum) {
                    System.out.println("Too high");
                } else {
                    System.out.println("You won!");
                    userWin = true;
                    break;
                }
            }
            if (!userWin) {
                System.out.printf("Your lost! My number was %d\n", myNum);
            }

        } while (askYesOrNo("Do you want to repeat? (Yes/No)"));
        System.out.printf("Good byu, %s!", userName);
    }

    public static int askNumber(String msg, int min, int max) {

        while (true) {
            System.out.print(msg + ":> ");

            try {
                int result = scanner.nextInt();

                if (result < min) {
                    System.out.println("Number should not be less than " + min);
                    continue;
                }
                if (result > max) {
                    System.out.println("Number should not be greater than " + max);
                    continue;
                }
                return result;

            } catch (InputMismatchException e) {
    //            System.out.println("Error - " + e.getMessage());
                String input = scanner.next();
                System.out.println("This is not a number");
            }
        }
    }

    public static boolean askYesOrNo(String msg) {

        while (true) {
            System.out.println(msg);
            String result = scanner.next();

            if (result.equalsIgnoreCase("yes")) {
                return true;
            } else if (result.equalsIgnoreCase("no")) {
                return false;
            } else {
                System.out.println("I don't understand... ");
            }
        }
    }

}
