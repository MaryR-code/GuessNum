package Guess;

import java.util.Scanner;

public class WhileDemo {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to start?");

        while (scanner.next().equals("yes")) {
            System.out.println("Doing something...");
            System.out.println("Do you want to repeat?");

        }

        System.out.println("Good bye");

    }
}
