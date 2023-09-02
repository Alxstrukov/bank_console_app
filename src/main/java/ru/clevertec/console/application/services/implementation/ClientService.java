package ru.clevertec.console.application.services.implementation;

import org.postgresql.util.PSQLException;
import ru.clevertec.console.application.model.Client;
import ru.clevertec.console.application.services.ClientManagable;
import ru.clevertec.console.application.services.DBService;
import ru.clevertec.console.application.utils.SQLquery;

import java.sql.*;

public class ClientService implements ClientManagable {

    public void createClient(String lastName, String firstName) {
        try (PreparedStatement ps = DBService.createPreparedStatement(SQLquery.INSERT_CLIENT);) {
            ps.setString(1, lastName);
            ps.setString(2, firstName);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Client readClient(int clientId) {
        Client client = null;
        try (ResultSet queryResult = DBService.getQueryResult(SQLquery.SELECT_ALL_CLIENTS);) {
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
        try (PreparedStatement ps = DBService.createPreparedStatement(SQLquery.UPDATE_CLIENT_BY_ID)) {
            ps.setString(1, newLastName);
            ps.setString(2, newFirstName);
            ps.setInt(3, clientId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteClient(int clientId) {
        try (PreparedStatement ps = DBService.createPreparedStatement(SQLquery.DELETE_CLIENT_BY_ID)) {
            ps.setInt(1, clientId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
