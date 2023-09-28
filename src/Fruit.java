import java.util.Formatter;
import java.util.Scanner;
/**
 * CET - CS Academic Level 3
 * Declaration: I declare that this is my own original work and is free from Plagiarism
 * This class is to add fruit items and format for output.
 * Assignment #2
 * Student Name: Yijia Xu
 * Student Number: 041061204
 * Modified Date: June 18, 2023
 * Section #: 301
 * Course: CST8130 - Data Structures
 * Professor: James Mwangi PhD.
 *
 */
public class Fruit extends FoodItem {
	/**
	 * A string represents orchard name
	 */
    private String orchardName;

    /**
     * Default constructor.
     */
    public Fruit() {
        //call super class to inherit item properties
        super();
    }

    /**
     * Overloaded constructor. Inherits item properties from super class.
     * @param code - item code
     * @param name - item name
     * @param quantity - item quantity
     * @param cost - item cost
     * @param price - item price
     * @param orchardName - orchard name
     */
    public Fruit(int code, String name, int quantity, float cost, float price, String orchardName){
        super(code, name, quantity, cost, price);
        this.orchardName = orchardName;
    }

    /**
     * A string to store all information of a fruit item
     *
     * @return a string of item information to print
     */
    @Override
    public String toString() {
        String str = super.toString() + " orchard supplier: " + orchardName;
        return str;
    }

    /**
     * Add a new item with the orchard supplier to inventory
     *
     * @param scanner - scan the user input
     * @return true if successfully added, return false if not
     */
    @Override
    public boolean addItem(Scanner scanner, boolean fromFile) {
        try {
            super.addItem(scanner, fromFile); //call the super class to add item info to inventory
            System.out.print("Enter the name of the orchard supplier:");
            scanner.nextLine();
            orchardName = scanner.nextLine(); //read the orchard name
        } catch (Exception e) {
            return false; //fail to add item
        }
        return true;
    }

    /**
     * Formats fruit info for file output.
     * @param writer - a Formatter type object for writing to file
     */
    @Override
    public void outputItem(Formatter writer){
        writer.format("%s\n", "f");
        super.outputItem(writer);
        writer.format("%s\n", orchardName);
    }

}

