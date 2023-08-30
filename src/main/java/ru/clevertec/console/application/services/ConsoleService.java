package ru.clevertec.console.application.services;

import ru.clevertec.console.application.Main;
import ru.clevertec.console.application.enums.Menu;
import ru.clevertec.console.application.menu_run.AuthorisedMenu;
import ru.clevertec.console.application.menu_run.MainMenu;
import ru.clevertec.console.application.model.BankAccount;
import ru.clevertec.console.application.model.Client;
import ru.clevertec.console.application.model.User;
import ru.clevertec.console.application.utils.SqlQuery;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

import static ru.clevertec.console.application.enums.Menu.*;


public class ConsoleService {
    private Scanner SCANNER = new Scanner(System.in);
    private Menu menuStatus;
    private User user = new User();
    AuthorisedMenu authorisedMenu = new AuthorisedMenu(user, menuStatus);
    MainMenu mainMenu = new MainMenu(user, menuStatus);


    //стартануть приложение
    public void startApp() {
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

    /*//получить нужный банковский счет пользователя по номеру счета
    private BankAccount getBankAccountByAccountNumber(int accountNumber) {
        BankAccount bankAccount = user.getBankAccounts()
                .stream()
                .filter(it -> it.getAccountNumber() == accountNumber)
                .findFirst().get();
        return bankAccount;
    }

    //получить все счета пользователя
    private void readAllUserBankAccounts(int userId) {
        try (ResultSet resultSet = DBService.getQueryResult(SqlQuery.SELECT_USER_ALL_BANK_ACCOUNTS + userId)) {
            while (resultSet.next()) {
                int accountNumber = resultSet.getInt("account_number");
                BigDecimal balance = resultSet.getBigDecimal("balance");
                Date date = resultSet.getDate("date");
                BankAccount bankAccount = new BankAccount(accountNumber, balance, user.getBank(), user.getClient(), date);
                user.getBankAccounts().add(bankAccount);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    //очистка (сдвиг) содержимого консоли вверх
    private void clearConsole() {
        System.out.printf("\n\n\n\n\n\n\n\n\n\n\n");
    }

    //проверка, есть ли клиент с таким id в банке
    private boolean isClientOfBank(int id) {
        return isEmptyInDataBaseById(id, SqlQuery.IS_CLIENT_OF_BANK);
    }

    //проверка, существует ли такой номер счёта в базе данных
    private boolean isBankAccountOfBank(int accountNumber) {
        return isEmptyInDataBaseById(accountNumber, SqlQuery.IS_BANK_ACCOUNT_OF_OTHER_BANK);
    }

    //проверка, принадлежит ли такой номер счёта банку Clever-Bank
    private boolean isBankAccountOfCleverBank(int accountNumber) {
        return isEmptyInDataBaseById(accountNumber, SqlQuery.IS_BANK_ACCOUNT_OF_CLEVER_BANK);
    }

    //принадлежит ли номер счета пользователю
    private boolean isBankAccountFromUser(int accountNumber) {
        if (user.getBankAccounts().stream()
                .filter(it -> it.getAccountNumber() == accountNumber)
                .findFirst().isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    //есть ли что-то в базе данных по такому Id
    private boolean isEmptyInDataBaseById(int id, String query) {
        try (ResultSet resultSet = DBService.getQueryResult(query + id)) {
            return (resultSet.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //проверка ввода пользователя (должен вводить только числа)
    private boolean isValidInput() {
        if (SCANNER.hasNextInt()) {
            return true;
        } else {
            System.out.println("Invalid input, try again...");
            return false;
        }
    }

    //проверка имени на валидность введенного имени
    private boolean isValidName(String name) {
        return name.matches("[a-zA-Z]+");
    }

    //получить ID нового клиента в банке
    private Integer getNewClientId(String lastName, String firstName) {
        try (ResultSet resultSet = DBService
                .getQueryResult(String
                        .format(SqlQuery.SELECT_NEW_CLIENT_ID, lastName, firstName))) {
            resultSet.next();
            return resultSet.getInt("id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //получить ID нового счета в банке
    private Integer getNewBankAccountId(int clientId) {
        try (ResultSet resultSet = DBService
                .getQueryResult(String
                        .format(SqlQuery.SELECT_NEW_BANK_ACCOUNT_ID, clientId))) {
            resultSet.next();
            return resultSet.getInt("account_number");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //получить id банка у банковского счета получателя
    private int getRecipientBankIdByAccountNumber(int accountNumber) {
        BankAccountService bankAccountService = new BankAccountService();
        return bankAccountService.readBankAccount(accountNumber).getBank().getID();
    }*/
}

