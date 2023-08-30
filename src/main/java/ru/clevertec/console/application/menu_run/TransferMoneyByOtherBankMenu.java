package ru.clevertec.console.application.menu_run;

import ru.clevertec.console.application.enums.Menu;
import ru.clevertec.console.application.model.BankAccount;
import ru.clevertec.console.application.model.User;
import ru.clevertec.console.application.services.BankAccountService;
import ru.clevertec.console.application.services.OperationService;

import java.math.BigDecimal;

import static ru.clevertec.console.application.enums.Menu.*;

public class TransferMoneyByOtherBankMenu extends AbstractMenu{
    protected TransferMoneyByOtherBankMenu(User user, Menu status) {
        super(user, status);
    }

    @Override
    public Menu run() {
        showTransferMoneyMenu();
        if (!SCANNER.hasNextInt()) {
            return isValidInput(menuStatus);
        }
        int accountNumber = SCANNER.nextInt();

        switch (accountNumber) {
            case 0: {
                menuStatus = MAIN;
            }
            break;
            default: {
                runTransferMoney(accountNumber);
            }
            break;
        }
        return menuStatus;
    }

    //показать меню перевода средств
    private void showTransferMoneyMenu() {
        System.out.println("-------------------Clever-Bank--------------------");
        System.out.println("-----TRANSFER MONEY TO THE OTHER BANK CLIENT-----");
        System.out.printf("\nPlease, select your bank account number\n");
        System.out.println("0. Go back");
    }

    private Menu runTransferMoney(int accountNumber) {
        if (!isBankAccountFromUser(accountNumber)) {
            System.out.println("Bank account number is not found");
            menuStatus = TRANSFER_MONEY_BY_OTHER_BANK;
        } else {
            Integer recipientAccountNumber = getInputRecipientAccountNumber();
            if (recipientAccountNumber == null) {
                return menuStatus;
            }

            if (isBankAccountOfBank(recipientAccountNumber)) {
                BigDecimal amount = getInputAmount();
                if (amount == null) {
                    return menuStatus;
                }
                performTransfer(accountNumber,recipientAccountNumber,amount);
            } else {
                System.out.println("*** THE BANK ACCOUNT NUMBER DOES NOT BELONG TO OTHER BANK ***");
                SCANNER.nextLine();
                return menuStatus = TRANSFER_MONEY_BY_CLEVER_BANK;
            }
        }
        return menuStatus;
    }

    //получить данные от пользрвателя (номер счета получателя)
    private Integer getInputRecipientAccountNumber() {
        System.out.printf("\nPlease, enter the other  bank client bank account number\n");
        if (!SCANNER.hasNextInt()) {
            System.out.println("INCORRECT INPUT! Enter a number");
            SCANNER.nextLine();
            menuStatus = TRANSFER_MONEY_BY_CLEVER_BANK;
            return null;
        }
        int recipientAccountNumber = SCANNER.nextInt();
        return recipientAccountNumber;
    }

    //получить данные от пользователя (сумму)
    private BigDecimal getInputAmount() {
        System.out.println("Please, enter the amount");
        if (!SCANNER.hasNextBigDecimal()) {
            System.out.println("INCORRECT INPUT! Enter a number");
            SCANNER.nextLine();
            menuStatus = TRANSFER_MONEY_BY_OTHER_BANK;
            return null;
        }
        return SCANNER.nextBigDecimal();
    }

    //получить id банка у банковского счета получателя
    private int getRecipientBankIdByAccountNumber(int accountNumber) {
        BankAccountService bankAccountService = new BankAccountService();
        return bankAccountService.readBankAccount(accountNumber).getBank().getID();
    }

    //запустить перевод средств
    private Menu performTransfer(int accountNumber, int recipientAccountNumber, BigDecimal amount) {
        BankAccount bankAccount = getBankAccountByAccountNumber(accountNumber);
        int recipientBankId = getRecipientBankIdByAccountNumber(recipientAccountNumber);
        OperationService operationService = new OperationService();
        if (operationService.transferMoney(bankAccount, recipientAccountNumber, recipientBankId, amount)) {
            bankAccount.receiveBalance(amount);
            System.out.println("Money was successfully transferred to the client other bank ");
        } else {
            System.out.println("****** NOT ENOUGH MONEY ******");
        }
        menuStatus = MAIN;
        return menuStatus;
    }


}
