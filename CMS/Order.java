package CMS;

public class Order {
    private final String itemName;
    private final double price;
    private final int quantity;
    private final String customerName;

    public Order(String itemName, double price, int quantity, String customerName) {
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
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
        this.customerName = customerName;
    }

    // Getters
    public String getItemName() {
        return itemName;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCustomerName() {
        return customerName;
    }

    // Calculate total price
    public double getTotalPrice() {
        return price * quantity;
    }

    // Calculate loyalty points (example: 10% of total price)
    public double calculateLoyaltyPoints() {
        return getTotalPrice() * 0.1;
    }

    @Override
    public String toString() {
        return itemName + " x " + quantity + " @ $" + price + " = $" + getTotalPrice();
    }
}