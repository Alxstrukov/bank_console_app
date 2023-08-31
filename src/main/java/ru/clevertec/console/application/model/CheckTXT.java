package ru.clevertec.console.application.model;

import ru.clevertec.console.application.services.CheckCreatable;
import ru.clevertec.console.application.utils.SQLquery;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;

public class CheckTXT implements CheckCreatable {

    //записать чек в txt файл
    synchronized public void createCheck(Transaction transaction) {
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
            showCheckConsole(transaction);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //вывод чека в консоль
    public void showCheckConsole(Transaction transaction){
        String dateString = new SimpleDateFormat("dd-MM-yyyy").format(transaction.getDate());

        System.out.println("--------------------------------------------------------");
        System.out.println("|                     Bank check                       |");
        System.out.printf
                ("|Check:                                           %d|\n", transaction.getTransactionNumber());
        System.out.printf
                ("|%s                                    %s|\n", dateString, transaction.getTime());
        System.out.printf
                ("|Type transaction:                       %.20s|\n",correctOperationName(transaction.getOperation()));
        System.out.printf
                ("|Bank sender:                               %-11.11s|\n", transaction.getSenderBankName());
        System.out.printf
                ("|Bank recipient:                            %-11.11s|\n", transaction.getRecipientBankName());
        System.out.printf
                ("|Bank account sender:                              %-4.4s|\n", transaction.getSenderAccountNumber());
        System.out.printf
                ("|Bank account recipient:                           %-4.4s|\n", transaction.getRecipientAccountNumber());
        System.out.printf
                ("|Amount:                                 %-10.10s BYN|\n", transaction.getAmount().setScale(2,
                        RoundingMode.HALF_EVEN));
        System.out.printf
                ("--------------------------------------------------------");
        System.out.println("\nThe check is saved along the path: "
                + CHECK_PATH
                + transaction.getTransactionNumber()
                + ".txt\n");
    }

    //перевести с английского тип операции
    private String translateOperationName(String operationName) {
        String translateName = null;
        switch (operationName) {
            case SQLquery.ADD_MONEY_ENUM_DB: {
                translateName = "Пополнение";
            }
            break;
            case SQLquery.RECEIVE_MONEY_ENUM_DB: {
                translateName = "Списание  ";
            }
            break;
            case SQLquery.TRANSFER_MONEY_ENUM_DB: {
                translateName = "Перевод   ";
            }
            break;
            default: {
                translateName = "";
            }
        }
        return translateName;
    }

    //коррекция длины строки (тип операции) для корректного вывода в консоли
    private String correctOperationName(String operationName){
        String translateName = null;
        switch (operationName) {
            case SQLquery.ADD_MONEY_ENUM_DB: {
                translateName = "ADD MONEY     ";
            }
            break;
            case SQLquery.RECEIVE_MONEY_ENUM_DB: {
                translateName = "RECEIVE MONEY ";
            }
            break;
            case SQLquery.TRANSFER_MONEY_ENUM_DB: {
                translateName = "TRANSFER MONEY";
            }
            break;
            default: {
                translateName = "";
            }
        }
        return translateName;
    }
}
