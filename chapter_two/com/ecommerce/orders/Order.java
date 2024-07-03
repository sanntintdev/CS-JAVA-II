package chapter_two.com.ecommerce.orders;

import java.util.List;

import chapter_two.com.ecommerce.Customer;
import chapter_two.com.ecommerce.Product;

public class Order {
    private int orderID;
    private Customer customer;
    private List<Product> products;
    private double total;
    private String status;

    public Order(int orderID, Customer customer, List<Product> products) {
        this.orderID = orderID;
        this.customer = customer;
        this.products = products;
        this.total = calculateTotal();
        this.status = "Placed";
    }

    private double calculateTotal() {
        return products.stream().mapToDouble(Product::getPrice).sum();
    }

    public String generateOrderSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("Order ID: ").append(orderID).append("\n");
        summary.append("Customer: ").append(customer.getName()).append("\n");
        summary.append("Products:\n");
        for (Product product : products) {
            summary.append("- ").append(product.getName()).append(" ($").append(product.getPrice()).append(")\n");
        }
        summary.append("Total: $").append(total).append("\n");
        summary.append("Status: ").append(status);
        return summary.toString();
    }

    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    // Getters
    public int getOrderID() {
        return orderID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public double getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }
}