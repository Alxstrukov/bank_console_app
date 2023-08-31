package ru.clevertec.console.application.menu_run;

import ru.clevertec.console.application.enums.Menu;
import ru.clevertec.console.application.model.User;

import static ru.clevertec.console.application.enums.Menu.MAIN;
import static ru.clevertec.console.application.enums.Menu.VIEW_BALANCE;

public class ViewBalanceMenu extends AbstractMenu {
    public ViewBalanceMenu(User user, Menu status) {
        super(user, status);
    }

    @Override
    public Menu run() {
        showViewMenu();
        showListBankAccountsInfo();
        getInput();
        return menuStatus;
    }

    //показать название меню
    private void showViewMenu() {
        System.out.println("---------------------Clever-Bank----------------------");
        System.out.println("------------------VIEW BALANCE MENU-------------------");
        System.out.println("                  List bank accounts           \n");
    }

    //получить ввод с консоли
    private boolean getInput() {
        System.out.printf("Please, enter any value to return to the main menu\n");
        if (!SCANNER.hasNextInt()) {
            isValidInput(menuStatus);
        } else {
            if (SCANNER.nextInt() == 0) {
                menuStatus = MAIN;
            } else {
                isValidInput(menuStatus);
                return false;
            }
        }
        return true;
    }
}
