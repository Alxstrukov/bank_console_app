package ru.clevertec.console.application.services;

import ru.clevertec.console.application.model.Transaction;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

public interface CheckCreatable {
   String CHECK_PATH = "src/main/java/ru/clevertec/console/application/checks/check_";
   void createCheck(Transaction transaction);
}
