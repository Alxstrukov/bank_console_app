package ru.clevertec.console.application.services;

import java.math.BigDecimal;

public interface TransactionManagable {
    void createTransactionAddPercent(int recipientNumber,
                                     BigDecimal amount, int bankRecipientId);

    void createTransactionAddMoney(int recipientNumber,
                                   BigDecimal amount, int bankRecipientId);

    void createTransactionReceiveMoney(int SenderNumber,
                                       BigDecimal amount, int bankSenderId);

    void createTransactionTransferMoney
            (int senderNumber, int recipientNumber, int bankSenderId, int bankRecipientId, BigDecimal amount);
}
