package org.example;

public class InvalidDepositAmountException extends Exception {
    public InvalidDepositAmountException(String message) {
        super(message);
    }
}