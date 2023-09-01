package ru.clevertec.console.application.services;

import ru.clevertec.console.application.enums.Menu;
import ru.clevertec.console.application.menu_run.AuthorisedMenu;
import ru.clevertec.console.application.menu_run.MainMenu;
import ru.clevertec.console.application.model.User;
import ru.clevertec.console.application.utils.LoadManager;

import java.util.Scanner;

import static ru.clevertec.console.application.enums.Menu.AUTHORIZED;
import static ru.clevertec.console.application.enums.Menu.EXIT;


public class ConsoleService {
    private Scanner SCANNER = new Scanner(System.in);
    private Menu menuStatus;
    private User user = new User();
    AuthorisedMenu authorisedMenu = new AuthorisedMenu(user, menuStatus);
    MainMenu mainMenu = new MainMenu(user, menuStatus);


    //стартануть приложение
    public void startApp(boolean runType) {
        if (runType){
            LoadManager.loadDataBase();
        }
        menuStatus = AUTHORIZED;
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

