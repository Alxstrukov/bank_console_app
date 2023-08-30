package ru.clevertec.console.application.menu_run;

import ru.clevertec.console.application.enums.Menu;
import ru.clevertec.console.application.model.User;

import static ru.clevertec.console.application.enums.Menu.*;

public class MainMenu extends AbstractMenu {
    private ViewBalanceMenu viewBalanceMenu = new ViewBalanceMenu(user, menuStatus);
    private AddMoneyMenu addMoneyMenu = new AddMoneyMenu(user, menuStatus);
    private ReceiveMoneyMenu receiveMoneyMenu = new ReceiveMoneyMenu(user, menuStatus);
    private TransferMoneyByCleverBankMenu transferClever = new TransferMoneyByCleverBankMenu(user, menuStatus);
    private TransferMoneyByOtherBankMenu transferOther = new TransferMoneyByOtherBankMenu(user, menuStatus);

    public MainMenu(User user, Menu status) {
        super(user, status);
    }

    @Override
    public Menu run() {
        showMainMenu();
        if (SCANNER.hasNextInt()) {
            switch (SCANNER.nextInt()) {
                case 0: {
                    menuStatus = AUTHORIZED;
                    user = new User();
                }
                break;
                case 1: {
                    runViewBalance();
                }
                break;
                case 2: {
                    runAddMoney();
                }
                break;
                case 3: {
                    runReceiveMoney();
                }
                break;
                case 4: {
                    runTransferMoneyCleverBank();
                }
                break;
                case 5: {
                    runTransferMoneyOtherBank();
                }
                break;
                case 6: {
                    menuStatus = AUTHORIZED;
                }
                break;
                default: {
                    menuStatus = isValidInput(MAIN);
                }
                break;
            }
        } else {
            menuStatus = isValidInput(MAIN);
        }
        return menuStatus;
    }

    //показать главное меню
    private void showMainMenu() {
        System.out.println("-------------------Clever-Bank--------------------");
        System.out.println("--------------------MAIN MENU---------------------");
        System.out.println("1. View bank accounts balance");
        System.out.println("2. Add money in bank account");
        System.out.println("3. Receive money in bank account");
        System.out.println("4. Transfer money to a Clever-Bank client");
        System.out.println("5. Transfer money to a client of another bank");
        System.out.println("6. Account statement");
        System.out.println("0. Exit from account");
        System.out.println("***    Enter the item number and press Enter    ***");
    }

    //вызов меню показать баланс
    private void runViewBalance(){
        viewBalanceMenu.setUser(user);
        viewBalanceMenu.setMenuStatus(menuStatus);
        viewBalanceMenu.run();
    }

    //вызов меню пополнения счета
    private void runAddMoney(){
        addMoneyMenu.setMenuStatus(menuStatus);
        addMoneyMenu.setUser(user);
        addMoneyMenu.run();
    }

    //вызов меню списания средств
    private void runReceiveMoney(){
        receiveMoneyMenu.setMenuStatus(menuStatus);
        receiveMoneyMenu.setUser(user);
        receiveMoneyMenu.run();
    }

    //вызов меню перевода средств в Clever-Bank
    private void runTransferMoneyCleverBank(){
        transferClever.setMenuStatus(menuStatus);
        transferClever.setUser(user);
        transferClever.run();
    }

    //вызов меню перевода средств в другой банк
    private void runTransferMoneyOtherBank(){
        transferOther.setMenuStatus(menuStatus);
        transferOther.setUser(user);
        transferOther.run();
    }
}
