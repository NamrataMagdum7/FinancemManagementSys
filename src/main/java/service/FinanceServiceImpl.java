package service;

import dao.FinanceRepositoryImpl;
import entity.Expense;
import myexception.UserNotFoundException;
import myexception.ExpenseNotFoundException;

import java.util.List;

public class FinanceServiceImpl implements IFinanceService {

    private final FinanceRepositoryImpl repo = new FinanceRepositoryImpl();

    @Override
    public int login(String username, String password) throws UserNotFoundException {
        return repo.login(username, password);
    }

    @Override
    public void createExpense(int userId, double amount, int categoryId, String date, String description) {
        repo.createExpense(userId, amount, categoryId, date, description);
    }

    @Override
    public List<Expense> viewExpenses(int userId) throws ExpenseNotFoundException{
       return repo.getExpensesByUserId(userId);  // We'll need to make this DAO method return List<Expense>
    }
}
