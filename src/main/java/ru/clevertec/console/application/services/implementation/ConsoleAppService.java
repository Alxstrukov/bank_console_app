package ru.clevertec.console.application.services.implementation;

import ru.clevertec.console.application.enums.DataBase;
import ru.clevertec.console.application.enums.Menu;
import ru.clevertec.console.application.menu.implementation.AuthorisedMenu;
import ru.clevertec.console.application.menu.implementation.MainMenu;
import ru.clevertec.console.application.model.User;
import ru.clevertec.console.application.services.ApplicationRunnable;
import ru.clevertec.console.application.utils.LoadManager;

import java.util.Scanner;

import static ru.clevertec.console.application.enums.Menu.AUTHORIZED;
import static ru.clevertec.console.application.enums.Menu.EXIT;

public class ConsoleAppService implements ApplicationRunnable {
    private AuthorisedMenu authorisedMenu;
    private MainMenu mainMenu;
    private Menu menuStatus;
    private DayService dayService;
    private Scanner SCANNER;
    private User user;

    public ConsoleAppService() {
        SCANNER = new Scanner(System.in);
        user = new User();
        authorisedMenu = new AuthorisedMenu(user, menuStatus);
        mainMenu = new MainMenu(user, menuStatus);
        dayService = new DayService();
    }

    //стартануть приложение
    public void startApp(DataBase runType) {
        if (runType == DataBase.NEW) {
            LoadManager.loadDataBase();
        }
        menuStatus = AUTHORIZED;
        dayService.setDaemon(true);
        dayService.start();
        while (menuStatus != Menu.EXIT) {
            switch (menuStatus) {
                case AUTHORIZED: {
                    runAuthorisedMenu();
                }
                break;
                case MAIN: {
                    runMainMenu();
                }
                break;
                default: {
                    menuStatus = EXIT;
                    dayService.interrupt();
                }
                break;
            }
        }
    }

    //выполнить вход в главное меню
    private void runMainMenu() {
        mainMenu.setUser(user);
        mainMenu.setMenuStatus(menuStatus);
        menuStatus = mainMenu.run();
        if (menuStatus == AUTHORIZED) {
            user = mainMenu.getUser();
        }
    }

    //выполнить вход в меню авторизации
    private void runAuthorisedMenu() {
        authorisedMenu.setUser(user);
        authorisedMenu.setMenuStatus(menuStatus);
        menuStatus = authorisedMenu.run();
        user = authorisedMenu.getUser();
    }
}

