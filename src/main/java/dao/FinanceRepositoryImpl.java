package dao;

import util.DBConnUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import entity.Expense;
import myexception.UserNotFoundException;
import myexception.ExpenseNotFoundException;

public class FinanceRepositoryImpl implements IFinanceRepository {

    @Override
    public int login(String username, String password) throws UserNotFoundException {
        try (Connection conn = DBConnUtil.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT user_id FROM users WHERE username = ? AND password = ?"
            );
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("user_id");
            else throw new UserNotFoundException("Invalid username or password.");
        } catch (Exception e) {
            System.err.println("Login error: " + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void createExpense(int userId, double amount, int categoryId, String date, String description) {
        try (Connection conn = DBConnUtil.getConnection()) {
            String sql = "INSERT INTO Expenses (user_id, amount, category_id, date, description) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setDouble(2, amount);
            ps.setInt(3, categoryId);
            ps.setDate(4, Date.valueOf(date));
            ps.setString(5, description);
            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Expense added successfully.");
            } else {
                System.out.println("Failed to add expense.");
            }
        } catch (Exception e) {
            System.out.println("Error while inserting expense: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void createExpense(Expense expense) {
        createExpense(
                expense.getUserId(),
                expense.getAmount(),
                expense.getCategoryId(),
                expense.getDate().toString(),
                expense.getDescription()
        );
    }

    @Override
    public List<Expense> getExpensesByUserId(int userId) throws ExpenseNotFoundException {
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT * FROM expenses WHERE user_id = ?";

        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Expense exp = new Expense();
                exp.setExpenseId(rs.getInt("expense_id"));
                exp.setUserId(rs.getInt("user_id"));
                exp.setCategoryId(rs.getInt("category_id"));
                exp.setAmount(rs.getDouble("amount"));
                exp.setDate(rs.getDate("date"));
                exp.setDescription(rs.getString("description"));
                expenses.add(exp);
            }

            if (expenses.isEmpty()) {
                throw new ExpenseNotFoundException("No expenses found for user ID: " + userId);
            }

        } catch (SQLException e) {
            throw new ExpenseNotFoundException("Error retrieving expenses: " + e.getMessage());
        }

        return expenses;
    }

    @Override
    public void updateExpense(Expense expense) throws ExpenseNotFoundException {
        try (Connection conn = DBConnUtil.getConnection()) {
            String sql = "UPDATE expenses SET amount = ?,  date = ?, description = ? WHERE category_id = ? AND user_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, expense.getAmount());
            ps.setInt(2, expense.getCategoryId());
            ps.setDate(3, expense.getDate());
            ps.setString(4, expense.getDescription());
            //ps.setInt(5, expense.getExpenseId());
            ps.setInt(6, expense.getUserId());

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                throw new ExpenseNotFoundException("Expense not found or not updated.");
            }
        } catch (ExpenseNotFoundException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteExpense(int expenseId, int userId) throws ExpenseNotFoundException {
        try (Connection conn = DBConnUtil.getConnection()) {
            String sql = "DELETE FROM expenses WHERE expense_id = ? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, expenseId);
            //ps.setInt(2, userId);
            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted == 0) {
                throw new ExpenseNotFoundException("Expense not found or not deleted.");
            }
        } catch (ExpenseNotFoundException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void viewCategories() {
        try (Connection conn = DBConnUtil.getConnection()) {
            String sql = "SELECT * FROM expensecategories";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("Category ID: " + rs.getInt("category_id") + " | Name: " + rs.getString("category_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void generateReport(int userId) {
        try (Connection conn = DBConnUtil.getConnection()) {
            String sql = "SELECT category_id, SUM(amount) as total FROM expenses WHERE user_id = ? GROUP BY category_id";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("Category ID: " + rs.getInt("category_id") + " | Total Spent: " + rs.getDouble("total"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
