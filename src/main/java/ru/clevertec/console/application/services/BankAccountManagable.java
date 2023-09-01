package ru.clevertec.console.application.services;

import ru.clevertec.console.application.model.BankAccount;

public interface BankAccountManagable {
    void createBankAccount(int bankId, int clientId);

    BankAccount readBankAccount(int bankAccountNumber);

    default void updateBankAccount(int oldBankAccountId,BankAccount newBankAccount) {
    }

    void deleteBankAccount(int bankAccountNumber);


}
