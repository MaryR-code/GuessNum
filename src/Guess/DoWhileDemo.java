package Guess;

import java.util.Scanner;

public class DoWhileDemo {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("Doing something useful!");
            System.out.println("Do you want to repeat?");
        } while (scanner.next().equals("yes"));

        System.out.println("Good bye");

    }
}
