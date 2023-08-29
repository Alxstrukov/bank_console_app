package ru.clevertec.console.application.model;

import ru.clevertec.console.application.interfaces.CheckCreatable;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;

public class CheckTXT implements CheckCreatable {

    //записать чек в txt файл
    public void createCheck(Transaction transaction) {
        System.out.println("The method for creating the receipt called the thread -> " + Thread.currentThread().getName());
        System.out.println();
        try (PrintWriter printWriter = new PrintWriter(CHECK_PATH + transaction.getTransactionNumber() + ".txt")) {
            String dateString = new SimpleDateFormat("dd-MM-yyyy").format(transaction.getDate());

            printWriter.println("--------------------------------------------------------");
            printWriter.println("|                 Банковский чек                       |");
            printWriter.printf
                    ("|Чек:                                             %d|\n", transaction.getTransactionNumber());
            printWriter.printf
                    ("|%s                                    %s|\n", dateString, transaction.getTime());
            printWriter.printf
                    ("|Тип транзакции:                             %.20s|\n", translateOperationName(transaction.getOperation()));
            printWriter.printf
                    ("|Банк отправителя:                          %-11.11s|\n", transaction.getSenderBankName());
            printWriter.printf
                    ("|Банк получателя:                           %-11.11s|\n", transaction.getRecipientBankName());
            printWriter.printf
                    ("|Счет отправителя:                                 %-4.4s|\n", transaction.getSenderAccountNumber());
            printWriter.printf
                    ("|Счет получателя:                                  %-4.4s|\n", transaction.getRecipientAccountNumber());
            printWriter.printf
                    ("|Сумма:                                  %-10.10s BYN|\n", transaction.getAmount().setScale(2,
                            RoundingMode.HALF_EVEN));
            printWriter.printf
                    ("--------------------------------------------------------");
            printWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String translateOperationName(String operationName) {
        String translateName = null;
        switch (operationName) {
            case "ADD MONEY": {
                translateName = "Пополнение";
            }
            break;
            case "RECEIVE MONEY": {
                translateName = "Списание  ";
            }
            break;
            case "TRANSFER MONEY": {
                translateName = "Перевод   ";
            }
            break;
            default: {
                translateName = "";
            }
        }
        return translateName;
    }
}
