package ru.clevertec.console.application.services;

import ru.clevertec.console.application.exception.TransactionNumberNullException;
import ru.clevertec.console.application.model.Transaction;
import ru.clevertec.console.application.utils.SQLquery;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;

public class TransactionService {


    //создать транзакцию пополнения средств в БД
    synchronized public void createTransactionAddMoney(int accountRecipientNumber,
                                                       BigDecimal amount, int bankRecipientId) {
        try (PreparedStatement preparedStatement = DBService.createPreparedStatement(SQLquery.INSERT_TRANSACTION_ADD_MONEY)) {
            preparedStatement.setInt(1, accountRecipientNumber);
            preparedStatement.setBigDecimal(2, amount);
            preparedStatement.setInt(3, bankRecipientId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //создать транзакцию списания средств в БД
    synchronized public void createTransactionReceiveMoney(int accountSenderNumber,
                                                           BigDecimal amount, int bankSenderId) {
        try (PreparedStatement preparedStatement = DBService.createPreparedStatement(SQLquery.INSERT_TRANSACTION_RECEIVE_MONEY)) {
            preparedStatement.setInt(1, accountSenderNumber);
            preparedStatement.setBigDecimal(2, amount);
            preparedStatement.setInt(3, bankSenderId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //создать транзакцию перевода средств в БД
    synchronized public void createTransactionTransferMoney
    (int accountSenderNumber, int accountRecipientNumber, int bankSenderId, int bankRecipientId, BigDecimal amount) {
        try (PreparedStatement preparedStatement = DBService.createPreparedStatement(SQLquery.INSERT_TRANSACTION_TRANSFER_MONEY)) {
            preparedStatement.setInt(1, accountSenderNumber);
            preparedStatement.setInt(2, accountRecipientNumber);
            preparedStatement.setInt(3, bankSenderId);
            preparedStatement.setInt(4, bankRecipientId);
            preparedStatement.setBigDecimal(5, amount);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //получить самую последнюю проведенную транзакцию по номеру счета и типу операции
    synchronized public Transaction getLatestTransactionByOperation(int accountNumber, String query) {
        Transaction transaction = null;
        try (ResultSet resultSet = DBService.getQueryResult(String.
                format(query, accountNumber))) {
            if (!resultSet.next()) {
                throw new TransactionNumberNullException("Failed to read transaction number from database");
            }
            int id = resultSet.getInt("id");
            Integer senderAccountNumber = resultSet.getInt("sender_account");
            Integer recipientAccountNumber = resultSet.getInt("recipient_account");
            String senderBank = resultSet.getString("s_bank");
            String recipientBank = resultSet.getString("r_bank");
            String operation = resultSet.getString("operation");
            BigDecimal amount = resultSet.getBigDecimal("amount");
            Date date = resultSet.getDate("date");
            Time time = resultSet.getTime("time");
            transaction = new Transaction(id, operation, senderAccountNumber, recipientAccountNumber,
                    senderBank, recipientBank, date, time, amount);
        } catch (Exception | TransactionNumberNullException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return transaction;
    }
}
