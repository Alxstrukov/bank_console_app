package ru.clevertec.console.application.services;

import java.sql.*;

public class DBService {
    public final static String DB_NAME = "postgres";
    public final static String DB_HOST = "localhost";
    public final static String DB_PORT = "5432";
    public final static String DB_USER = "postgres";
    public final static String DB_PASS = "postgres";
    public final static String DB_ENCODING = "?useUnicode=yes&characterEncoding=UTF-8";
    public final static String DB_URL = "jdbc:postgresql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME + DB_ENCODING;
    private static Connection connection = null;
    private static ResultSet resultSet;
    private static Statement statement;
    private static PreparedStatement preparedStatement;


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
    }
}
