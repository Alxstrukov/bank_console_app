package ru.clevertec.console.application.services;

import ru.clevertec.console.application.model.Client;
import ru.clevertec.console.application.utils.SqlQuery;

import java.sql.*;

public class ClientService {

    public void createClient(String lastName, String firstName) {
        try (PreparedStatement ps = DBService.createPreparedStatement(SqlQuery.INSERT_CLIENT);) {
            ps.setString(1, lastName);
            ps.setString(2, firstName);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Client readClient(int clientId) {
        Client client = null;
        try (ResultSet queryResult = DBService.getQueryResult(SqlQuery.SELECT_ALL_CLIENTS);) {
            while (queryResult.next()) {
                if (queryResult.getInt(1) == clientId) {
                    String lastName = queryResult.getString(2);
                    String firstName = queryResult.getString(3);
                    client = new Client(clientId, lastName, firstName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    public void updateClient(int clientId, String newLastName, String newFirstName) {
        try (PreparedStatement ps = DBService.createPreparedStatement(SqlQuery.UPDATE_CLIENT_BY_ID);) {
            ps.setString(1, newLastName);
            ps.setString(2, newFirstName);
            ps.setInt(3, clientId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteClient(int clientId) {
        try (PreparedStatement ps = DBService.createPreparedStatement(SqlQuery.DELETE_CLIENT_BY_ID)) {
            ps.setInt(1, clientId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
