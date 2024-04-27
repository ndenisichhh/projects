package game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Tournament {
	private final int countPlayers;
	private final Player[] places;
	private final Map<Player, Integer> playersNumber = new HashMap<>();
	private final List<Player> players;
	private final Random random = new Random();
	private final boolean log;
	private int number;
	private int index = 0;

	public Tournament(List<Player> players, boolean log) {
		this.players = players;
		this.log = log;
		countPlayers = players.size();
		for (int i = 0; i < countPlayers; i++) playersNumber.put(players.get(i), i + 1);
		places = new Player[countPlayers];
		number = places.length;
	}

	private void resultGame(int loser, int winner) {
		if (log) System.out.println("В этой битве игрок под номером " + winner + " одержал победу!"
				+ System.lineSeparator() + System.lineSeparator());
		places[places.length - number] = players.get(loser);
		players.remove(loser);
	}

	private void game(int i, int j, int m, int n, int k) {
		int pl1N = playersNumber.get(players.get(i));
		int pl2N = playersNumber.get(players.get(j));
		if (log) {
			System.out.println("Сейчас играют игроки под номерами " + Math.min(pl1N, pl2N) + " и " + Math.max(pl1N, pl2N));
			System.out.println("Игрок под номером " + pl1N + " играет за X");
			System.out.println("Игрок под номером " + pl2N + " играет за O");
			System.out.println();
		}
		int result;
		do {
			Board board = new MKNBoard(m, n, k);
			result = new Game(players.get(i), players.get(j), log).play(board);
		} while (result == 0);

		switch (result) {
			case 1:
				resultGame(j, pl1N);
				break;
			case 2:
				resultGame(i, pl2N);
				break;
		}
	}

	private int pow2(int times) {
		int res = 1;
		for (int i = 0; i < times; i++) res *= 2;
		return res;
	}

	private int pow2log2(int n) {
		while ((n & (n - 1)) != 0) n &= (n - 1);
		return n;
	}

	private void playGame(int m, int n, int k) {
		if (index + 1 >= players.size()) index = 0;
		if (random.nextInt(2) == 1) game(index, index + 1, m, n, k);
		else game(index + 1, index, m, n, k);
		number--;
		index++;
	}

	public void play(int m, int n, int k) {
		int firstsGames = countPlayers - pow2log2(countPlayers);
		while (firstsGames-- > 0) {
			playGame(m, n, k);
		}
		index = 0;
		while (number > 1) {
			playGame(m, n, k);
		}
		places[places.length - 1] = players.get(0);

		int p = 0;
		int ln = 0;
		while (ln < places.length) {
			if (p - 2 < 0) {
				System.out.println((p + 1) + " место занял игрок под номером " + playersNumber.get(places[places.length - ln - 1]));
				ln++;
			} else {
				System.out.print((p + 1) + " место заняли игроки под номерами ");
				for (int l = 0; l < pow2(p - 1) && ln < places.length; l++) {
					System.out.print(playersNumber.get(places[places.length - ln - 1]) + " ");
					ln++;
				}
				System.out.println();
			}
			p++;
		}
	}
}
