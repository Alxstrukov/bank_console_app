package ru.clevertec.console.application.menu.implementation;

import ru.clevertec.console.application.enums.Menu;
import ru.clevertec.console.application.model.BankAccount;
import ru.clevertec.console.application.model.User;
import ru.clevertec.console.application.services.implementation.OperationService;

import java.math.BigDecimal;

import static ru.clevertec.console.application.enums.Menu.MAIN;
import static ru.clevertec.console.application.enums.Menu.RECEIVE_MONEY;

public class ReceiveMoneyMenu extends AbstractMenu {
    public ReceiveMoneyMenu(User user, Menu status) {
        super(user, status);
    }

    @Override
    public Menu run() {
        showReceiveMoney();
        if (!SCANNER.hasNextInt()) {
            isValidInput(menuStatus);
        }
        int accountNumber = SCANNER.nextInt();
        switch (accountNumber) {
            case 0: {
                menuStatus = MAIN;
            }
            break;
            default: {
                runReceiveMoney(accountNumber);
            }
            break;
        }
        return menuStatus;
    }

    private void showReceiveMoney() {
        System.out.println("-------------------Clever-Bank--------------------");
        System.out.println("------------------RECEIVE MONEY-------------------");
        System.out.println("           List bank accounts           ");
        showListBankAccountsInfo();
        System.out.printf("Please select your bank account number or enter '0' to return to the main menu\n");
        System.out.printf("For example: 1045\n");
    }

    private Menu runReceiveMoney(int accountNumber) {
        if (!isBankAccountFromUser(accountNumber)) {
            System.out.println("Bank account is not found");
            menuStatus = RECEIVE_MONEY;
        } else {
            System.out.printf("\n Please, enter the amount\n");
            System.out.printf("For example: 3,44\n");

            if (!SCANNER.hasNextBigDecimal()) {
                System.out.println("INCORRECT INPUT! Enter a number");
                SCANNER.nextLine();
                return menuStatus;
            }

            BigDecimal amount = SCANNER.nextBigDecimal();
            BankAccount bankAccount = getBankAccountByAccountNumber(accountNumber);

            OperationService operationService = new OperationService();
            if (operationService.receiveMoney(bankAccount, amount)) {
                bankAccount.minusBalance(amount);

                System.out.println("*********** Successfully receive money ***********\n");
            } else {
                clearConsole();
                System.out.println("**************** NOT ENOUGH MONEY ****************\n");
            }
            menuStatus = MAIN;
        }
        return menuStatus;
    }
}

