package osu.cse2123;

import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A program that generates an inventory report
 * 
 * @name Maria Caterina Fehn
 * @version 9 March 2023
 * 
 */

public class InventoryReport {
	
	private static String line = "----------------------------------------------------------------------";

	public static void readFile(String fname, Scanner txtscnr) {

		try {
			
			System.out.println("Product Inventory Summary Report");
			System.out.println(line);
			File textFile = new File(fname);
			txtscnr = new Scanner(textFile);
			String[] column;
			Product prod = new Product();
			System.out.printf("%-25s %-10s %-4s %-6s %-6s %-6.6s %-6.6s%n", "Product Name", "I Code", "Type", "Quant.", "Price",  "Rating", "# Rat.");
			System.out.printf("%-25s %-10s %-4s %-6s %-6s %-6.6s %-6.6s%n", "-------------------------", "----------", "----" ,"------" ,"------", "------" ,"------");
			while (txtscnr.hasNext()) {
				String readFile = txtscnr.nextLine();
				column = readFile.split(",");
				title(prod, column);
				inventoryCode(prod, column);
				type(prod, column);
				quantity(prod, column);
				price(prod, column);
				numRating(prod, column);
			}
			System.out.println(line);
			analytics();
			System.out.print(line);
		}
		catch (FileNotFoundException e) {
			System.out.println("ERROR! File " + fname + " not found!");
		}
	}
	
	public static void splitColumn(ArrayList<String> rowNum, String[] column, int columnNum) {
		rowNum.add(column[columnNum]);
	}
	
	public static void title(Product prod, String[] column) {
		prod.setName(column[0]);
		System.out.printf("%-25.25s ", prod.getName());
	}
	
	public static void inventoryCode(Product prod, String[] column) {
		prod.setInventoryCode(column[1]);
		System.out.printf("%-10.10s ", prod.getInventoryCode());
	}
	
	public static void type(Product prod, String[] column) {
		prod.setType(column[4]);
		System.out.printf("%-4.4s ", prod.getType());
	}
	
	public static void quantity(Product prod, String[] column) {
		prod.setQuantity(Integer.parseInt(column[2]));
		System.out.printf("%6d ", prod.getQuantity());
	}
	
	public static void price(Product prod, String[] column) {
		prod.setPrice(Integer.parseInt(column[3]));
		int priceInCents = prod.getPrice();
		double centsToDB = priceInCents;
		centsToDB /= 100;
		System.out.printf("%6.2f ", centsToDB);
	}
	
	public static void rating(Integer numRating, Product prod, String[] column) {
		int numStars = prod.getSum();
		StringBuilder sb = new StringBuilder();
		if (numRating != 0) {
			numStars = numStars / numRating;
		}
		for (int i = 0; i < numStars; i++) {
			sb.append("*");
		}
		System.out.printf("%6.6s", sb.toString());
		prod.resetArray();
	}
	
	public static void numRating(Product prod, String[] column) {
		for (int i = 5; i < column.length; i++) {
			prod.addUserRating(Integer.parseInt(column[i]));
		}
		int numRating = prod.getUserRatingCount();
		rating(numRating, prod, column);
		System.out.printf("%6d%n", numRating);
		
		prod.resetArray();
	}
	
	public static void analytics() {  //DEBUG: PARAMETERIZE THIS METHOD
		System.out.println("Total products in database: ");
		System.out.println("Highest Average Ranked Item: ");
		System.out.println("Lowest Average Ranked Item: ");
	}
	
	public static void main(String[] args) {
		Scanner userInput = new Scanner(System.in);
		System.out.println("Enter an inventory filename: ");
		String fname = userInput.nextLine();
		Scanner txtscnr = new Scanner(fname);
		readFile(fname,txtscnr);
		
		txtscnr.close();
		userInput.close();
	}

}