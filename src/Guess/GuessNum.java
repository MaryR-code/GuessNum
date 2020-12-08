package Guess;

import java.util.*;

public class GuessNum {

    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<GameResult> leaderboard = new ArrayList<>();

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
            long startTime = System.currentTimeMillis();    // начало игры

            for (int attempt = 1; attempt <= 10; attempt++) {
                System.out.printf("Attempt #%d. ", attempt);
                int userNum = askNumber("Your guess", 1, 100);

                if (myNum > userNum) {
                    System.out.println("Too low");
                } else if (myNum < userNum) {
                    System.out.println("Too high");
                } else {
                    long endTime = System.currentTimeMillis();
                    
                    GameResult gr = new GameResult();   // записываем победителя в список
                    gr.setName(userName);
                    gr.setAttempts(attempt);
                    gr.setDuration(endTime-startTime);
                    leaderboard.add(gr);

                    System.out.println("You won!");
                    userWin = true;
                    break;
                }
            }
            if (!userWin) {
                System.out.printf("Your lost! My number was %d\n", myNum);
            }

        } while (askYesOrNo("Do you want to repeat?"));

        leaderboard.sort(
                Comparator
                        .comparingInt(GameResult::getAttempts)
                        .thenComparingLong(GameResult::getDuration)
        );

        System.out.printf("\n");
        System.out.printf("%-10S %10S %14S\n","name","attempts","duration");
        System.out.printf("------------------------------------\n");

        for (GameResult gr : leaderboard) {
    //        System.out.printf("name: %s, attend: %d, time: %.1f\n",
            System.out.printf("%-10s %10d %10.1f sec\n",
                    gr.getName(),
                    gr.getAttempts(),
                    gr.getDuration()/1000.0);
        }

        System.out.printf("------------------------------------\n");
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
            System.out.println(msg + " (Yes/No)");
            String result = scanner.next();

            if (result.equalsIgnoreCase("yes")) {
                return true;
            } else if (result.equalsIgnoreCase("no")) {
                return false;
            }
            System.out.println("I don't understand...");
        }
    }

}
