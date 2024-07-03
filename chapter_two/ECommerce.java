package chapter_two;

import chapter_two.com.ecommerce.Product;
import chapter_two.com.ecommerce.Customer;
import chapter_two.com.ecommerce.orders.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ECommerce {
    private static List<Product> availableProducts = new ArrayList<>();
    private static List<Customer> customers = new ArrayList<>();
    private static int orderIdCounter = 1;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeProducts();

        while (true) {
            System.out.println("\n--- E-Commerce System ---");
            System.out.println("1. Create new user");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    createNewUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    System.out.println("Thank you for using our e-commerce system. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void initializeProducts() {
        availableProducts.add(new Product(1, "Laptop", 999.99));
        availableProducts.add(new Product(2, "Smartphone", 499.99));
        availableProducts.add(new Product(3, "Headphones", 79.99));
        availableProducts.add(new Product(4, "Tablet", 299.99));
    }

    private static void createNewUser() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        int customerId = customers.size() + 1;
        Customer newCustomer = new Customer(customerId, name);
        customers.add(newCustomer);
        System.out.println("User created successfully. Your customer ID is: " + customerId);
    }

    private static void loginUser() {
        System.out.print("Enter your customer ID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Customer customer = findCustomerById(customerId);
        if (customer == null) {
            System.out.println("Customer not found. Please try again.");
            return;
        }

        System.out.println("Welcome, " + customer.getName() + "!");
        showCustomerMenu(customer);
    }

    private static Customer findCustomerById(int customerId) {
        return customers.stream()
                .filter(c -> c.getCustomerID() == customerId)
                .findFirst()
                .orElse(null);
    }

    private static void showCustomerMenu(Customer customer) {
        while (true) {
            System.out.println("\n--- Customer Menu ---");
            System.out.println("1. Browse products");
            System.out.println("2. View cart");
            System.out.println("3. Place order");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    browseProducts(customer);
                    break;
                case 2:
                    viewCart(customer);
                    break;
                case 3:
                    placeOrder(customer);
                    break;
                case 4:
                    System.out.println("Logging out. Goodbye, " + customer.getName() + "!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void browseProducts(Customer customer) {
        System.out.println("\nAvailable products:");
        for (Product product : availableProducts) {
            System.out.println(product);
        }

        System.out.print("Enter the product ID to add to cart (or 0 to go back): ");
        int productId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (productId != 0) {
            Product selectedProduct = findProductById(productId);
            if (selectedProduct != null) {
                customer.addToCart(selectedProduct);
                System.out.println(selectedProduct.getName() + " added to your cart.");
            } else {
                System.out.println("Product not found. Please try again.");
            }
        }
    }

    private static Product findProductById(int productId) {
        return availableProducts.stream()
                .filter(p -> p.getProductID() == productId)
                .findFirst()
                .orElse(null);
    }

    private static void viewCart(Customer customer) {
        List<Product> cart = customer.getShoppingCart();
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("\nYour shopping cart:");
            for (Product product : cart) {
                System.out.println(product);
            }
            System.out.println("Total: $" + customer.calculateTotal());
        }
    }

    private static void placeOrder(Customer customer) {
        if (customer.getShoppingCart().isEmpty()) {
            System.out.println("Your cart is empty. Add some products before placing an order.");
            return;
        }

        Order order = new Order(orderIdCounter++, customer, customer.getShoppingCart());
        System.out.println("\nOrder placed successfully!");
        System.out.println(order.generateOrderSummary());

        // Clear the customer's cart after placing the order
        customer.clearCart();
    }
}