package ru.clevertec.console.application;

import ru.clevertec.console.application.enums.DataBase;
import ru.clevertec.console.application.services.implementation.ConsoleAppService;

public class Main {
    public static void main(String[] args) {
        ConsoleAppService service = new ConsoleAppService();
        service.startApp(DataBase.NEW);
    }
}
