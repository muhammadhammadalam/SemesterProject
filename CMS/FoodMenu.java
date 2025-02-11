package CMS;

import java.util.HashMap;
import java.util.Map;

public class FoodMenu {
    private Map<String, Double> menuItems;

    public FoodMenu() {
        menuItems = new HashMap<>();
        // Example menu items (can be updated by admin)
        menuItems.put("Burger", 5.0);
        menuItems.put("Pizza", 8.0);
        menuItems.put("Pasta", 7.0);
        menuItems.put("Coke", 1.5);
    }

    public void addItem(String itemName, double price) {
        if (menuItems.containsKey(itemName)) {
            System.out.println(itemName + " already exists in the menu.");
            return;
        }
        if (price <= 0) {
            System.out.println("Price must be greater than 0.");
            return;
        }
        menuItems.put(itemName, price);
        System.out.println(itemName + " added to the menu.");
    }

    public void removeItem(String itemName) {
        if (!menuItems.containsKey(itemName)) {
            System.out.println(itemName + " does not exist in the menu.");
            return;
        }
        menuItems.remove(itemName);
        System.out.println(itemName + " removed from the menu.");
    }

    public void updatePrice(String itemName, double newPrice) {
        if (!menuItems.containsKey(itemName)) {
            System.out.println(itemName + " does not exist in the menu.");
            return;
        }
        if (newPrice <= 0) {
            System.out.println("Price must be greater than 0.");
            return;
        }
        menuItems.put(itemName, newPrice);
        System.out.println(itemName + " price updated.");
    }

    public void displayMenu() {
        System.out.println("\nFood Menu:");
        int index = 1;
        for (Map.Entry<String, Double> entry : menuItems.entrySet()) {
            System.out.println(index + ". " + entry.getKey() + ": $" + entry.getValue());
            index++;
        }
    }

    public String getItemByNumber(int number) {
        if (number < 1 || number > menuItems.size()) {
            throw new IllegalArgumentException("Invalid item number: " + number);
        }
        int index = 1;
        for (String item : menuItems.keySet()) {
            if (index == number) {
                return item;
            }
            index++;
        }
        return null;
    }

    public double getPrice(String itemName) {
        return menuItems.getOrDefault(itemName, 0.0);
    }
}