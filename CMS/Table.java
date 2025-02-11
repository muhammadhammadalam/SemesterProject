package CMS;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private final int tableNumber;
    private boolean occupied;
    private List<Order> orders;

    public Table(int tableNumber) {
        this.tableNumber = tableNumber;
        this.occupied = false;
        this.orders = new ArrayList<>();
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public boolean isOccupied() {
        return occupied;
    }

    private void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public void addOrder(String itemName, double price, int quantity, String customerName) {
        if (itemName == null || itemName.trim().isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be null or empty.");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0.");
        }
        if (customerName == null || customerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be null or empty.");
        }
        if (!this.isOccupied()) {
            this.setOccupied(true);
        }
        Order newOrder = new Order(itemName, price, quantity, customerName);
        orders.add(newOrder);
    }

    public List<Order> getOrders() {
        return new ArrayList<>(orders); // Return a copy of the list
    }

    public void clearOrders(boolean markUnoccupied) {
        orders.clear();
        if (markUnoccupied) {
            this.setOccupied(false);
        }
    }

    public boolean hasOrders() {
        return !orders.isEmpty();
    }
}