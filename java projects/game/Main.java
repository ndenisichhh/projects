package game;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // wants bot + player
        Scanner sc = new Scanner(System.in);
        int m, n, k;
        while (true) {
            System.out.print("Введите m, n и k: ");
            try {
                m = sc.nextInt();
                n = sc.nextInt();
                k = sc.nextInt();
                if (m <= 0 || n <= 0) {
                    System.out.println("m и n - положительные числа!");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("m, n и k должны быть числами!");
            }
            sc.nextLine();
        }
        String needTour;
        while (true) {
            System.out.print("Вы хотите провести тунир ? (Y/N) ");
            needTour = sc.next();
            if (!needTour.equals("Y") && !needTour.equals("N")) {
                System.out.println("Введите Y или N!");
                sc.nextLine();
                continue;
            }
            sc.nextLine();
            break;
        }
        if (needTour.equals("Y")) {
            int countPlayers;
            while (true) {
                System.out.print("На сколько игроков турнир ?: ");
                try {
                    countPlayers = sc.nextInt();
                    if (countPlayers <= 0) {
                        System.out.println("Введите положительное число!");
                        continue;
                    }
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Введите число игроков!");
                }
                sc.nextLine();
            }
            List<Player> players = new ArrayList<>();
            for (int i = 0; i < countPlayers; i++) players.add(new RandomPlayer());
            players.add(new HumanPlayer());
            countPlayers++;
            String needLog;
            while (true) {
                System.out.print("Хотите выводить доски ? (Y/N) ");
                needLog = sc.next();
                if (!needLog.equals("Y") && !needLog.equals("N")) {
                    System.out.println("Введите Y или N!");
                    sc.nextLine();
                    continue;
                }
                sc.nextLine();
                break;
            }
            boolean log = needLog.equals("Y");
            System.out.println();
            new Tournament(players, log).play(m, n, k);
        } else {
            Board board = new MKNBoard(m, n, k);
            System.out.println("Создано поле размером " + m + " на " + n +
                    ". Чтобы выиграть нужно собрать k=" + k +
                    " своих значков по горизонтали, вертикали или диагонали. " +
                    "Первый ходит крестик");
            System.out.println();
            Player player1 = new HumanPlayer();
            Player player2 = new HumanPlayer();
            int result = new Game(player1, player2, true).play(board);
            if (result == 0) System.out.println("Ничья.");
            else System.out.println("Победил " + result + " игрок.");
        }
    }
}
