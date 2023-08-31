package ru.clevertec.console.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.clevertec.console.application.utils.SQLquery;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private Client client;
    private Bank bank = new Bank(SQLquery.CLEVER_BANK_ID, SQLquery.CLEVER_BANK_NAME);
    private ArrayList<BankAccount> bankAccounts = new ArrayList<>();
}
