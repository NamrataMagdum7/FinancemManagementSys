package app;

import dao.FinanceRepositoryImpl;
import entity.Expense;
import myexception.ExpenseNotFoundException;
import myexception.UserNotFoundException;
import service.FinanceServiceImpl;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class FinanceApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        FinanceRepositoryImpl repo = new FinanceRepositoryImpl();
        FinanceServiceImpl financeServiceservice=new FinanceServiceImpl();
        int userId = -1;

        // Login block
        while (userId == -1) {
            System.out.print("Username: ");
            String uname = sc.nextLine();
            System.out.print("Password: ");
            String pass = sc.nextLine();

            try {
                userId = repo.login(uname, pass);
            } catch (UserNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }

        // Menu
        System.out.println("\nWelcome to Finance Management System!");
        while (true) {
            System.out.println("\n------ Menu ------");
            System.out.println("1. Add Expense");
            System.out.println("2. View My Expenses");
            System.out.println("3. Update Expense");
            System.out.println("4. Delete Expense");
            System.out.println("5. Add Expense Category (Not implemented)");
            System.out.println("6. View Categories");
            System.out.println("7. Generate Expense Report");
            System.out.println("8. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            try {
                switch (choice) {
                    case 1: {
                        System.out.print("Amount: ");
                        double amt = sc.nextDouble();
                        System.out.print("Category ID: ");
                        int catId = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Date (yyyy-mm-dd): ");
                        String date = sc.nextLine();
                        System.out.print("Description: ");
                        String desc = sc.nextLine();
                        repo.createExpense(userId, amt, catId, date, desc);
                        break;
                    }

                    case 2:
                        try {
                            List<Expense> expenses = financeServiceservice.viewExpenses(userId);
                            for (Expense expense : expenses) {
                                System.out.println("Expense ID: " + expense.getExpenseId() +
                                        " | Amount: " + expense.getAmount() +
                                        " | Category ID: " + expense.getCategoryId() +
                                        " | Date: " + expense.getDate() +
                                        " | Description: " + expense.getDescription());
                            }
                        } catch (ExpenseNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 3: {
                        System.out.print("Enter user ID to update: ");
                        int expenseId = sc.nextInt();
                        System.out.print("New Amount: ");
                        double amount = sc.nextDouble();
                        System.out.print("New Category ID: ");
                        int categoryId = sc.nextInt();
                        sc.nextLine();
                        System.out.print("New Date (yyyy-mm-dd): ");
                        String date = sc.nextLine();
                        System.out.print("New Description: ");
                        String description = sc.nextLine();

                        Expense expense = new Expense(expenseId, userId, amount, categoryId, Date.valueOf(date), description);
                        repo.updateExpense(expense);
                        break;
                    }

                    case 4: {
                        System.out.print("Enter Expense ID to delete: ");
                        int delId = sc.nextInt();
                        repo.deleteExpense(delId, userId); // FIXED: Pass userId
                        break;
                    }

                    case 5: {
                        System.out.println("Add Category feature not implemented yet.");
                        // Uncomment and implement if needed in repository:
                        // System.out.print("Enter new category name: ");
                        // String catName = sc.nextLine();
                        // repo.addCategory(catName);
                        break;
                    }

                    case 6:
                        repo.viewCategories();
                        break;

                    case 7: {
                        System.out.println("Generating overall category-wise report...");
                        repo.generateReport(userId);
                        break;
                    }

                    case 8: {
                        System.out.println("Goodbye!");
                        return;
                    }

                    default:
                        System.out.println("Invalid choice.");
                        break;
                }
            } catch (ExpenseNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}