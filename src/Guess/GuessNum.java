package Guess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

public class GuessNum {

    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<GameResult> leaderboard = new ArrayList<>();
    private static File leaderboardFile = new File("leaderboard.txt");

    public static void main(String[] args) {
        loadLeaderboard();
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
                    gr.setStartTime(startTime);
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

        saveLeaderboard();
        printLeaderboard3();

        System.out.printf("Good bye, %s!", userName);
    }

    private static void loadLeaderboard() {
        try(var in = new Scanner(leaderboardFile)) {
            while (in.hasNext()) {
                var gr = new GameResult();
                gr.setName(in.next());
                gr.setAttempts(in.nextInt());
                gr.setDuration(in.nextLong());
                gr.setStartTime(in.nextLong());
                leaderboard.add(gr);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Cannot save leaderboard");
        } ;

    }

    private static void saveLeaderboard() {
        try (var out = new PrintWriter(leaderboardFile)) {
            for (var gr : leaderboard) {
                out.printf("%s %d %d %d\n", gr.getName(), gr.getAttempts(), gr.getDuration(), gr.getStartTime());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Cannot save leaderboard");
        }
    }

    private static void printLeaderboard() {        // simple
        leaderboard.sort(
                Comparator
                        .comparingInt(GameResult::getAttempts)
                        .thenComparingLong(GameResult::getDuration)
        );
        System.out.print("\n");
        System.out.printf("%-10S %10S %14S\n","name","attempts","duration");
        System.out.print("------------------------------------\n");
        for (GameResult gr : leaderboard) {
    //        System.out.printf("name: %s, attend: %d, time: %.1f\n",
            System.out.printf("%-10s %10d %10.1f sec\n",
                    gr.getName(), gr.getAttempts(), gr.getDuration()/1000.0);
        }
        System.out.print("------------------------------------\n");
    }

    private static void printLeaderboard2() {       // FOR-EACH with counter
        leaderboard.sort(
                Comparator
                        .comparingInt(GameResult::getAttempts)
                        .thenComparingLong(GameResult::getDuration)
        );
        var rows = 5;       // сколько результатов выводим
        for (GameResult gr : leaderboard) {
            System.out.printf("%s \t %d \t %.1f \n",
                    gr.getName(), gr.getAttempts(), gr.getDuration()/1000.0);
        //    rows--;
        //    if (rows == 0) {
            if (--rows == 0) {      // можно использовать предекремент --rows (лучше не использовать)
                break;
            }
        }
    }

    private static void printLeaderboard3() {       // FOR-EACH with sublist
        leaderboard.sort(
                Comparator
                        .comparingInt(GameResult::getAttempts)
                        .thenComparingLong(GameResult::getDuration)
        );
        var num = Math.min(5, leaderboard.size());      // сколько результатов выводим
        var sublist = leaderboard.subList(0, num);
        int max = 0;
        for (GameResult gr : sublist) {
            if (gr.getName().length() > max) {
                max = gr.getName().length();
            }
        }
        System.out.print("\n");
        System.out.printf("%-" + max+ "S %8S %12S %17S \n","name","attempts","time","start");
        for (int i = 0; i < max; i++) {
            System.out.print("-");
        }
        System.out.print("----------------------------------------\n");
        for (GameResult gr : sublist) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy HH:mm");
            Date startDate = new Date(gr.getStartTime());
            System.out.printf("%-" + max + "s %8d %8.1f sec    %s \n",
                    gr.getName(), gr.getAttempts(), gr.getDuration()/1000.0,sdf.format(startDate));
        }
        for (int i = 0; i < max; i++) {
            System.out.print("-");
        }
        System.out.print("----------------------------------------\n");
    }

    private static void printLeaderboard4() {       // FOR
        leaderboard.sort(
                Comparator
                        .comparingInt(GameResult::getAttempts)
                        .thenComparingLong(GameResult::getDuration)
        );
        for (int i = 0; i < 5 && i < leaderboard.size(); i++) {
            GameResult gr = leaderboard.get(i);
            System.out.printf("%d. %s \t %d \t %.1f \n",
                    i+1, gr.getName(), gr.getAttempts(), gr.getDuration()/1000.0);
        }
    }

    private static void printLeaderboard5() {       // Stream API

        leaderboard.stream()
            //    .filter(gr -> !gr.getName().equals("zzz"))
                .sorted(
                        Comparator
                                .comparingInt(GameResult::getAttempts)
                                .thenComparingLong(GameResult::getDuration)
                )
                .limit(5)
                .forEach(gr -> {
                    System.out.printf("%s \t %d \t %.1f \n",
                            gr.getName(), gr.getAttempts(), gr.getDuration()/1000.0);
                });
    }


    public static int askNumber(String msg, int min, int max) {

        while (true) {
            String massage = msg + ":> ";
            System.out.print(massage);

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
