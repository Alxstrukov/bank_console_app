package ru.clevertec.console.application.utils;

import lombok.NoArgsConstructor;
import ru.clevertec.console.application.services.DBService;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@NoArgsConstructor
public class LoadManager {
    public static void loadDataBase() {
        try (PreparedStatement preparedStatement = DBService.createPreparedStatement(SQLquery.LOAD_AND_INIT_DB)) {
            preparedStatement.executeUpdate();
            System.out.println("SUCCESSFULLY LOAD DATA");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
