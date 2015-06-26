package com.gcit.training;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Assignment2Part2 {

	public static void main(String[] args) {
		// constructing a list and adding the fruits
		List<String> list = new ArrayList<String>();

		String pear = "pear", banana = "banana", tangerine = "tangerine", strawberry = "strawberry", blackberry = "blackberry";

		list.add(pear);
		list.add(banana);
		list.add(tangerine);
		list.add(strawberry);
		list.add(blackberry);

		// displaying the contents in order using the list iterator

		Iterator<String> i = list.iterator();

		System.out.print("[" + i.next());
		while (i.hasNext()) {
			System.out.print(", " + i.next());
		}
		System.out.println("]");

		// displaying the contents in reverse order using the ListIterator
		ListIterator<String> li = list.listIterator();

		// first move the cursor to the end of the list
		while (li.hasNext()) {
			li.next();
		}

		// then now iterate backwards
		System.out.print("[" + li.previous());
		while (li.hasPrevious()) {
			System.out.print(", " + li.previous());
		}
		System.out.println("]");

		// inserting a second banana between the tangerine and the strawberry
		String secondBanana = "banana";
		list.add(3, secondBanana);

		// printing to ensure that banana was inserted properly
		System.out.println(list);

	}

}
