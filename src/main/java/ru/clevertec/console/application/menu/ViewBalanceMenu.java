package ru.clevertec.console.application.menu;

import ru.clevertec.console.application.enums.Menu;
import ru.clevertec.console.application.model.User;

import static ru.clevertec.console.application.enums.Menu.MAIN;

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

    //�������� �������� ����
    private void showViewMenu() {
        System.out.println("---------------------Clever-Bank----------------------");
        System.out.println("------------------VIEW BALANCE MENU-------------------");
        System.out.println("                  List bank accounts           \n");
    }

    //�������� ���� � �������
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
