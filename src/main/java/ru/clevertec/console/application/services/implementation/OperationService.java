package ru.clevertec.console.application.services.implementation;

import ru.clevertec.console.application.model.BankAccount;
import ru.clevertec.console.application.model.Transaction;
import ru.clevertec.console.application.services.DBService;
import ru.clevertec.console.application.services.OperationManagable;
import ru.clevertec.console.application.utils.SQLquery;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OperationService implements OperationManagable {

    //начисление процента
    synchronized public boolean addBalanceByPercentage(int accountNumber, BigDecimal amount) {
        boolean status = false;
        if (!status) {
            try (PreparedStatement ps = DBService.createPreparedStatement(SQLquery.ADD_MONEY)) {
                ps.setBigDecimal(1, amount);
                ps.setInt(2, accountNumber);
                ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
            TransactionService transactionService = new TransactionService();
            transactionService.createTransactionAddPercent(accountNumber, amount, SQLquery.CLEVER_BANK_ID);
            status = true;
        }
        return status;
    }

    //операция пополнение счета
    synchronized public boolean addMoney(BankAccount bankAccount, BigDecimal amount) {
        boolean status = false;
        if (!status) {
            try (PreparedStatement ps = DBService.createPreparedStatement(SQLquery.ADD_MONEY);) {
                ps.setBigDecimal(1, amount);
                ps.setInt(2, bankAccount.getAccountNumber());
                ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
            TransactionService transactionService = new TransactionService();
            transactionService.createTransactionAddMoney(bankAccount.getAccountNumber(), amount, bankAccount.getBank().getID());
            Transaction transaction = transactionService.getLatestTransactionByOperation(bankAccount.getAccountNumber(), SQLquery.SELECT_LATEST_TRANSACTION_ADD);
            CheckTXTService checkTxt = new CheckTXTService();
            checkTxt.createCheck(transaction);
            status = true;
        }
        return status;
    }

    //операция снятие средств со счета
    synchronized public boolean receiveMoney(BankAccount bankAccount, BigDecimal amount) {
        if (isEnoughMoney(bankAccount, amount)) {
            try (PreparedStatement preparedStatement = DBService.createPreparedStatement(SQLquery.RECEIVE_MONEY)) {
                preparedStatement.setBigDecimal(1, amount);
                preparedStatement.setInt(2, bankAccount.getAccountNumber());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            TransactionService transactionService = new TransactionService();
            transactionService.createTransactionReceiveMoney(bankAccount.getAccountNumber(), amount, bankAccount.getBank().getID());
            Transaction transaction = transactionService.getLatestTransactionByOperation(bankAccount.getAccountNumber(), SQLquery.SELECT_LATEST_TRANSACTION_RECEIVE);
            CheckTXTService checkTxt = new CheckTXTService();
            checkTxt.createCheck(transaction);
            return true;
        }
        return false;
    }

    //перевод средств
    synchronized public boolean transferMoney(BankAccount bankAccount, int bankAccountNumber, int bankId, BigDecimal amount) {
        if (isThereAccountNumber(bankId, bankAccountNumber) && isEnoughMoney(bankAccount, amount)) {
            try (PreparedStatement preparedStatement = DBService.createPreparedStatement(SQLquery.TRANSFER_MONEY)) {
                preparedStatement.setBigDecimal(1, amount);
                preparedStatement.setInt(2, bankAccount.getAccountNumber());
                preparedStatement.setBigDecimal(3, amount);
                preparedStatement.setInt(4, bankAccountNumber);
                preparedStatement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
            TransactionService transactionService = new TransactionService();
            transactionService.createTransactionTransferMoney(bankAccount.getAccountNumber(),
                    bankAccountNumber,
                    bankAccount.getBank().getID(), bankId, amount);
            Transaction transaction = transactionService.getLatestTransactionByOperation(bankAccount.getAccountNumber(), SQLquery.SELECT_LATEST_TRANSACTION_TRANSFER);
            CheckTXTService checkTxt = new CheckTXTService();
            checkTxt.createCheck(transaction);
            return true;
        }
        return false;
    }

    //достаточно ли денег на счету для списания
    private boolean isEnoughMoney(BankAccount bankAccount, BigDecimal amount) {
        BigDecimal result = bankAccount.getBalance().subtract(amount);
        if (result.signum() < 0) return false;
        return true;
    }

    //проверяет, принадлежит ли данный номер счета банку
    private boolean isThereAccountNumber(int bankId, int accountNumber) {
        try (ResultSet resultSet = DBService.getQueryResult(SQLquery.SELECT_ALL_BANK_ACCOUNTS_BY_BANK_ID + bankId)) {
            while (resultSet.next()) {
                if (resultSet.getInt("account_number") == accountNumber) return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
