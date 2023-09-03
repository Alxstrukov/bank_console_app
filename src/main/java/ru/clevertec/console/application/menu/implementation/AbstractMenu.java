package ru.clevertec.console.application.menu.implementation;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.clevertec.console.application.enums.Menu;
import ru.clevertec.console.application.menu.MenuRunnable;
import ru.clevertec.console.application.model.Bank;
import ru.clevertec.console.application.model.BankAccount;
import ru.clevertec.console.application.model.User;
import ru.clevertec.console.application.services.DBService;
import ru.clevertec.console.application.utils.SQLquery;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

import static ru.clevertec.console.application.enums.Menu.TRANSFER_MONEY_BY_CLEVER_BANK;

@NoArgsConstructor
@Data
public abstract class AbstractMenu implements MenuRunnable {
    protected User user;
    protected Menu menuStatus;
    protected Scanner SCANNER = new Scanner(System.in);

    protected AbstractMenu(User user, Menu status) {
        this.user = user;
        menuStatus = status;
    }


    //����� ��������� � �������� ����� � �������� ������� ����
    protected Menu isValidInput(Menu status) {
        clearConsole();
        System.out.println("INCORRECT INPUT! Enter a date");
        SCANNER.nextLine();
        return status;
    }

    //�������� ����� ����������� ����� �������������
    protected BankAccount getBankAccountByAccountNumber(int accountNumber) {
        BankAccount bankAccount = user.getBankAccounts()
                .stream()
                .filter(it -> it.getAccountNumber() == accountNumber)
                .findFirst().get();
        return bankAccount;
    }

    //�������� ��� ����� ������������
    protected void readAllUserBankAccounts(int userId) {
        try (ResultSet resultSet = DBService.getQueryResult(SQLquery.SELECT_USER_ALL_BANK_ACCOUNTS + userId)) {
            while (resultSet.next()) {
                int accountNumber = resultSet.getInt("account_number");
                BigDecimal balance = resultSet.getBigDecimal("balance");
                Date date = resultSet.getDate("date");
                int bankId = resultSet.getInt("bank_id");
                String bankName = resultSet.getString("name_bank");
                Bank bank = new Bank(bankId, bankName);
                BankAccount bankAccount = new BankAccount(accountNumber, balance, bank, user.getClient(), date);
                user.getBankAccounts().add(bankAccount);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    //������� (�����) ����������� ������� �����
    protected void clearConsole() {
        System.out.printf("\n\n\n\n\n\n\n\n\n\n\n");
    }

    //��������, ���� �� ������ � ����� id � �����
    protected boolean isClientOfBank(int id) {
        return isEmptyInDataBaseById(id, SQLquery.IS_CLIENT_OF_BANK);
    }

    //��������, ���������� �� ����� ����� ����� � ���� ������
    protected boolean isBankAccountOfBank(int accountNumber) {
        return isEmptyInDataBaseById(accountNumber, SQLquery.IS_BANK_ACCOUNT_OF_OTHER_BANK);
    }

    //��������, ����������� �� ����� ����� ����� ����� Clever-Bank
    protected boolean isBankAccountOfCleverBank(int accountNumber) {
        return isEmptyInDataBaseById(accountNumber, SQLquery.IS_BANK_ACCOUNT_OF_CLEVER_BANK);
    }

    //����������� �� ����� ����� ������������
    protected boolean isBankAccountFromUser(int accountNumber) {
        if (user.getBankAccounts().stream()
                .filter(it -> it.getAccountNumber() == accountNumber)
                .findFirst().isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    //���� �� ���-�� � ���� ������ �� ������ Id
    protected boolean isEmptyInDataBaseById(int id, String query) {
        try (ResultSet resultSet = DBService.getQueryResult(query + id)) {
            return (resultSet.next());
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
        return false;
    }

    //�������� ����� �� ���������� ���������� �����
    protected boolean isValidName(String name) {
        return name.matches("[a-zA-Z]+");
    }

    //�������� ID ������ ������� � �����
    protected Integer getNewClientId(String lastName, String firstName) {
        try (ResultSet resultSet = DBService
                .getQueryResult(String
                        .format(SQLquery.SELECT_NEW_CLIENT_ID, lastName, firstName))) {
            resultSet.next();
            return resultSet.getInt("id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //�������� ID ������ ����� � �����
    protected Integer getNewBankAccountId(int clientId) {
        try (ResultSet resultSet = DBService
                .getQueryResult(String
                        .format(SQLquery.SELECT_NEW_BANK_ACCOUNT_ID, clientId))) {
            resultSet.next();
            return resultSet.getInt("account_number");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //�������� ���� � ���������� ������
    protected void showListBankAccountsInfo() {
        System.out.println("Client: " + user.getClient().getLastName()
                + " " + user.getClient().getFirstName() + " ID: " + user.getClient().getID());
        user.getBankAccounts().clear();
        readAllUserBankAccounts(user.getClient().getID());
        user.getBankAccounts().stream().forEach(it -> {
            System.out.printf(it.showInfoBalance());
        });
        System.out.println();
    }

    //�������� ������ �� ������������ (�����)
    protected BigDecimal getInputAmount() {
        System.out.println("Please, enter the amount");
        System.out.printf("For example: 107,50\n");
        if (!SCANNER.hasNextBigDecimal()) {
            System.out.println("INCORRECT INPUT! Enter a number");
            SCANNER.nextLine();
            menuStatus = TRANSFER_MONEY_BY_CLEVER_BANK;
            return null;
        }
        BigDecimal amount = new BigDecimal(SCANNER.nextInt());

        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            System.out.println("INCORRECT INPUT! Amount = 0");
            menuStatus = TRANSFER_MONEY_BY_CLEVER_BANK;
            return null;
        }
        return amount;
    }


}
