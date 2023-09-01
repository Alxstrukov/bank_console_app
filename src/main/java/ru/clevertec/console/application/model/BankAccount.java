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
        String infoLine = String.format("Bank: %12.12s  Number:%d  Balance = %6.10s BYN",
                bank.getBankName(),
                accountNumber,
                balance.setScale(2, RoundingMode.HALF_EVEN))
                + "\n======================================================\n";
        return infoLine;
    }

    public void plusBalance(BigDecimal amount) {
        balance = balance.add(amount);
    }

    public void minusBalance(BigDecimal amount) {
        balance = balance.subtract(amount);
    }
}
