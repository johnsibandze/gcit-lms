package com.gcit.training;
import java.util.*;

public class SetDemo {

	public static void main(String[] args) {

		// Create a set called mySet
		Set mySet = new HashSet();
		// Ensure that this set contains an interesting selection of fruit
		String fruit1 = "pear", fruit2 = "banana", fruit3 = "tangerine", fruit4 = "strawberry", fruit5 = "blackberry";
		mySet.add(fruit1);
		mySet.add(fruit2);
		mySet.add(fruit3);
		mySet.add(fruit2);
		mySet.add(fruit4);
		mySet.add(fruit5);
		// Display contents of mySet
		System.out.println("mySet now contains:");
		System.out.println(mySet);

		// the cardinality of mySet
		System.out.println(mySet.size());

		// remove the blackberry and strawberry
		mySet.remove(fruit5);
		mySet.remove(fruit4);
		
		// display the contents to ensure success
		System.out.println(mySet);
		
		// removing the remaining fruit in a single method invocation
		mySet.removeAll(mySet);
		
		// showing that the set is now empty
		System.out.println(mySet.isEmpty());
		
		
		
		
		
		
		
		
		
	}
}