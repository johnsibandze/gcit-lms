package com.gcit.training;

import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class LibMangApp {

	private Scanner sc;

	private User user;

	public LibMangApp() {
		sc = new Scanner(System.in);
	}

	public static void main(String[] args) {
		new LibMangApp().run();
	}

	public void run() {
		mainMenu();
	}

	private void mainMenu() {
		System.out
				.println("Welcome to the GCIT Library Management System. Which category of a user are you\n");

		System.out.println("1) Librarian");
		System.out.println("2) Administrator");
		System.out.println("3) Borrower");

		int userInt = Integer.parseInt(sc.nextLine());

		if (userInt == 1) {
			handleLibrarian();

			// lib1Menu();
		} else if (userInt == 2) {
			// TODO handle the administrator
		} else {
			handleBorrower();
		}
	}

	// TODO Stop duplicating these methods. Will make one method in User
	private void handleLibrarian() {
		user = new Librarian();

		Librarian librarian = (Librarian) user;
		librarian.lib1Menu();

		if (librarian.isStartMainMenu()) {
			librarian.setStartMainMenu(false);
			mainMenu();
		}
	}

	private void handleBorrower() {
		user = new Borrower();

		Borrower borrower = (Borrower) user;
		borrower.enterCardNo();

		if (borrower.isStartMainMenu()) {
			borrower.setStartMainMenu(false);
			mainMenu();
		}
	}

}
