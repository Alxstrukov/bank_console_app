package ru.clevertec.console.application.services;

import ru.clevertec.console.application.enums.Menu;
import ru.clevertec.console.application.model.BankAccount;
import ru.clevertec.console.application.model.Client;
import ru.clevertec.console.application.model.User;
import ru.clevertec.console.application.utils.SqlQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static ru.clevertec.console.application.enums.Menu.*;

public class ConsoleService {
    private User user = new User();
    private final Scanner SCANNER = new Scanner(System.in);
    private Menu menuStatus;

    //стартануть приложение
    public void startApp() {
        menuStatus = AUTHORIZED;
        while (menuStatus != Menu.EXIT) {
            switch (menuStatus) {
                case AUTHORIZED: {
                    enterAuthorisedMenu();
                }
                break;
                case MAIN: {
                    enterMainMenu();
                }
                break;
                case VIEW_BALANCE: {
                    enterViewBalanceMenu();
                }
                break;
                default: {

                }
                break;
            }
        }
    }


    //показать меню авторизации
    private void showAuthorisedMenu() {
        //clearConsole();
        System.out.println("-------------------Clever-Bank--------------------");
        System.out.println("1. Log in to Clever-Bank by ID");
        System.out.println("2. Become a new client at CleverBank");
        System.out.println("0. Exit the application");
        System.out.println("***    Enter the item number and press Enter    ***");
    }

    //войти в меню авторизации
    private Menu enterAuthorisedMenu() {
        showAuthorisedMenu();
        if (SCANNER.hasNextInt()) {
            Integer value = SCANNER.nextInt();
            switch (value) {
                case 1: {
                    System.out.println("Enter your unique ID number");
                    if (SCANNER.hasNextInt()) {
                        int userId = SCANNER.nextInt();
                        if (isClientOfBank(userId)) {
                            ClientService clientService = new ClientService();
                            Client client = clientService.readClient(userId);
                            user.setClient(client);
                            System.out.println("--------Welcome, " + user.getClient().getLastName()
                                    + " " + user.getClient().getFirstName() + "--------");
                            menuStatus = MAIN;
                        } else {
                            System.out.println("The client with this ID was not found:(");
                            menuStatus = AUTHORIZED;
                        }
                    } else {
                        System.out.println("INCORRECT INPUT! Enter a number");
                        SCANNER.nextLine();
                        menuStatus = AUTHORIZED;
                        break;
                    }
                }
                break;
                case 2: {
                    System.out.println("Enter your first name");
                    SCANNER.nextLine();
                    String clientFirstName = SCANNER.nextLine();
                    if (!isValidName(clientFirstName)) {
                        System.out.println("Incorrect first name");
                        menuStatus = AUTHORIZED;
                        break;
                    }
                    System.out.println("Enter your last name");
                    String clientLastName = SCANNER.nextLine();
                    if (!isValidName(clientLastName)) {
                        System.out.println("Incorrect last name");
                        menuStatus = AUTHORIZED;
                        break;
                    }
                    //создаем в базе нового клиента
                    ClientService clientService = new ClientService();
                    clientService.createClient(clientLastName, clientFirstName);
                    //получаем сгенерированный базой его ID
                    int clientId = getNewClientId(clientLastName, clientFirstName);
                    //и иденфицируем нашего пользователя в приложении
                    user.setClient(clientService.readClient(clientId));

                    //заводим клиенту счёт в Clever-Bank
                    BankAccountService bankAccountService = new BankAccountService();
                    bankAccountService.createBankAccount(119, user.getClient().getID());
                    //получаем сгенерированный базой номер его счёта
                    int bankAccountNumber = getNewBankAccountId(clientId);
                    //идентифицируем счёт нашего пользователя в приложении
                    user.setBankAccount(bankAccountService.readBankAccount(bankAccountNumber));
                    System.out.println("-------- Welcome," + user.getClient().getLastName()
                            + " " + user.getClient().getFirstName() + "--------");
                    menuStatus = MAIN;
                }
                break;
                case 0: {
                    clearConsole();
                    System.out.println("Goodbye, Your Clever-Bank");
                    menuStatus = EXIT;
                }
                break;
                default: {
                    SCANNER.hasNextLine();
                    System.out.println("Incorrect menu selection");
                    if (SCANNER.hasNextLine()) {
                        menuStatus = AUTHORIZED;
                    }
                }
                break;
            }
        } else {
            System.out.println("INCORRECT INPUT! Enter a number");
            SCANNER.nextLine();
            menuStatus = AUTHORIZED;
        }
        return menuStatus;
    }

    //показать главное меню
    private void showMainMenu() {
        System.out.println("-------------------Clever-Bank--------------------");
        System.out.println("1. View account balance");
        System.out.println("2. Add money in bank account");
        System.out.println("3. Receive money in bank account");
        System.out.println("4. Transfer money to a Clever-Bank client");
        System.out.println("5. Transfer money to a client of another bank");
        System.out.println("6. Account statement");
        System.out.println("0. Exit from account");
        System.out.println("***    Enter the item number and press Enter    ***");
    }

    //войти в главное меню
    private Menu enterMainMenu() {
        showMainMenu();
        switch (SCANNER.nextInt()) {
            case 0: {
                menuStatus = AUTHORIZED;
                user = new User();
            }
            break;
            case 1: {
                menuStatus = VIEW_BALANCE;
            }
            break;
            case 2: {
                menuStatus = AUTHORIZED;
            }
            break;
            case 3: {
                menuStatus = AUTHORIZED;
            }
            break;
            case 4: {
                menuStatus = AUTHORIZED;
            }
            break;
            case 5: {
                menuStatus = AUTHORIZED;
            }
            break;
            case 6: {
                menuStatus = AUTHORIZED;
            }
            break;
            default: {

            }
            break;
        }
        return menuStatus;
    }

    private Menu enterViewBalanceMenu() {
        if (user.getBankAccount() == null) {
            System.out.println("-------Account number not selected-------");
            System.out.println("1. View the list of account numbers");
            System.out.println("2. Enter the account number");
            System.out.println("0. Go back");
            System.out.println("***    Enter the item number and press Enter    ***");

            if (SCANNER.hasNextInt()) {
                switch (SCANNER.nextInt()) {
                    case 1: {

                    }
                    break;
                    case 2: {
                        System.out.println("Enter the number bank account");
                        if (SCANNER.hasNextInt()) {
                            int bankAccountNumber = SCANNER.nextInt();
                            if (isBankAccountOfBank(bankAccountNumber)) {
                                BankAccountService bankAccountService = new BankAccountService();
                                BankAccount bankAccount = bankAccountService.readBankAccount(bankAccountNumber);
                                user.setBankAccount(bankAccount);
                                menuStatus = VIEW_BALANCE;
                            } else {
                                System.out.println("Bank account not found");
                                menuStatus = VIEW_BALANCE;
                            }
                        } else {
                            SCANNER.nextLine();
                            SCANNER.nextLine();
                            System.out.println("INCORRECT INPUT!");
                        }
                    }
                    break;
                    case 0: {
                        menuStatus = MAIN;
                    }
                    break;
                    default: {
                        System.out.println("Please, enter the number 1, 2 or 0");
                        menuStatus = VIEW_BALANCE;
                    }
                    break;
                }
            } else {
                SCANNER.nextLine();
                System.out.println("INCORRECT INPUT! Enter the number");
                menuStatus = VIEW_BALANCE;
            }
        } else {
            System.out.printf("Balance:         %.2f BYN\n", user.getBankAccount().getBalance());
            System.out.println("0. Go back");
            System.out.println("***    Enter the item number and press Enter    ***");
            if (SCANNER.hasNextInt()) {
                if (SCANNER.nextInt() == 0) {
                    menuStatus = MAIN;
                } else {
                    SCANNER.nextLine();
                    System.out.println("INCORRECT INPUT!");
                }
            }else{
                SCANNER.nextLine();
                clearConsole();
                System.out.println("INCORRECT INPUT! Enter the number '0' \n\n");
                menuStatus = VIEW_BALANCE;
            }
        }
        return menuStatus;
    }


    //=================================ПРОЧИЕ МЕТОДЫ=============================================//


    //очистка (сдвиг) содержимого консоли вверх
    private void clearConsole() {
        System.out.printf("\n\n\n\n\n\n\n\n\n\n\n");
    }

    //проверка, есть ли клиент с таким id в банке
    private boolean isClientOfBank(int id) {
        return isEmptyInDataBaseById(id, SqlQuery.IS_CLIENT_OF_BANK);
    }

    //проверка, есть ли такой номер счета в банке
    private boolean isBankAccountOfBank(int accountNumber) {
        return isEmptyInDataBaseById(accountNumber, SqlQuery.IS_BANK_ACCOUNT_OF_BANK);
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
}
