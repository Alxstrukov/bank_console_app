package ru.clevertec.console.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.management.ConstructorParameters;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private Client client;
    private Bank bank;
    private BankAccount bankAccount;
}
