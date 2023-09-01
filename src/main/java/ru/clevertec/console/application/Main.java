package ru.clevertec.console.application;

import ru.clevertec.console.application.services.ConsoleService;

public class Main {
    public static void main(String[] args) {
        ConsoleService service = new ConsoleService();
        service.startApp(true);
    }
}
