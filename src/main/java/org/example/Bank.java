package org.example;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Bank {
    private static final Logger logger = LogManager.getLogger(Bank.class);

    public static void main(String[] args) {
        logger.info("Starting Bank application");

        // Create a customer
        Customer customer = new Customer("John Doe", "C001");
        logger.info("Created customer: " + customer);

        // Create an account for the customer
        Account account = new Account("A001", 1000.0, customer);
        logger.info("Created account: " + account);

        logger.info("Final account state: " + account);
    }
}