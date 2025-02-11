package CMS;

import java.util.*;

public class CanteenManagementSystem {
    private Scanner scanner;
    private FoodMenu menu;
    private List<Table> tables;
    private Customer customerManager;
    private final String ADMIN_USERNAME = "admin";
    private final String ADMIN_PASSWORD = "admin123";
    private String loggedInUser = null;
    private boolean isAdminLoggedIn = false;

    public CanteenManagementSystem() {
        this.scanner = new Scanner(System.in);
        this.menu = new FoodMenu();
        this.tables = new ArrayList<>();
        this.customerManager = new Customer();

        // Initialize tables
        for (int i = 1; i <= 20; i++) {
            this.tables.add(new Table(i));
        }
    }

    public void showLoginMenu() {
        while (loggedInUser == null && !isAdminLoggedIn) {
            System.out.println("\nCanteen Management System - Login");
            System.out.println("1. Customer Login");
            System.out.println("2. Admin Login");
            System.out.println("3. Register as Customer");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = getIntInput();

            switch (choice) {
                case 1:
                    customerLogin();
                    break;
                case 2:
                    adminLogin();
                    break;
                case 3:
                    registerCustomer();
                    break;
                case 4:
                    System.out.println("Exiting system...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }

    public void registerCustomer() {
        System.out.print("Enter a username: ");
        String username = scanner.next();
        System.out.print("Enter a password: ");
        String password = scanner.next();

        if (customerManager.addCustomer(username, password)) {
            System.out.println("Registration successful! You can now log in.");
        } else {
            System.out.println("Username already exists. Try again.");
        }
    }

    public void customerLogin() {
        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();

        if (customerManager.verifyCredentials(username, password)) {
            System.out.println("Welcome back, " + username + "!");
            loggedInUser = username;
        } else {
            System.out.println("Invalid username or password. Try again.");
        }
    }

    public void adminLogin() {
        System.out.print("Enter admin username: ");
        String username = scanner.next();
        System.out.print("Enter admin password: ");
        String password = scanner.next();

        if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
            System.out.println("Admin login successful!");
            isAdminLoggedIn = true;
        } else {
            System.out.println("Incorrect admin credentials. Access denied.");
        }
    }

    public void displayTables() {
        System.out.println("\nTables Status:");
        for (Table table : tables) {
            String status = table.isOccupied() ? "Occupied" : "Available";
            System.out.println("Table " + table.getTableNumber() + ": " + status);
        }
    }

    public void placeOrder() {
        displayTables();
        System.out.print("\nEnter Table Number to place order: ");
        int tableNumber = getIntInput();
        Table table = findTableByNumber(tableNumber);

        if (table == null) {
            System.out.println("Invalid table number!");
            return;
        }

        menu.displayMenu();
        System.out.print("\nEnter item number to order: ");
        int itemNumber = getIntInput();
        String orderItem = menu.getItemByNumber(itemNumber);

        if (orderItem != null) {
            double price = menu.getPrice(orderItem);
            System.out.print("Enter quantity: ");
            int quantity = getIntInput();

            table.addOrder(orderItem, price, quantity, loggedInUser);
            System.out.println("Added " + quantity + "x " + orderItem + " to Table " + tableNumber);

            // Update loyalty points
            double loyaltyPoints = price * quantity * 0.1; // 10% of total price
            customerManager.updateLoyaltyPoints(loggedInUser, loyaltyPoints);
            System.out.println("Earned " + loyaltyPoints + " loyalty points!");
        } else {
            System.out.println("Invalid item selection!");
        }
    }

    public void generateReceipt() {
        System.out.print("Enter Table Number to generate receipt: ");
        int tableNumber = getIntInput();
        Table table = findTableByNumber(tableNumber);

        if (table == null || !table.isOccupied()) {
            System.out.println("Table " + tableNumber + " is not occupied.");
            return;
        }

        List<Order> orders = table.getOrders();
        double total = 0;
        System.out.println("\nReceipt for Table " + tableNumber + ":");
        for (Order order : orders) {
            System.out.println(order);
            total += order.getTotalPrice();
        }
        System.out.println("Total: $" + total);
        table.clearOrders(true); // Clear orders and mark table as unoccupied
    }

    public void viewLoyaltyPoints() {
        if (loggedInUser == null) {
            System.out.println("No customer logged in.");
            return;
        }
        double points = customerManager.getLoyaltyPoints(loggedInUser);
        System.out.println("Loyalty Points for " + loggedInUser + ": " + points);
    }

    public void viewOrderHistory() {
        if (loggedInUser == null) {
            System.out.println("No customer logged in.");
            return;
        }
        System.out.println("\nOrder History for " + loggedInUser + ":");
        for (Table table : tables) {
            List<Order> orders = table.getOrders();
            for (Order order : orders) {
                if (order.getCustomerName().equals(loggedInUser)) {
                    System.out.println("Table " + table.getTableNumber() + ": " + order);
                }
            }
        }
    }

    public void adminMenu() {
        while (isAdminLoggedIn) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Add Menu Item");
            System.out.println("2. Remove Menu Item");
            System.out.println("3. Update Menu Item Price");
            System.out.println("4. View All Orders");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            int choice = getIntInput();

            switch (choice) {
                case 1:
                    System.out.print("Enter item name: ");
                    String itemName = scanner.next();
                    System.out.print("Enter item price: ");
                    double price = getDoubleInput();
                    menu.addItem(itemName, price);
                    break;
                case 2:
                    System.out.print("Enter item name to remove: ");
                    itemName = scanner.next();
                    menu.removeItem(itemName);
                    break;
                case 3:
                    System.out.print("Enter item name to update: ");
                    itemName = scanner.next();
                    System.out.print("Enter new price: ");
                    price = getDoubleInput();
                    menu.updatePrice(itemName, price);
                    break;
                case 4:
                    viewAllOrders();
                    break;
                case 5:
                    isAdminLoggedIn = false;
                    System.out.println("Admin logged out.");
                    break;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }

    public void viewAllOrders() {
        System.out.println("\nAll Orders:");
        for (Table table : tables) {
            if (table.isOccupied()) {
                System.out.println("Table " + table.getTableNumber() + ":");
                for (Order order : table.getOrders()) {
                    System.out.println("  " + order);
                }
            }
        }
    }

    public Table findTableByNumber(int tableNumber) {
        return tables.stream().filter(t -> t.getTableNumber() == tableNumber).findFirst().orElse(null);
    }

    public int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next();
        }
        return scanner.nextInt();
    }

    public double getDoubleInput() {
        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next();
        }
        return scanner.nextDouble();
    }

    public void start() {
        showLoginMenu();
        while (true) {
            if (isAdminLoggedIn) {
                adminMenu();
            } else {
                System.out.println("\nMain Menu:");
                System.out.println("1. Display Tables");
                System.out.println("2. Place Order");
                System.out.println("3. Generate Receipt");
                System.out.println("4. View Loyalty Points");
                System.out.println("5. View Order History");
                System.out.println("6. Logout");
                System.out.print("Enter your choice: ");
                int choice = getIntInput();

                switch (choice) {
                    case 1:
                        displayTables();
                        break;
                    case 2:
                        placeOrder();
                        break;
                    case 3:
                        generateReceipt();
                        break;
                    case 4:
                        viewLoyaltyPoints();
                        break;
                    case 5:
                        viewOrderHistory();
                        break;
                    case 6:
                        loggedInUser = null;
                        System.out.println("Logged out successfully.");
                        showLoginMenu();
                        break;
                    default:
                        System.out.println("Invalid choice! Try again.");
                }
            }
        }
    }

    public static void main(String[] args) {
        CanteenManagementSystem system = new CanteenManagementSystem();
        system.start();
    }
}