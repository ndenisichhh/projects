package game;

import java.util.InputMismatchException;
import java.util.Scanner;

public class HumanPlayer implements Player{

    @Override
    public Move makeMove(Position position) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("Введите строку и столбец: ");
                int row = sc.nextInt() - 1;
                int col = sc.nextInt() - 1;
                sc.nextLine();
                System.out.println();
                Move move = new Move(row, col, position.getTurn());
                if (position.isValid(move)) return move;
                System.out.println("Этот ход является недопустимым по правилам игры! Измените ход");
            } catch (InputMismatchException e) {
                System.out.println("Ваш ход должен содержать два положительных числа!");
                sc.nextLine();
            }
        }
    }
}
