package com.gcit.training;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
			user = new Librarian();

			Librarian librarian = (Librarian) user;
			librarian.lib1Menu();

			if (librarian.isStartMainMenu()) {
				librarian.setStartMainMenu(false);
				mainMenu();
			}

			// lib1Menu();
		} else if (userInt == 2) {
			// TODO handle the administrator
		} else {
			// TODO handle the borrower
			user = new Borrower();
			((Borrower) user).enterCardNo();
		}
	}

}
