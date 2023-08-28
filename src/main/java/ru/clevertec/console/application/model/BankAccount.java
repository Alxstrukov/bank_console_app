package ru.clevertec.console.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


@AllArgsConstructor
@Data
public class BankAccount {
    private int accountNumber;
    private BigDecimal balance;
    private Bank bank;
    private Client client;
    private Date creationDate;


}
