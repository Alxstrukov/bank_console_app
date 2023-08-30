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

    public String showInfoBalance() {
        System.out.println("           List bank accounts           ");
        final StringBuilder sb = new StringBuilder("BankAccount{");
        sb.append("accountNumber=").append(accountNumber);
        sb.append(", balance=").append(balance);
        sb.append(" BYN");
        sb.append(", creationDate=").append(creationDate);
        sb.append('}');
        return sb.toString();
    }

    public void addBalance(BigDecimal amount) {
        balance = balance.add(amount);
    }

    public void receiveBalance(BigDecimal amount) {
        balance = balance.subtract(amount);
    }
}
