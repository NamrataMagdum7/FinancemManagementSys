package dao;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class FinanceRepositoryImplTest {
    @Test
    public void testLoginSuccess() {
        FinanceRepositoryImpl repo = new FinanceRepositoryImpl();
        int userId = repo.login("testuser", "testpass");
        assertTrue(userId > 0);
    }
}
