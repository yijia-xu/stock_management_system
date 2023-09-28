import java.util.Formatter;
import java.util.Scanner;
/**
 * CET - CS Academic Level 3
 * Declaration: I declare that this is my own original work and is free from Plagiarism
 * This class is to collect and format FoodItem information by user input.
 * Assignment #2
 * Student Name: Yijia Xu
 * Student Number: 041061204
 * Modified Date: June 18, 2023
 * Section #: 301
 * Course: CST8130 - Data Structures
 * Professor: James Mwangi PhD.
 *
 */
public class FoodItem implements Comparable<FoodItem>{
    /**
     * Item code
     */
    private int itemCode;
    /**
     * Item name
     */
    private String itemName;
    /**
     * Item price
     */
    private float itemPrice;
    /**
     * Current item quantity in stock
     */
    private int itemQuantityInStock;
    /**
     * Cost of the item
     */
    private float itemCost;

    /**
     * Default constructor
     */
    public FoodItem() {

    }

    /**
     * Overloaded constructor.
     * @param code - item code
     * @param name - item name
     * @param quantity - item quantity
     * @param cost - item cost
     * @param price - item price
     */
    public FoodItem(int code, String name, int quantity, float cost, float price){
        this.itemCode = code;
        this.itemName = name;
        this.itemQuantityInStock = quantity;
        this.itemCost = cost;
        this.itemPrice = price;
    }

    /**
     * Update item quantity in stock
     * @param amount - quantity to add into stock
     * @return true if successfully updated, return false if the stock is less than 0 (cannot sell)
     */
    public boolean updateItem(int amount) {
        int updated = amount + itemQuantityInStock;
        if (updated < 0) //not have enough stock to sell
            return false;
        else {
            itemQuantityInStock = updated; //update current quantity
            return true;
        }
    }

    /**
     * Check if the input item code equals to item code in inventory
     * @param item - item to check
     * @return true of the two item codes equals, return false if not
     */
    public boolean isEqual(FoodItem item) {
        return this.itemCode == item.itemCode;
    }

    /**
     * Add a new item to inventory
     * @param scanner - scan the user input
     * @param fromFile - item added from file for true, from console input for false
     * @return true if successfully added, return false if not
     */
    public boolean addItem(Scanner scanner, boolean fromFile) {
        Inventory inventory = new Inventory();
        if (!fromFile) { //read from console
            System.out.print("Enter the name for the item:");
            scanner.nextLine();

            itemName = scanner.nextLine();
            itemQuantityInStock = addPositiveInt("Enter the quantity for the item:", scanner);
            itemCost = addPositiveNum("Enter the cost of the item:", scanner);
            itemPrice = addPositiveNum("Enter the sales price of the item:", scanner);

            /* create an item object to store input information */
            FoodItem item = new FoodItem(itemCode, itemName, itemQuantityInStock, itemCost, itemPrice);
        } else { // read from file
            inventory.readFromFile(scanner);
        }
        return true;
    }

    /**
     * Prompt user to input item code.
     * @param scanner - scan the user input
     * @param fromFile - item added from file for true, from console input for false
     * @return true if the code is valid
     */
    public boolean inputCode(Scanner scanner, boolean fromFile) {
        if (!fromFile) { //read from console
            while (true) {
                System.out.print("Enter the code for the item:");
                try {
                    String input = scanner.next(); //scan the input
                    /*if the input is an integer, return true*/
                    itemCode = Integer.parseInt(input);
                    return true;
                } catch (NumberFormatException e) { //input is not an integer
                    System.out.println("Invalid code");
                }
            }
        } else { //read from file
            itemCode = getItemCode();
        }
        return true;
    }

    /**
     * Getter from itemCode
     * @return itemCode - item code
     */
    public int getItemCode(){
        return itemCode;
    }

    /**
     * Compares current food item with compared item
     * @param item - item to be compared
     * @return a negative int if this item is smaller than compared item, return a
     * positive int if this item is larger than compared item.
     * Return 0 for equal item codes.
     */
    @Override
    public int compareTo(FoodItem item) {
        int comparedCode = item.getItemCode(); //acquire compared itemCode
        return this.itemCode - comparedCode;
    }

    /**
     * Formats item info for file output.
     * @param writer - a Formatter type object for writing to file
     */
    public void outputItem(Formatter writer) {
        writer.format("%d\n%s\n%d\n%.2f\n%.2f\n", itemCode, itemName, itemQuantityInStock, itemCost, itemPrice);
    }

    /**
     * A string to store all information of an item
     * @return a string of item information to print
     */
    @Override
    public String toString() {
        /*keep 2 significant numbers*/
        String price = String.format("%.2f", itemPrice);
        String cost = String.format("%.2f", itemCost);

        return "Item: " + itemCode + " " + itemName + " " + itemQuantityInStock + " price: $" + price
                + " cost: $" + cost;
    }

    /**
     * Prompts for a positive number
     * @param prompt - ask user to enter value
     * @param scanner - scan the user input
     * @return a positive float value
     */
    public float addPositiveNum(String prompt, Scanner scanner) {
        while (true) {
            System.out.print(prompt);
            scanner.nextLine();
            if (scanner.hasNextFloat()) {
                float next = scanner.nextFloat(); //read a float value
                if (next > 0) { //check if it is positive
                    return next;
                } else { //input is a negative number
                    System.out.println("Invalid entry.");
                }
            } else { //input is not a number
                System.out.println("Invalid entry.");
            }

        }
    }

    /**
     * Prompts for an integer value (quantity)
     * @param prompt - ask user to enter quantity
     * @param scanner - scan the user input
     * @return a positive integer
     */
    public int addPositiveInt(String prompt, Scanner scanner) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                int next = scanner.nextInt(); //read an int value
                if (next > 0) { //check if it is positive
                    return next;
                } else { //input is a negative number
                    System.out.println("Invalid quantity.");
                }
            } else { //input is not a number
                System.out.println("Invalid quantity.");
            }
            scanner.nextLine(); //change to a new line
        }
    }
}
