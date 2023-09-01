package ru.clevertec.console.application.menu_run;

import ru.clevertec.console.application.enums.Menu;
import ru.clevertec.console.application.model.BankAccount;
import ru.clevertec.console.application.model.User;
import ru.clevertec.console.application.services.OperationService;
import ru.clevertec.console.application.utils.SQLquery;

import java.math.BigDecimal;

import static ru.clevertec.console.application.enums.Menu.MAIN;
import static ru.clevertec.console.application.enums.Menu.TRANSFER_MONEY_BY_CLEVER_BANK;

public class TransferMoneyByCleverBankMenu extends AbstractMenu {
    public TransferMoneyByCleverBankMenu(User user, Menu status) {
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

    //�������� ���� �������� �������
    private void showTransferMoneyMenu() {
        System.out.println("-------------------Clever-Bank--------------------");
        System.out.println("-----TRANSFER MONEY TO THE CLEVER-BANK CLIENT-----");
        System.out.println("           List bank accounts           ");
        showListBankAccountsInfo();
        System.out.printf("Please select your bank account number or enter '0' to return to the main menu\n");
        System.out.printf("For example: 1045\n");
    }

    private Menu runTransferMoney(int accountNumber) {
        if (!isBankAccountFromUser(accountNumber)) {
            System.out.println("Bank account number is not found");
            menuStatus = TRANSFER_MONEY_BY_CLEVER_BANK;
        } else {
            Integer recipientAccountNumber = getInputRecipientAccountNumber();
            if (recipientAccountNumber == accountNumber) {
                System.out.println("Error. The sender and the recipient have the same bank account");
                return menuStatus;
            }

            if (isBankAccountOfCleverBank(recipientAccountNumber)) {
                BigDecimal amount = getInputAmount();
                if (amount == null) {
                    return menuStatus;
                }
                performTransfer(accountNumber, recipientAccountNumber, amount);
            } else {
                System.out.println("*** THE BANK ACCOUNT NUMBER DOES NOT BELONG TO CLEVER-BANK ***");
                SCANNER.nextLine();
                return menuStatus = TRANSFER_MONEY_BY_CLEVER_BANK;
            }
        }
        return menuStatus;
    }

    //�������� �� ��������� �� ����� ���������� � ����������� (����� ������ ���� �� ��������)
    private boolean isCompareBankAccountNumber(int senderNumber, int recipientNumber) {
        return (senderNumber == recipientNumber);
    }

    //�������� ������ �� ������������ (����� ����� ����������)
    private Integer getInputRecipientAccountNumber() {
        System.out.printf("\nPlease, enter the Clever-Bank client bank account number\n");
        System.out.printf("For example: 1046\n");
        if (!SCANNER.hasNextInt()) {
            System.out.println("INCORRECT INPUT! Enter a number");
            SCANNER.nextLine();
            menuStatus = TRANSFER_MONEY_BY_CLEVER_BANK;
            return null;
        }
        int recipientAccountNumber = SCANNER.nextInt();
        return recipientAccountNumber;
    }

    //�������� ������ �� ������������ (�����)
    private BigDecimal getInputAmount() {
        System.out.println("Please, enter the amount");
        System.out.printf("For example: 107,50\n");
        if (!SCANNER.hasNextBigDecimal()) {
            System.out.println("INCORRECT INPUT! Enter a number");
            SCANNER.nextLine();
            menuStatus = TRANSFER_MONEY_BY_CLEVER_BANK;
            return null;
        }
        return SCANNER.nextBigDecimal();
    }

    //��������� ������� �������
    private Menu performTransfer(int accountNumber, int recipientAccountNumber, BigDecimal amount) {
        BankAccount bankAccount = getBankAccountByAccountNumber(accountNumber);
        OperationService operationService = new OperationService();
        if (operationService.transferMoney(bankAccount, recipientAccountNumber, SQLquery.CLEVER_BANK_ID, amount)) {
            bankAccount.minusBalance(amount);
            user.getBankAccounts().clear();
            readAllUserBankAccounts(user.getClient().getID());
            System.out.println("********** Successfully transfer money ***********\n");
        } else {
            clearConsole();
            System.out.println("**************** NOT ENOUGH MONEY ****************\n");
        }
        menuStatus = MAIN;
        return menuStatus;
    }

}
