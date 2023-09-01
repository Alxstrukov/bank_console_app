package ru.clevertec.console.application.services;

import ru.clevertec.console.application.model.Bank;

public interface BankManagable {
    void createBank(String value);

    Bank readBank(int value);

    void updateBank(int id, String value);

    void deleteBank(int id);
}
