package dao;

import entity.Expense;
import entity.User;
import myexception.ExpenseNotFoundException;
import myexception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.FinanceServiceImpl;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FinanceSystemTest {

    private FinanceServiceImpl financeService;

    @BeforeEach
    void setUp() {
        financeService = new FinanceServiceImpl();
    }

   




    @Test
    void testInvalidLoginThrowsException() {
        assertThrows(UserNotFoundException.class, () -> {
            financeService.login("wronguser", "wrongpass");
        });
    }

    @Test
    void testViewExpenseThrowsWhenNotFound() {
        assertThrows(ExpenseNotFoundException.class, () -> {
            financeService.viewExpenses(9999); // Replace with non-existent user ID
        });
    }
}