package chapter_six;

import java.util.Scanner;

public class LibraryCatalogSystem {
    private static Catalog<LibraryItem<String>> catalog = new Catalog<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    addItem();
                    break;
                case 2:
                    removeItem();
                    break;
                case 3:
                    catalog.displayCatalog();
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        System.out.println("Thank you for using the Library Catalog System.");
    }

    private static void displayMenu() {
        System.out.println("\nLibrary Catalog System");
        System.out.println("1. Add a new item");
        System.out.println("2. Remove an item");
        System.out.println("3. View catalog");
        System.out.println("4. Exit");
    }

    private static void addItem() {
        String title = getStringInput("Enter book title: ");
        String author = getStringInput("Enter book author: ");
        String itemID = getStringInput("Enter book ID: ");
        LibraryItem<String> item = new LibraryItem<>(title, author, itemID);
        catalog.addItem(item);
    }

    private static void removeItem() {
        if (catalog.getSize() == 0) {
            System.out.println("The catalog is empty. No items to remove.");
            return;
        }
        catalog.displayCatalog();
        int index = getIntInput("Enter the number of the item to remove: ") - 1;
        try {
            LibraryItem<String> item = catalog.getItem(index);
            catalog.removeItem(item);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid item number. Please try again.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
}
