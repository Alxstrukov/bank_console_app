package ru.clevertec.console.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private Client client;
    private Bank bank = new Bank(119, "Clever-Bank");
    private ArrayList<BankAccount> bankAccounts = new ArrayList<>();
}
