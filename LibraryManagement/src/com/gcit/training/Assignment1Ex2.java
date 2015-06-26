package com.gcit.training;
import java.util.Scanner;

public class Assignment1Ex2 {

	private Player player1;
	private Player player2;
	private int totalChipsRemaining;
	private Scanner sc;

	private Player currentPlayer;

	private String gameString;

	// Assignment1Ex2() {
	// sc = new Scanner(System.in);
	// player1 = new Player();
	// player2 = new Player();
	// gameString = "y";
	// }

	private void reset() {
		sc = new Scanner(System.in);
		player1 = new Player();
		player2 = new Player();
		gameString = "y";
	}

	public static void main(String[] args) {
		new Assignment1Ex2().run();
	}

	public void run() {
		reset();
		while (gameString.toLowerCase().equals("y")) {
			reset();
			assignPlayerNames();

			runInitialPhase();

			takeTurns();
			askToContinue();
		}

	}

	private void askToContinue() {
		System.out.println("\n");
		System.out.print("Play another game? (y/n)");

		gameString = sc.nextLine();

		System.out.println();
	}

	private Player getOther() {
		return currentPlayer == player1 ? player2 : player1;
	}

	private void takeTurns() {
		currentPlayer = player1;
		while (!isGameOver()) {
			if (currentPlayer.getNumChips() == 1) {
				System.out.println(currentPlayer.getName() + " has "
						+ currentPlayer.getNumChips() + " chip.");
			} else {
				System.out.println(currentPlayer.getName() + " has "
						+ currentPlayer.getNumChips() + " chips.");
			}

			if (getOther().getNumChips() == 1) {
				System.out.println(getOther().getName() + " has "
						+ getOther().getNumChips() + " chip.");
			} else {
				System.out.println(getOther().getName() + " has "
						+ getOther().getNumChips() + " chips.");
			}

			System.out.println("It is your turn, " + currentPlayer.getName()
					+ ".");
			if (totalChipsRemaining > 1) {
				System.out.println("There are " + totalChipsRemaining
						+ " chips remaining.");
				System.out.print("You may take any number of chips from 1 to "
						+ totalChipsRemaining / 2
						+ " .    How many will you take, "
						+ currentPlayer.getName() + "? ");
			} else {
				System.out.println("There is 1 chip remaining.");
				System.out.print("How many will you take, "
						+ currentPlayer.getName() + "? ");
			}

			// the number of chips that the current player wants to pick
			int chipsWanted = Integer.parseInt(sc.nextLine());
			while (!isValidMove(chipsWanted)) {
				printNonValid(chipsWanted);
				System.out.print("How many will you take, "
						+ currentPlayer.getName() + "? ");
				chipsWanted = Integer.parseInt(sc.nextLine());
			}

			changePlayer(chipsWanted);

			if (!isGameOver()) {
				System.out
						.println("\n* * * * * * * * * * * * * * * * * * * * * * * * * * *\n");
			} else {
				printResult();
			}

		}
	}

	private void printResult() {
		System.out.println();
		System.out.println(player1.getName() + " has " + player1.getNumChips()
				+ " chips.");
		System.out.println(player2.getName() + " has " + player2.getNumChips()
				+ " chips.");

		Player winner = player1.getNumChips() % 2 == 0 ? player1 : player2;

		System.out.println(winner.getName() + " wins!");
	}

	private void changePlayer(int chipsWanted) {
		currentPlayer.setNumChips(currentPlayer.getNumChips() + chipsWanted);
		totalChipsRemaining -= chipsWanted;

		// now change the player
		if (currentPlayer == player1) {
			currentPlayer = player2;
		} else {
			currentPlayer = player1;
		}
	}

	private void printNonValid(int chipsWanted) {
		if (chipsWanted < 1) {
			System.out
					.println("Illegal move: you must take at least one chip.");
		} else {
			System.out.println("Illegal move: you may not take more than "
					+ totalChipsRemaining / 2 + " chips.");
		}
	}

	private boolean isValidMove(int chipsPicked) {
		if (chipsPicked == 1) {
			return true;
		}
		return chipsPicked > 0 && chipsPicked <= totalChipsRemaining / 2;
	}

	private boolean isGameOver() {
		return totalChipsRemaining == 0;
		// return player1.getNumChips() + player2.getNumChips() ==
		// totalChipsRemaining;
	}

	private void runInitialPhase() {
		System.out.print("How many chips does the initial pile contain? ");

		totalChipsRemaining = Integer.parseInt(sc.nextLine());
		while (totalChipsRemaining < 3 || totalChipsRemaining % 2 == 0) {
			if (totalChipsRemaining < 3) {
				System.out
						.print("You have to start with at least 3 chips.    Choose another number: ");
			} else {
				System.out
						.print("You have to start with an odd number of chips.    Choose another number: ");
			}
			totalChipsRemaining = Integer.parseInt(sc.nextLine());
		}

		// sc.close();

		System.out
				.println("\n* * * * * * * * * * * * * * * * * * * * * * * * * * *\n");
	}

	private void assignPlayerNames() {

		// Read the first player's name
		System.out.print("What is the name of the first player? ");
		player1.setName(sc.nextLine());

		// Read the second player's name
		System.out.print("What is the name of the second player? ");
		player2.setName(sc.nextLine());

		while (player2.getName().toLowerCase()
				.equals(player1.getName().toLowerCase())) {
			System.out.print("Both players cannot be named "
					+ player2.getName() + "." + "    Enter a different name: ");
			player2.setName(sc.nextLine());
		}
	}
}
