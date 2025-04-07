package service;

import entity.Expense;
import java.util.List;

public interface IFinanceService {
    int login(String username, String password) throws myexception.UserNotFoundException;
    void createExpense(int userId, double amount, int categoryId, String date, String description);
    List<Expense> viewExpenses(int userId)throws myexception.ExpenseNotFoundException;;
}
