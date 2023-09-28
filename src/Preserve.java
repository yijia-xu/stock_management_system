import java.util.Formatter;
import java.util.Scanner;

/**
 * CET - CS Academic Level 3
 * Declaration: I declare that this is my own original work and is free from Plagiarism
 * This class is to add preserve items and format for output.
 * Assignment #2
 * Student Name: Yijia Xu
 * Student Number: 041061204
 * Modified Date: June 18, 2023
 * Section #: 301
 * Course: CST8130 - Data Structures
 * Professor: James Mwangi PhD.
 */
public class Preserve extends FoodItem {
	/**
	 * An integer represents jar size
	 */
    private int jarSize;

    /**
     * Default constructor
     */
    public Preserve() {
        //call super class to inherit item info
        super();
    }

    /**
     * Overloaded constructor. Inherits item properties from super class.
     * @param code - item code
     * @param name - item name
     * @param quantity - item quantity
     * @param cost - item cost
     * @param price - item price
     * @param jarSize - jar size (positive integer only)
     */
    public Preserve(int code, String name, int quantity, float cost, float price, int jarSize){
        super(code, name, quantity, cost, price);
        this.jarSize = jarSize;
    }

    /**
     * A string to store all information of a preserve item
     * @return a string of item information to print
     */
    @Override
    public String toString() {
        String str = super.toString() + " size: " + jarSize + "ml";
        return str;

    }

    /**
     * Add a new item with the jar size information to inventory
     * @param scanner - scan the user input
     * @return true if successfully added, return false if not
     */
    @Override
    public boolean addItem(Scanner scanner, boolean fromFile) {
        try {
            super.addItem(scanner, fromFile); //call the super class to add item info to inventory
            jarSize = super.addPositiveInt("Enter the size of the jar in millilitres:", scanner); //read the jar size from user input
        } catch (Exception e) { 
            return false; //fail to add item
        }
        return true;

    }

    /**
     * Formats preserve info for file output.
     * @param writer - a Formatter type object for writing to file
     */
    public void outputItem(Formatter writer){
        writer.format("%s\n", "p");
        super.outputItem(writer);
        writer.format("%s\n", jarSize);

    }
}
