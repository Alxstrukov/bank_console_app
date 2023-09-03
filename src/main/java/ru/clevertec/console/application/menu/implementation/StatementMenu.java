package ru.clevertec.console.application.menu.implementation;

import ru.clevertec.console.application.enums.Menu;
import ru.clevertec.console.application.model.BankAccount;
import ru.clevertec.console.application.model.User;
import ru.clevertec.console.application.services.DBService;
import ru.clevertec.console.application.services.implementation.BankAccountService;
import ru.clevertec.console.application.utils.SQLquery;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static ru.clevertec.console.application.enums.Menu.MAIN;

public class StatementMenu extends AbstractMenu {
    String STATEMENT_PATH = "src/main/java/ru/clevertec/console/application/statement_money/statement_";

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
        return showStatement(startDate, endDate, accountNumber);
    }

    //����� ���� ��� �������
    private void showStatementMoneyMenu() {
        System.out.println("----------------------Clever-Bank---------------------");
        System.out.println("-------------------ACCOUNT STATEMENT------------------");
        System.out.println("                  List bank accounts           ");
        showListBankAccountsInfo();
    }

    private Menu showStatement(String startDate, String endDate, int accountNumber) {
        BigDecimal sumAddMoney = getAllAddMoney(startDate, endDate, accountNumber);
        BigDecimal sumReceiveMoney = getAllReceiveMoney(startDate, endDate, accountNumber);
        BankAccount bankAccount = getBankAccount(accountNumber);
        String newStartDate = convertDate(convertStringToDate(startDate));
        String newEndDate = convertDate(convertStringToDate(endDate));
        String timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        System.out.println("-----------------Money Statement-----------------");
        System.out.println("-------------------Clever-Bank-------------------");
        System.out.printf("Client:                | %s %s\n",
                user.getClient().getLastName(), user.getClient().getFirstName());
        System.out.printf("Bank account:          | %-4.4s\n", accountNumber);
        System.out.printf("Currency:              | BYN\n");
        System.out.printf("Creation date:         | %s\n",
                convertDate(bankAccount.getCreationDate()));
        System.out.printf("Period:                | %s -> %s\n", newStartDate, newEndDate);
        System.out.printf("Date and time creation | %s\n", timeStamp);
        System.out.printf("Balance                | %s BYN  \n", bankAccount.getBalance().setScale(2,
                RoundingMode.HALF_EVEN));
        System.out.printf("          ADD MONEY    |    RECEIVE MONEY\n");
        System.out.printf("          -------------------------------\n");
        System.out.printf("            %-5.5s BYN | -%-5.5s BYN\n\n\n", sumAddMoney, sumReceiveMoney);
        generatedStatementTXT(startDate, endDate, accountNumber, sumAddMoney, sumReceiveMoney, bankAccount.getBalance(), bankAccount.getCreationDate());
        return (menuStatus = MAIN);
    }

    private void generatedStatementTXT(String startDate, String endDate,
                                       int accountNumber, BigDecimal sumAddMoney,
                                       BigDecimal sumReceiveMoney, BigDecimal balance,
                                       Date creationdate) {
        String date = new SimpleDateFormat("dd_MM_yyyy").format(new Date());
        String newStartDate = convertDate(convertStringToDate(startDate));
        String newEndDate = convertDate(convertStringToDate(endDate));
        String timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        try (PrintWriter printWriter = new PrintWriter(STATEMENT_PATH + date + ".txt")) {
            printWriter.println("---------------------Money Statement---------------------");
            printWriter.println("-----------------------Clever-Bank-----------------------");
            printWriter.printf("������:                      | %s %s\n",
                    user.getClient().getLastName(), user.getClient().getFirstName());
            printWriter.printf("����:                        | %-4.4s\n", accountNumber);
            printWriter.printf("������:                      | BYN\n");
            printWriter.printf("���� ��������:               | %s\n",
                    convertDate(creationdate));
            printWriter.printf("������:                      | %s -> %s\n", newStartDate, newEndDate);
            printWriter.printf("���� � ����� ������������:   | %s\n", timeStamp);
            printWriter.printf("�������:                     | %s BYN  \n", balance.setScale(2,
                    RoundingMode.HALF_EVEN));
            printWriter.printf("                   ������    |    ����\n");
            printWriter.printf("                -------------------------------\n");
            printWriter.printf("                  %-5.5s BYN | -%-5.5s BYN\n\n\n", sumAddMoney, sumReceiveMoney);
            printWriter.println("---------------------------------------------------------");
            printWriter.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //��������������� ���� � ������ ������
    private String convertDate(Date date) {
        String dateString = new SimpleDateFormat("dd-MM-yyyy").format(date);
        return dateString;
    }

    //������������� ������ � ����
    private Date convertStringToDate(String stringDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse(stringDate);
            stringDate = dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    //�������� ����� ����������
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

    //�������� ����� ��������
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

    //�������� ���������� ����
    private BankAccount getBankAccount(int accountNumber) {
        BankAccountService bankAccountService = new BankAccountService();
        return bankAccountService.readBankAccount(accountNumber);
    }

    //��������� ����� ����� ��� �������
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
        if (!isBankAccountOfCleverBank(accountNumber)) {
            menuStatus = MAIN;
            return null;
        }
        return accountNumber;
    }

    //��������� ���� ������ ����� ��� �������
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

    //��������� ���� ����� ����� ��� �������
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


    //�������� ��������� ���� �� ����������
    private boolean isValidDate(String date) {
        return date.matches("((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])");
    }
}
