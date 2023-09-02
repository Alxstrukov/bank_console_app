package ru.clevertec.console.application.services;

import ru.clevertec.console.application.model.BankAccount;

import java.math.BigDecimal;

public interface OperationManagable {
    boolean addBalanceByPercentage(int accountNumber, BigDecimal amount);

    boolean addMoney(BankAccount bankAccount, BigDecimal amount);

    boolean receiveMoney(BankAccount bankAccount, BigDecimal amount);

    boolean transferMoney(BankAccount bankAccount, int bankAccountNumber, int bankId, BigDecimal amount);
}
