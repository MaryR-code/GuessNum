package Demo;

import java.util.Scanner;

public class ForDemo {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many times to repeat: ");
        int count = scanner.nextInt();

        for (int i = 0; i < count; i++) {
            System.out.println("Counting " + i);
            System.out.println("Do you want to stop?");
            if (scanner.next().equals("yes")) {
                break;
            }
        }

        System.out.println("Good bye");

    }
}
