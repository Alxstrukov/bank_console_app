package ru.clevertec.console.application.model;

import ru.clevertec.console.application.interfaces.CheckCreatable;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;

public class CheckTXT implements CheckCreatable {

    //�������� ��� � txt ����
    public void createCheck(Transaction transaction) {
        System.out.println("The method for creating the receipt called the thread -> " + Thread.currentThread().getName());
        System.out.println();
        try (PrintWriter printWriter = new PrintWriter(CHECK_PATH + transaction.getTransactionNumber() + ".txt")) {
            String dateString = new SimpleDateFormat("dd-MM-yyyy").format(transaction.getDate());

            printWriter.println("--------------------------------------------------------");
            printWriter.println("|                 ���������� ���                       |");
            printWriter.printf
                    ("|���:                                             %d|\n", transaction.getTransactionNumber());
            printWriter.printf
                    ("|%s                                    %s|\n", dateString, transaction.getTime());
            printWriter.printf
                    ("|��� ����������:                             %.20s|\n", translateOperationName(transaction.getOperation()));
            printWriter.printf
                    ("|���� �����������:                          %-11.11s|\n", transaction.getSenderBankName());
            printWriter.printf
                    ("|���� ����������:                           %-11.11s|\n", transaction.getRecipientBankName());
            printWriter.printf
                    ("|���� �����������:                                 %-4.4s|\n", transaction.getSenderAccountNumber());
            printWriter.printf
                    ("|���� ����������:                                  %-4.4s|\n", transaction.getRecipientAccountNumber());
            printWriter.printf
                    ("|�����:                                  %-10.10s BYN|\n", transaction.getAmount().setScale(2,
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
                translateName = "����������";
            }
            break;
            case "RECEIVE MONEY": {
                translateName = "��������  ";
            }
            break;
            case "TRANSFER MONEY": {
                translateName = "�������   ";
            }
            break;
            default: {
                translateName = "";
            }
        }
        return translateName;
    }
}
