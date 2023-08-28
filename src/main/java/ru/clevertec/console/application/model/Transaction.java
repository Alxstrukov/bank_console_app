package ru.clevertec.console.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

@AllArgsConstructor
@Data
public class Transaction {
    private int transactionNumber;
    private String operation;
    private Integer senderAccountNumber;
    private Integer recipientAccountNumber;
    private String senderBankName;
    private String recipientBankName;
    private Date date;
    private Time time;
    private BigDecimal amount;

    //���������� ����������� �������, ��� ���� ����� � ������ public void createCheck (class CheckTXT)
    // �� ��������� ��� �� ���������� null ��� "0"
    public String getRecipientAccountNumber() {
        return recipientAccountNumber == 0 ? "-" : recipientAccountNumber.toString();
    }

    public String getSenderAccountNumber() {
        return senderAccountNumber == 0 ? "-" : senderAccountNumber.toString();
    }

    public String getSenderBankName() {
        return senderBankName == null ? "-" : senderBankName;
    }

    public String getRecipientBankName() {
        return recipientBankName == null ? "-" : recipientBankName;
    }
}
