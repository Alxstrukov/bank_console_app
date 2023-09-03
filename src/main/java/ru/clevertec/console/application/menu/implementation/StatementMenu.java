package ru.clevertec.console.application.menu.implementation;

import ru.clevertec.console.application.enums.Menu;
import ru.clevertec.console.application.model.BankAccount;
import ru.clevertec.console.application.model.User;
import ru.clevertec.console.application.services.DBService;
import ru.clevertec.console.application.services.implementation.BankAccountService;
import ru.clevertec.console.application.utils.SQLquery;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static ru.clevertec.console.application.enums.Menu.MAIN;
import static ru.clevertec.console.application.enums.Menu.TRANSFER_MONEY_BY_CLEVER_BANK;

public class StatementMenu extends AbstractMenu {
    protected StatementMenu(User user, Menu status) {
        super(user, status);
    }

    @Override
    public Menu run() {
        showStatementMoneyMenu();
        Integer accountNumber = getInputAccountNumber();
        if (accountNumber == null || accountNumber == 0) return menuStatus;
        String startDate = getInputStartDate();
        if (startDate == null) {
            System.out.println("INCORRECT INPUT DATE!");
            return menuStatus;
        }
        String endDate = getInputEndDate();
        if (endDate == null) {
            System.out.println("INCORRECT INPUT DATE!");
            return menuStatus;
        }
        return generatedStatement(startDate, endDate, accountNumber);
    }

    //вывод меню для выписки
    private void showStatementMoneyMenu() {
        System.out.println("----------------------Clever-Bank---------------------");
        System.out.println("-------------------ACCOUNT STATEMENT------------------");
        System.out.println("                  List bank accounts           ");
        showListBankAccountsInfo();
    }

    private Menu generatedStatement(String startDate, String endDate, int accountNumber) {
        BigDecimal summAddMoney = getAllAddMoney(startDate, endDate, accountNumber);
        BigDecimal summReceiveMoney = getAllReceiveMoney(startDate, endDate, accountNumber);
        BankAccount bankAccount = getBankAccount(accountNumber);
        StringBuilder statement = new StringBuilder("Statement");
        statement.append("Start Date: ").append(startDate);
        statement.append("End Date: ").append(endDate);
        statement.append("Sum add money= ").append(summAddMoney);
        statement.append("Sum receive money= ").append(summReceiveMoney);
        statement.append(bankAccount);
        System.out.println(statement);
        return (menuStatus = MAIN);
    }

    //получить сумму пополнений
    public BigDecimal getAllAddMoney(String startDate, String endDate, int accountNumber) {
        BigDecimal sum = BigDecimal.ZERO;
        try (ResultSet resultSet = DBService.getQueryResult(String.format(SQLquery.GET_ALL_ADD_MONEY, accountNumber, startDate, endDate))) {
            resultSet.next();
            sum = resultSet.getBigDecimal("sum");
            if (sum == null) sum = BigDecimal.ZERO;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sum;
    }

    //получить сумму расходов
    private BigDecimal getAllReceiveMoney(String startDate, String endDate, int accountNumber) {
        BigDecimal sum = BigDecimal.ZERO;
        try (ResultSet resultSet = DBService.getQueryResult(String.format(SQLquery.GET_ALL_RECEIVE_MONEY, accountNumber, startDate, endDate))) {
            resultSet.next();
            sum = resultSet.getBigDecimal("sum");
            if (sum == null) sum = BigDecimal.ZERO;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sum;
    }

    //получить банковский счет
    private BankAccount getBankAccount(int accountNumber) {
        BankAccountService bankAccountService = new BankAccountService();
        return bankAccountService.readBankAccount(accountNumber);
    }

    //запросить номер счета для выписки
    private Integer getInputAccountNumber() {
        System.out.printf("Please select your bank account number or enter '0' to return to the main menu\n");
        System.out.printf("For example: 1045\n");
        if (!SCANNER.hasNextInt()) {
            System.out.println("INCORRECT INPUT! Enter a number");
            SCANNER.nextLine();
            menuStatus = MAIN;
            return null;
        }
        int accountNumber = SCANNER.nextInt();
        if (!isBankAccountFromUser(accountNumber)) {
            menuStatus = MAIN;
            return null;
        }
        return accountNumber;
    }

    //запросить дату начала срока для выписки
    private String getInputStartDate() {
        SCANNER.nextLine();
        System.out.printf("Please, enter the start of the date\n");
        System.out.printf("For example: 2023-11-10\n");
        if (!SCANNER.hasNextLine()) {
            isValidInput(MAIN);
            return null;
        }
        String startDate = SCANNER.nextLine();
        if (!isValidDate(startDate)) {
            return null;
        }
        return startDate;
    }

    //запросить дату конца срока для выписки
    private String getInputEndDate() {
        System.out.printf("Please, enter the end of the date\n");
        System.out.printf("For example: 2023-11-10\n");
        if (!SCANNER.hasNextLine()) {
            isValidInput(MAIN);
            return null;
        }
        String endDate = SCANNER.nextLine();
        if (!isValidDate(endDate)) {
            return null;
        }
        return endDate;
    }


    //проверка введенной даты на валидность
    private boolean isValidDate(String date) {
        return date.matches("((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])");
    }
}
