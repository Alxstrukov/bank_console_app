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

    //�������� �������� ����
    private void showViewMenu() {
        System.out.println("-------------------Clever-Bank--------------------");
        System.out.println("-----------------VIEW BALANCE MENU----------------");
    }

    //������� ���� � ���������� ������
    private void showListBankAccountsInfo() {
        user.getBankAccounts().stream().forEach(it -> {
            System.out.println(it.showInfoBalance());
        });
        System.out.println();
    }

    //�������� ���� � �������
    private void getInput() {
        System.out.println("0. Go back");
        if (SCANNER.nextInt() == 0) {
            menuStatus = MAIN;
        } else {
            isValidInput(menuStatus);
        }
    }
}
