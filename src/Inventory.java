import java.io.*;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * CET - CS Academic Level 3
 * Declaration: I declare that this is my own original work and is free from Plagiarism
 * This class is to sort and search items in inventory and updates inventory according to user operations.
 * Assignment #2
 * Student Name: Yijia Xu
 * Student Number: 041061204
 * Modified Date: June 18, 2023
 * Section #: 301
 * Course: CST8130 - Data Structures
 * Professor: James Mwangi PhD.
 */
public class Inventory {
    /**
     * An array list of inventory to store all item information
     */
    private ArrayList<FoodItem> inventory;

    /**
     * Number of items in the array
     */
    private int numItems;

    /**
     * Default constructor. Initialize inventory arraylist.
     */
    public Inventory() {
        inventory = new ArrayList<FoodItem>();
    }

    /**
     * Check if the input item already exists in the inventory array
     * @param item - the input item
     * @return the index of a FoodItem in arraylist with the same itemCode, returns -1 if not found
     */
    public int alreadyExists(FoodItem item) {
        /* search for the same itemCode */
        for (int i = 0; i < numItems; i++) {
            if (inventory.get(i).isEqual(item))
                return i; // return the index of the existing item
        }
        return -1; //not found
    }

    /**
     * Adds an item to inventory arraylist
     * @param scanner - scan the next item type
     * @param fromFile - item added from file for true, from console input for false
     * @return true if successfully added, else return false
     */
    public boolean addItem(Scanner scanner, boolean fromFile) {
        FoodItem item = null; //initialize item

        if (!fromFile) { //read from console input
            boolean isContinued = true;
            while (isContinued) {
                System.out.print("Do you wish to add a fruit(f), vegetable(v), preserve(p)");
                String itemType = scanner.next();
                switch (itemType) {
                    case "f":
                        item = new Fruit(); //create a new Fruit type object
                        isContinued = false; //set false to jump out of the next loop
                        break;
                    case "v":
                        item = new Vegetable(); //create a new Vegetable type object
                        isContinued = false;
                        break;
                    case "p":
                        item = new Preserve(); //create a new Preserve type object
                        isContinued = false;
                        break;
                    default: //input other than 'f','v','p'
                        System.out.println("Invalid entry.");
                }
            }
            /*prompt user to input code, and check if it already exists*/
            if (item.inputCode(scanner, false)) {
                int newIndex = alreadyExists(item);
                    if (newIndex != -1) { // no duplicate
                        System.out.println("Item code already exists.");
                        return false;
                    }

                    if (item.addItem(scanner, false)){//check if the item is successfully added
                        newIndex = alreadyExists(item);
                        insertItem(item);
                        numItems ++;
                    }
            }
            return true;
        }
        else { //read inventory from file
            readFromFile(scanner);
        }

        return true;
    }

    /**
     * Update (buy or sell) quantity of items in the inventory
     * @param scanner - scan user input
     * @param buyOrSell - true for buying, false for selling
     * @return ture if quantity updated successfully, false if failed
     */
    public boolean updateQuantity(Scanner scanner, boolean buyOrSell) {
        int changedQuant;
        if (numItems == 0) //no items in the inventory
            return false;
        FoodItem foodItem = new FoodItem();
        foodItem.inputCode(scanner, false);

        /*search for item code from inventory, return false if cannot find */
        int itemIndex = alreadyExists(foodItem);
        if (itemIndex == -1) {
            System.out.println("Code not found in inventory...");
            return false;
        }

        /*prompt user to enter quantity to buy, add them to inventory*/
        if (buyOrSell) { //buy items
            changedQuant = foodItem.addPositiveInt("Enter valid quantity to buy: ", scanner);
            inventory.get(itemIndex).updateItem(changedQuant);
        } else { //sell items
            /*prompt user to enter quantity to sell, update the inventory*/
            changedQuant = foodItem.addPositiveInt("Enter valid quantity to sell: ", scanner);
            changedQuant *= -1; //subtract the sold quantity

            /*check if it has enough stock to sell*/
            if (inventory.get(itemIndex).updateItem(changedQuant)) {
                return true;
            } else {
                System.out.println("Insufficient stock in inventory...");
                return false;
            }
        }
        return true;
    }

    /**
     * Inserts a FoodItem into the inventory in an ascending sorted order
     * @param item - the food item
     */
    private void insertItem(FoodItem item) {
        /* iterate the arraylist, and insert the item in the correct location */
        for (int i = 0; i < numItems; i++) {
            FoodItem comparedItem = inventory.get(i);
            if (item.compareTo(comparedItem) == 0) //item already exists in the arraylist
                return;

            if (item.compareTo(comparedItem) > 0) // item greater than compared item, move forward
                continue;

            inventory.add(i, item); //item greater than the last one, smaller than currently compared one
            return;
        }

        inventory.add(item); //item greater than the greatest one, put it at last
    }

    /**
     * Searchs for a food item in the inventory arraylist.
     * @param scanner - scan for item code
     */
    public void searchForItem(Scanner scanner) {
        int itemCode = 0;
        /* keeps reading item code until user inputs an integer */
        while (true) {
            System.out.print("Enter the code for the item: ");
            if (scanner.hasNextInt()) {
                itemCode = scanner.nextInt();
                break;
            }
            else {
                System.out.println("Invalid value.");
                scanner.nextLine();
            }
        }

        int low = 0;
        int high = numItems - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            FoodItem item = inventory.get(mid); //get the middle item in inventory

            if (itemCode == item.getItemCode()) { //find the target item code
                System.out.println(item.toString());
                return;
            } else if (itemCode > item.getItemCode()) { //item code higher than middle
                low = mid + 1; //change the lower bound for next searching
            } else { //item code lower than middle
                high = mid - 1; //change the higher bound for next searching
            }
        }
        System.out.println("Code not found in inventory...");
    }

    /**
     * Saves inventory to file.
     * @param scanner - scan for filename
     */
    public void saveToFile(Scanner scanner) {
        System.out.print("Enter the filename to save to: ");
        String filename = scanner.next();

        /* write items in inventory into the target file */
        try (Formatter writer = new Formatter(filename)) {
            for (int i = 0; i < inventory.size(); i++){
                inventory.get(i).outputItem(writer);
            }
        } catch (IOException e) {
            System.out.println("Writing to file failed.");
        }
    }

    /**
     * Reads item information from a file and save it in the inventory
     * @param scanner - scan for filename and item info
     */
    public void readFromFile(Scanner scanner) {
        FoodItem item = null; // initialize item
        System.out.print("Enter the filename to read from: ");
        String filename = scanner.next();

        try {
            /*read from the file*/
            File file = new File(filename);
            scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                char type = scanner.nextLine().charAt(0);
                int code = Integer.parseInt(scanner.nextLine());
                String name = scanner.nextLine();
                int quantity = Integer.parseInt(scanner.nextLine());
                if (quantity <= 0)
                    throw new InputMismatchException(); //check if quantity positive
                float cost = Float.parseFloat(scanner.nextLine());
                if (cost <= 0)
                    throw new InputMismatchException(); //check if cost positive
                float price = Float.parseFloat(scanner.nextLine());
                if (price <= 0)
                    throw new InputMismatchException(); //check if price positive

                switch (type) {
                    case 'f': //Fruit type
                        String orchardName = scanner.nextLine();
                        /*create a fruit type object*/
                        item = new Fruit(code, name, quantity, cost, price, orchardName);
                        break;
                    case 'p': //Preserve type
                        int jarSize = Integer.parseInt(scanner.nextLine());
                        if (jarSize < 0) {
                            throw new NumberFormatException(); //check if jarSize is positive
                        } else //create a preserve type object
                            item = new Preserve(code, name, quantity, cost, price, jarSize);
                        break;
                    case 'v': //Vegetable type
                        String farmName = scanner.nextLine();
                        /*create a vegetable type object*/
                        item = new Vegetable(code, name, quantity, cost, price, farmName);
                        break;
                    default: //other than 'f', 'p', 'v', invalid
                        throw new InputMismatchException();
                }

                if (alreadyExists(item) == -1) { //no duplicate items
                    insertItem(item);
                    numItems++;
                } else {
                    System.out.println("Item code already exists.");
                    throw new InputMismatchException();
                }
            }
            scanner.close(); //close input stream

        } catch (NumberFormatException | InputMismatchException e) {
            System.out.println("Error Encountered while reading the file, aborting...");
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found, ignoring...");
        }
    }

    /**
     * A string to store all item information in the inventory
     * @return - a string stores all items in inventory
     */
    @Override
    public String toString() {
        String str = ""; //initialize a string
        /*store each item in inventory into the string*/
        for (int i = 0; i < numItems; i++)
            str += inventory.get(i).toString() + "\n";

        return str;
    }
}
