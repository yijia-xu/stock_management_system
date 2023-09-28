import java.util.Formatter;
import java.util.Scanner;
/**
 * CET - CS Academic Level 3
 * Declaration: I declare that this is my own original work and is free from Plagiarism
 * This class is to add vegetable items and format for output.
 * Assignment #2
 * Student Name: Yijia Xu
 * Student Number: 041061204
 * Modified Date: June 18, 2023
 * Section #: 301
 * Course: CST8130 - Data Structures
 * Professor: James Mwangi PhD.
 */
public class Vegetable extends FoodItem {
    /**
     * Farm name
     */
    private String farmName;

    /**
     *Default constructor
     */
    public Vegetable() {
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
     * @param farmName - farm name
     */
    public Vegetable(int code, String name, int quantity, float cost, float price, String farmName){
        super(code, name, quantity, cost, price);
        this.farmName = farmName;
    }

    /**
     * A string to store all information of a vegetable item
     * @return a string of item information to print
     */
    @Override
    public String toString() {
        String str = super.toString() + " farm supplier: " + farmName;
        return str;
    }

    /**
     * Add a new item with the farm supplier to inventory
     * @param scanner - scan the user input
     * @return true if successfully added, return false if not
     */
    @Override
    public boolean addItem(Scanner scanner, boolean fromFile) {
        super.addItem(scanner, fromFile);
        try {
            System.out.print("Enter the name of the farm supplier:"); //call the super class to add item info to inventory
            scanner.nextLine();
            farmName = scanner.nextLine(); //read the farm name from user input
        } catch (Exception e) {
            return false; //fail to add item
        }
        return true;
    }

    /**
     * Formats vegetable info for file output.
     * @param writer - a Formatter type object for writing to file
     */
    public void outputItem(Formatter writer){
        writer.format("%s\n", "v");
        super.outputItem(writer);
        writer.format("%s\n", farmName);

    }
}
