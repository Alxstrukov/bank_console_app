package ru.clevertec.console.application.services;

import lombok.Setter;
import ru.clevertec.console.application.utils.PropertiesManager;

import java.sql.*;

public class DBService {
    /*private static final String DB_SERVER;
    private final static String DB_NAME;
    private final static String DB_HOST;
    private final static String DB_PORT;
    private final static String DB_USER;
    private final static String DB_PASS;
    private final static String DB_ENCODING;
    private final static String DB_URL;
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;
    private static PreparedStatement preparedStatement;

    static {
        connection = null;
        DB_NAME = PropertiesManager.getConfigProperties("dbName");
        DB_HOST = PropertiesManager.getConfigProperties("dbHost");
        DB_PORT = PropertiesManager.getConfigProperties("dbPort");
        DB_USER = PropertiesManager.getConfigProperties("dbUser");
        DB_PASS = PropertiesManager.getConfigProperties("dbPass");
        DB_ENCODING = PropertiesManager.getConfigProperties("dbEncoding");
        DB_SERVER = PropertiesManager.getConfigProperties("jdbcServer");
        DB_URL = DB_SERVER + DB_HOST + ":" + DB_PORT + "/" + DB_NAME + DB_ENCODING;
    }

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static ResultSet getQueryResult(String query) {
        try {
            if (resultSet == null || resultSet.isClosed())
                try {
                    resultSet = createStatement().executeQuery(query);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public static Statement createStatement() {
        try {
            if (statement == null || statement.isClosed()) {
                statement = getConnection().createStatement();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }

    public static PreparedStatement createPreparedStatement(String query) {
        try {
            if (preparedStatement == null || preparedStatement.isClosed()) {
                preparedStatement = getConnection().prepareStatement(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preparedStatement;
    }*/

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private static final String DB_SERVER;
    private final static String DB_NAME;
    private final static String DB_HOST;
    private final static String DB_PORT;
    private final static String DB_USER;
    private final static String DB_PASS;
    private final static String DB_ENCODING;
    private final static String DB_URL;
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;
    private static PreparedStatement preparedStatement;

    static {
        connection = null;
        DB_NAME = PropertiesManager.getConfigProperties("dbName");
        DB_HOST = PropertiesManager.getConfigProperties("dbHost");
        DB_PORT = PropertiesManager.getConfigProperties("dbPort");
        DB_USER = PropertiesManager.getConfigProperties("dbUser");
        DB_PASS = PropertiesManager.getConfigProperties("dbPass");
        DB_ENCODING = PropertiesManager.getConfigProperties("dbEncoding");
        DB_SERVER = PropertiesManager.getConfigProperties("jdbcServer");
        DB_URL = DB_SERVER + DB_HOST + ":" + DB_PORT + "/" + DB_NAME + DB_ENCODING;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    synchronized public static ResultSet getQueryResult(String query) {
        try {
            resultSet = createStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public static Statement createStatement() {
        try {
            if (statement == null || statement.isClosed()) {
                statement = getConnection().createStatement();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }

    public static PreparedStatement createPreparedStatement(String query) {
        try {
            preparedStatement = getConnection().prepareStatement(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preparedStatement;
    }
}
