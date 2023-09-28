import java.util.Scanner;
/**
 * CET - CS Academic Level 3
 * Declaration: I declare that this is my own original work and is free from Plagiarism
 * This class is to display the main menu and prompt user to choose an option.
 * Assignment #2
 * Student Name: Yijia Xu
 * Student Number: 041061204
 * Modified Date: June 18, 2023
 * Section #: 301
 * Course: CST8130 - Data Structures
 * Professor: James Mwangi PhD.
 */
public class AssignTwoTest {
    /**
     * The main method
     * @param args - args
     */
    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        Scanner scanner = new Scanner(System.in);
        boolean isContinued = true; //set a boolean to control the while loop
        boolean isUpdated;

        while (isContinued) {
            switch (displayMenu()) {
                /* add item to inventory */
                case 1:
                    inventory.addItem(scanner, false);
                    break;
                /* print current inventory */
                case 2:
                    System.out.println("Inventory:");
                    System.out.println(inventory.toString());
                    break;
                /* buy items */
                case 3:
                    isUpdated = inventory.updateQuantity(scanner, true); //call method to add and update quantity
                    if (isUpdated == false)
                        System.out.println("Error...could not buy item");
                    break;
                /* sell items */
                case 4:
                    isUpdated = inventory.updateQuantity(scanner, false); //call method to add and update quantity
                    if (isUpdated == false)
                        System.out.println("Error...could not sell item");
                    break;
                /* search for item */
                case 5:
                    inventory.searchForItem(scanner);
                    break;
                /* save items to file */
                case 6:
                    inventory.saveToFile(scanner);
                    break;
                /* read items from file */
                case 7:
                    inventory.readFromFile(scanner);
                    break;
                /* to exit */
                case 8:
                    isContinued = false; //set isContinued false to stop the next loop
                    System.out.println("Exiting...");
            }
        }

    }

    /**
     * Displays the main menu to console and prompts user to choose an option.
     * @return the option number user chose
     */
    public static int displayMenu() {
        Scanner input = new Scanner(System.in);
        int option = -1; //initialize option value

        while (true){
            System.out.println("Please select one of the following:");
            System.out.println("1: Add Item to Inventory");
            System.out.println("2: Display Current Inventory");
            System.out.println("3: Buy Item(s)");
            System.out.println("4: Sell Item(s)");
            System.out.println("5: Search for Item");
            System.out.println("6: Save Inventory to File");
            System.out.println("7: Read Inventory from File");
            System.out.println("8: To Exit");

            if (input.hasNextInt()) {
                option = input.nextInt(); //read the next int
                if (option < 1 || option > 8) { //integer not in the range
                    System.out.println("...Invalid input, please try again...\n");
                } else
                    break; //jump out of the loop
            } else //the user input is not an integer
                System.out.println("...Invalid input, please try again...\n");

            input.nextLine(); //change to a new line and ready to read next input
        }
        return option;
    }
}