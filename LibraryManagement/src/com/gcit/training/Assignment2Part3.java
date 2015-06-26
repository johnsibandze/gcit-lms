package com.gcit.training;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;

public class Assignment2Part3 {

	public static void main(String[] args) {

		SortedMap<String, Double> alphabetical = new TreeMap<String, Double>();
		SortedMap<Double, ArrayList<String>> merit = new TreeMap<Double, ArrayList<String>>();

		HashMap<String, Integer> numRecords = new HashMap<String, Integer>();

		// The name of the file to open.
		String fileName = "MyFile";

		// The next line on the file
		String line = null;
		try {
			FileReader fileReader = new FileReader(fileName);

			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				String name = line.split(" ")[0];
				int mark = Integer.parseInt(line.split(" ")[1]);

				if (alphabetical.containsKey(name)) {
					alphabetical.put(name, alphabetical.get(name) + mark);
					numRecords.put(name, numRecords.get(name) + 1);
				} else {
					alphabetical.put(name, (double) mark);
					numRecords.put(name, 1);
				}
			}

			// close file after reading
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			// print the name of the file
			System.out.println("Error reading file '" + fileName + "'");
		}

		for (String s : alphabetical.keySet()) {
			alphabetical.put(s, alphabetical.get(s) / numRecords.get(s));
		}

		// to be used for formatting
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(1);

		// Print alphabetical order
		System.out.println("Alpha order");
		for (String s : alphabetical.keySet()) {
			if (alphabetical.get(s) == Math.floor(alphabetical.get(s))) {
				System.out.println(s + " " + numRecords.get(s) + " "
						+ alphabetical.get(s));
			} else {
				System.out.println(s + " " + numRecords.get(s) + " "
						+ df.format(alphabetical.get(s)));
			}
		}

		// now print merit order
		for (String s : alphabetical.keySet()) {
			if (merit.containsKey(alphabetical.get(s))) {
				double key = alphabetical.get(s);
				merit.get(key).add(s);
			} else {
				double key = alphabetical.get(s);

				merit.put(key, new ArrayList<String>());
				merit.get(key).add(s);
			}
		}

		HashMap<String, Integer> rank = new HashMap<String, Integer>();
		// the names ranked from lowest score to highest score
		ArrayList<String> rankNames = new ArrayList<String>();
		int i = merit.size();
		for (double d : merit.keySet()) {
			for (String s : merit.get(d)) {
				rank.put(s, i);
				rankNames.add(s);
			}
			i--;
		}

		// now printing the merit order
		System.out.println("\nMerit order");
		for (int j = rankNames.size() - 1; j >= 0; j--) {
			if (alphabetical.get(rankNames.get(j)) == Math.floor(alphabetical
					.get(rankNames.get(j)))) {

				System.out.println(rank.get(rankNames.get(j)) + " "
						+ rankNames.get(j) + " "
						+ numRecords.get(rankNames.get(j)) + " "
						+ alphabetical.get(rankNames.get(j)));
			} else {
				System.out.println(rank.get(rankNames.get(j)) + " "
						+ rankNames.get(j) + " "
						+ numRecords.get(rankNames.get(j)) + " "
						+ df.format(alphabetical.get(rankNames.get(j))));
			}
		}

		System.out.println();

		// calculate the average mark
		double avgMark = 0;
		for (String s : alphabetical.keySet()) {
			avgMark += alphabetical.get(s);
		}

		avgMark /= rankNames.size();

		double sumSquareDev = 0;
		for (String s : alphabetical.keySet()) {
			sumSquareDev += (avgMark - alphabetical.get(s))
					* (avgMark - alphabetical.get(s));
		}

		double sd = Math.sqrt(sumSquareDev / (rankNames.size() - 1));

		// now print the # of students, average mark and standard deviation

		System.out.println("Number of students: " + rankNames.size());
		System.out.println("Average student mark: " + df.format(avgMark));
		System.out.println("Standard deviation: " + df.format(sd));

	}
}
