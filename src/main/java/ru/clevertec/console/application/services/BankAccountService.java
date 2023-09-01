package ru.clevertec.console.application.services;


import lombok.NoArgsConstructor;
import ru.clevertec.console.application.model.Bank;
import ru.clevertec.console.application.model.BankAccount;
import ru.clevertec.console.application.model.Client;
import ru.clevertec.console.application.utils.SQLquery;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

@NoArgsConstructor
public class BankAccountService implements BankAccountManagable {

    //создание нового банк.счета (ID банка указываем в параметрах)
    public void createBankAccount(int bankId, int clientId) {
        try (PreparedStatement preparedStatement = DBService.createPreparedStatement(SQLquery.INSERT_BANK_ACCOUNT)) {
            preparedStatement.setInt(1, bankId);
            preparedStatement.setInt(2, clientId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //считать банковский счет с БД и создать объект
    public BankAccount readBankAccount(int bankAccountNumber) {
        try (ResultSet resultSet = DBService.getQueryResult(SQLquery.SELECT_BANK_ACCOUNTS + bankAccountNumber)) {
            resultSet.next();
            int bankId = resultSet.getInt("bank_id");
            String bankName = resultSet.getString("name_bank");
            int clientId = resultSet.getInt("client_id");
            String lastName = resultSet.getString("last_name");
            String firstName = resultSet.getString("first_name");
            int accountNumber = resultSet.getInt("account_number");
            BigDecimal balance = resultSet.getBigDecimal("balance");
            Date date = resultSet.getDate("date");
            Bank bank = new Bank(bankId, bankName);
            Client client = new Client(clientId, lastName, firstName);
            return new BankAccount(accountNumber, balance, bank, client, date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //удалитьт банковский счет с БД
    public void deleteBankAccount(int bankAccountNumber) {
        try (PreparedStatement preparedStatement = DBService.createPreparedStatement(SQLquery.DELETE_BANK_ACCOUNT)) {
            preparedStatement.setInt(1, bankAccountNumber);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
