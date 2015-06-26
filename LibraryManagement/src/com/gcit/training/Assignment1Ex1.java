package com.gcit.training;
import java.util.Scanner;

public class Assignment1Ex1 {

	private Scanner sc;
	private String continueString;

	public static void main(String[] args) {
		new Assignment1Ex1().run();
	}

	public void run() {
		sc = new Scanner(System.in);
		continueString = "y";
		while (continueString.toLowerCase().equals("y")) {
			start();
			askToContinue();
		}

		sc.close();
	}

	private void askToContinue() {
		System.out.print("Do you want to continue? (y/n) ");
		continueString = sc.nextLine();
	}

	private void start() {
		int programInt = (int) (Math.random() * 30);

		// comment out to check code functionality
		// System.out.println(programInt);

		System.out.println("Input a guess");

		for (int counter = 0; counter < 5; counter++) {
			String s = sc.nextLine();

			while (!isValidInt(s)) {
				System.out.println("Please enter a number");
				s = sc.nextLine();
			}

			int userInt = Integer.parseInt(s);

			if (Math.abs(userInt - programInt) <= 10) {
				System.out.println("The correct number is: " + programInt);
				break;
			} else {
				if (counter == 4) {
					System.out.println("Sorry");
				} else {
					System.out.println("Keep trying");
				}
			}
		}
	}

	private static boolean isValidInt(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (!(s.charAt(i) >= '0' && s.charAt(i) <= '9')) {
				return false;
			}
		}

		return true;
	}

}
