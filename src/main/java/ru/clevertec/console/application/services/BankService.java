package ru.clevertec.console.application.services;

import lombok.NoArgsConstructor;
import ru.clevertec.console.application.model.Bank;
import ru.clevertec.console.application.utils.SqlQuery;

import java.sql.*;

@NoArgsConstructor
public class BankService {

    //метод создающий Bank в БД
    public void createBank(String bankName) {
        try (PreparedStatement preparedStatement = DBService.createPreparedStatement(SqlQuery.INSERT_BANK)) {
            preparedStatement.setString(1, bankName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //метод считывающий с БД банк по Id и возвращающий объект Bank
    public Bank readBank(int bankId) {
        try (ResultSet resultSet = DBService.getQueryResult(SqlQuery.SELECT_BANKS + bankId)) {
            resultSet.next();
            if (resultSet.getInt("id") == bankId) {
                String bankName = resultSet.getString(2);
                return new Bank(bankId, bankName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //метод ищущий в БД банк по ID и изменяющий его имя
    public void updateBank(int bank_id, String newBankName) {
        try (PreparedStatement preparedStatement = DBService.createPreparedStatement(SqlQuery.UPDATE_BANK_BY_ID)) {
            preparedStatement.setString(1, newBankName);
            preparedStatement.setInt(2, bank_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //метод ищущий в БД банк по ID и удаляющий его
    public void deleteBank(int bankID) {
        try (PreparedStatement preparedStatement = DBService.createPreparedStatement(SqlQuery.DELETE_BANK_BY_ID)) {
            preparedStatement.setInt(1, bankID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
