package ru.clevertec.console.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
        final StringBuilder sb = new StringBuilder("Bank account number:  ");
        sb.append(accountNumber);
        sb.append("   ");
        sb.append("     Balance=").append(balance.setScale(2, RoundingMode.HALF_EVEN));
        sb.append(" BYN\n");
        sb.append("======================================================\n");
        return sb.toString();
    }

    public void plusBalance(BigDecimal amount) {
        balance = balance.add(amount);
    }

    public void minusBalance(BigDecimal amount) {
        balance = balance.subtract(amount);
    }
}
