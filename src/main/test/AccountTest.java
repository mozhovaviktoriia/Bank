package org.example;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountTest {

    private Account account;
    private InterestRateService interestRateService;

    @BeforeEach
    void setUp() {
        interestRateService = Mockito.mock(InterestRateService.class);
        Customer customer = new Customer("John Doe", "001");
        account = new Account("A001", 1000.0, customer, interestRateService);
    }

    @Test
    void testDepositValidAmount() throws InvalidDepositAmountException {
        account.deposit(200.0);
        assertEquals(1200.0, account.getBalance(), "Balance should be 1200 after depositing 200.");
    }

    @Test
    void testDepositInvalidAmount() {
        Exception exception = assertThrows(InvalidDepositAmountException.class, () -> account.deposit(-100.0));
        assertEquals("Deposit amount must be greater than zero.", exception.getMessage());
    }

    @Test
    void testWithdrawValidAmount() throws InsufficientFundsException, InvalidDepositAmountException {
        account.withdraw(500.0);
        assertEquals(500.0, account.getBalance(), "Balance should be 500 after withdrawing 500.");
    }

    @Test
    void testWithdrawInvalidAmount() {
        Exception exception = assertThrows(InvalidDepositAmountException.class, () -> account.withdraw(-100.0));
        assertEquals("Withdrawal amount must be greater than zero.", exception.getMessage());
    }

    @Test
    void testWithdrawInsufficientFunds() {
        Exception exception = assertThrows(InsufficientFundsException.class, () -> account.withdraw(1500.0));
        assertEquals("Insufficient balance for this withdrawal.", exception.getMessage());
    }

@Test
void testCalculateInterestWithMockedService() {
    // Налаштування поведінки мок-об'єкта
    when(interestRateService.getRate(1500.0, 12)).thenReturn(2.5);

    // Виклик методу
    double interestRate = account.calculateInterestRate(1500.0, 12);

    // Перевірка результату
    assertEquals(2.5, interestRate, "Interest rate should match mocked value.");

    // Перевірка, що метод getRate був викликаний 1 раз
    verify(interestRateService, times(1)).getRate(1500.0, 12);
}
    @Test
    void testCalculateInterestRateShortTermSmallAmount() {
        double interestRate = account.calculateInterestRate(500.0, 3);  // Сума < 1000 і термін < 6 місяців
        assertEquals(1.0, interestRate, "Interest rate should be 1.0% for small short-term deposit.");
    }

    @Test
    void testCalculateInterestRateShortTermLargeAmount() {
        double interestRate = account.calculateInterestRate(1500.0, 3);  // Сума > 1000 і термін < 6 місяців
        assertEquals(1.5, interestRate, "Interest rate should be 1.5% for large short-term deposit.");
    }

    @Test
    void testCalculateInterestRateMediumTermSmallAmount() {
        double interestRate = account.calculateInterestRate(500.0, 9);  // Сума < 1000 і термін <= 12 місяців
        assertEquals(1.8, interestRate, "Interest rate should be 1.8% for small medium-term deposit.");
    }

    @Test
    void testCalculateInterestRateMediumTermLargeAmount() {
        double interestRate = account.calculateInterestRate(1500.0, 9);  // Сума > 1000 і термін <= 12 місяців
        assertEquals(2.5, interestRate, "Interest rate should be 2.5% for large medium-term deposit.");
    }

    @Test
    void testCalculateInterestRateLongTermSmallAmount() {
        double interestRate = account.calculateInterestRate(500.0, 15);  // Сума < 1000 і термін > 12 місяців
        assertEquals(2.2, interestRate, "Interest rate should be 2.2% for small long-term deposit.");
    }

    @Test
    void testCalculateInterestRateLongTermLargeAmount() {
        double interestRate = account.calculateInterestRate(1500.0, 15);  // Сума > 1000 і термін > 12 місяців
        assertEquals(3.0, interestRate, "Interest rate should be 3.0% for large long-term deposit.");
    }
}