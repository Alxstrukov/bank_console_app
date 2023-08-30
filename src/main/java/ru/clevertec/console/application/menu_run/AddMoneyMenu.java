package ru.clevertec.console.application.menu_run;

import ru.clevertec.console.application.enums.Menu;
import ru.clevertec.console.application.model.BankAccount;
import ru.clevertec.console.application.model.User;
import ru.clevertec.console.application.services.OperationService;

import java.math.BigDecimal;

import static ru.clevertec.console.application.enums.Menu.ADD_MONEY;
import static ru.clevertec.console.application.enums.Menu.MAIN;

public class AddMoneyMenu extends AbstractMenu {

    public AddMoneyMenu(User user, Menu status) {
        super(user, status);
    }

    @Override
    public Menu run() {
        showAddMoneyMenu();
        if (!SCANNER.hasNextInt()){
            isValidInput(menuStatus);
        }
        int accountNumber = SCANNER.nextInt();
        switch (accountNumber) {
            case 0: {
                menuStatus = MAIN;
            }
            break;
            default: {
                runAddMoney(accountNumber);
            }
            break;
        }
        return menuStatus;
    }

    private void showAddMoneyMenu(){
        System.out.println("-------------------Clever-Bank--------------------");
        System.out.println("--------------------ADD MONEY---------------------");
        System.out.printf("Please, select your bank account number");
        System.out.println("0. Go back");
    }

    private Menu runAddMoney(int accountNumber) {
        if (!isBankAccountFromUser(accountNumber)) {
            System.out.println("Bank account is not found");
            menuStatus = ADD_MONEY;
        } else {
            System.out.printf("\nPlease, enter the amount\n");

            if (!SCANNER.hasNextBigDecimal()) {
                System.out.println("INCORRECT INPUT! Enter a number");
                SCANNER.nextLine();
                return menuStatus;
            }

            BigDecimal amount = SCANNER.nextBigDecimal();
            BankAccount bankAccount = getBankAccountByAccountNumber(accountNumber);

            OperationService operationService = new OperationService();
            operationService.addMoney(bankAccount, amount);
            bankAccount.addBalance(amount);

            System.out.println("Successfully add money");
            menuStatus = MAIN;
        }
        return menuStatus;
    }
}
