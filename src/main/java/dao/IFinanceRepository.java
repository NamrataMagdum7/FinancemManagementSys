package dao;

import entity.Expense;
import myexception.UserNotFoundException;
import myexception.ExpenseNotFoundException;

import java.util.List;

public interface IFinanceRepository {
    int login(String username, String password) throws UserNotFoundException;

    void createExpense(int userId, double amount, int categoryId, String date, String description);

    void createExpense(Expense expense);


    List<Expense> getExpensesByUserId(int userId) throws ExpenseNotFoundException;
    void updateExpense(Expense expense) throws ExpenseNotFoundException;
    void deleteExpense(int expenseId, int userId) throws ExpenseNotFoundException;


    void viewCategories();

    void generateReport(int userId);
}