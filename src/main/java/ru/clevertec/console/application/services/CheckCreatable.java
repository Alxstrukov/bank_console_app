package ru.clevertec.console.application.services;

import ru.clevertec.console.application.model.Transaction;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

public interface CheckCreatable {
    void createCheck(Transaction transaction);

    default void showCheckConsole(Transaction transaction) {
    }
}
