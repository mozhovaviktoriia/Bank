package org.example;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class Account {
    private static final Logger logger = LogManager.getLogger(Account.class);
    private String accountId;
    private double balance;
    private Customer customer;

    public Account(String accountId, double balance, Customer customer) {
        this.accountId = accountId;
        this.balance = balance;
        this.customer = customer;
    }

    public String getAccountId() {
        return accountId;
    }

    public double getBalance() {
        return balance;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            logger.info("Deposited " + amount + " to account " + accountId);
        } else {
            logger.warn("Attempted to deposit a non-positive amount");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            logger.info("Withdrew " + amount + " from account " + accountId);
        } else {
            logger.warn("Attempted to withdraw an invalid amount");
        }
    }

    @Override
    public String toString() {
        logger.info("Logging from Account class");
        return "Account [accountId=" + accountId + ", balance=" + balance + ", customer=" + customer + "]";
    }
}
