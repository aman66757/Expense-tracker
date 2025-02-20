import java.io.*;
import java.util.*;

class Expense implements Serializable {  
    private String category;
    private double amount;

    public Expense(String category, double amount) {
        this.category = category;
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return category + ": $" + amount;
    }
}

public class ExpenseTracker {
    private static List<Expense> expenses = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        loadExpenses();

        while (true) {
            System.out.println("\n1. Add Expense\n2. View Expenses\n3. Save & Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    addExpense(scanner);
                    break;
                case 2:
                    viewExpenses();
                    break;
                case 3:
                    saveExpenses();
                    System.out.println("Expenses saved. Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void addExpense(Scanner scanner) {
        System.out.print("Enter category: ");
        String category = scanner.nextLine();
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); 

        expenses.add(new Expense(category, amount));
        System.out.println("Expense added!");
    }

    private static void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
            return;
        }

        System.out.println("\nExpenses:");
        for (Expense exp : expenses) {
            System.out.println(exp);
        }
    }

    private static void saveExpenses() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("expenses.ser"))) {
            out.writeObject(expenses);
        } catch (IOException e) {
            System.out.println("Error saving expenses.");
            e.printStackTrace();
        }
    }

    private static void loadExpenses() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("expenses.ser"))) {
            expenses = (List<Expense>) in.readObject();
            System.out.println("Expenses loaded successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("No previous expenses found.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading expenses.");
        }
    }
}