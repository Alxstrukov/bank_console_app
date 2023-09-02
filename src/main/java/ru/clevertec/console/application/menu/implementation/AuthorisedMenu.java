package ru.clevertec.console.application.menu.implementation;

import ru.clevertec.console.application.enums.Menu;
import ru.clevertec.console.application.model.BankAccount;
import ru.clevertec.console.application.model.Client;
import ru.clevertec.console.application.model.User;
import ru.clevertec.console.application.services.implementation.BankAccountService;
import ru.clevertec.console.application.services.implementation.ClientService;
import ru.clevertec.console.application.utils.SQLquery;

import static ru.clevertec.console.application.enums.Menu.*;

public class AuthorisedMenu extends AbstractMenu {

    public AuthorisedMenu(User user, Menu status) {
        super(user, status);
    }

    @Override
    //��������� ���� �����������
    public Menu run() {
        showAuthorisedMenu();
        selectAuthorisedMenu();
        return menuStatus;
    }

    //�������� ���������� ���� ����������
    private void showAuthorisedMenu() {
        System.out.println("-------------------Clever-Bank--------------------");
        System.out.println("-----------------AUTHORISED MENU--------------------");
        System.out.println("1. Log in to Clever-Bank by ID");
        System.out.println("2. Become a new client at CleverBank");
        System.out.println("0. Exit the application");
    }

    //������� ������� � ��������������� ����
    private void selectAuthorisedMenu() {
        if (SCANNER.hasNextInt()) {
            Integer value = SCANNER.nextInt();
            switch (value) {
                case 1: {
                    logInById();
                }
                break;
                case 2: {
                    registerNewClient();
                }
                break;
                case 0: {
                    exitApp();
                }
                break;
                default: {
                    menuStatus = isValidInput(AUTHORIZED);
                }
                break;
            }
        } else {
            menuStatus = isValidInput(AUTHORIZED);
        }
    }

    //����������� �� id (case: 1)
    private Menu logInById() {
        System.out.println("Enter your unique ID number");
        if (SCANNER.hasNextInt()) {
            int userId = SCANNER.nextInt();
            if (isClientOfBank(userId)) {
                identifyClientById(userId);
                uploadClientBankAccounts(userId);
                greet();
            } else {
                System.out.println("Is not a client of Clever-Bank:(");
                menuStatus = AUTHORIZED;
            }
        } else {
            menuStatus = isValidInput(AUTHORIZED);
        }
        return menuStatus;
    }

    //������������� ������������ � ����������
    private void identifyClientById(int id) {
        ClientService clientService = new ClientService();
        Client client = clientService.readClient(id);
        user.setClient(client);
    }

    //��������� ���������� ����� ������ ������������
    private void uploadClientBankAccounts(int id) {
        readAllUserBankAccounts(id);
    }

    //�������������� ������������
    private void greet() {
        System.out.println("--------Welcome, " + user.getClient().getLastName()
                + " " + user.getClient().getFirstName() + "--------");
        menuStatus = MAIN;
    }

    //���������������� ������ ������� (case: 2)
    private Menu registerNewClient() {
        String firstName = readFirstName();
        if (firstName == null) {
            return menuStatus;
        }
        String lastName = readLastName();
        if (lastName == null) {
            return menuStatus;
        }
        identifyNewClient(lastName, firstName);
        identifyNewClientBankAccount();
        greet();
        return menuStatus;
    }

    //��������� ��������� ��� ������ ������������
    private String readFirstName() {
        System.out.println("Enter your first name");
        SCANNER.nextLine();
        String firstName = SCANNER.nextLine();
        if (!isValidName(firstName)) {
            System.out.println("Incorrect first name");
            menuStatus = AUTHORIZED;
            return null;
        }
        return firstName;
    }

    //��������� ��������� ������� ������ ������������
    private String readLastName() {
        System.out.println("Enter your last name");
        //SCANNER.nextLine();
        String lastName = SCANNER.nextLine();
        if (!isValidName(lastName)) {
            System.out.println("Incorrect last name");
            menuStatus = AUTHORIZED;
            return null;
        }
        return lastName;
    }

    //������������� ������ ���������� ������������ � ����������
    private void identifyNewClient(String lastName, String firstName) {
        ClientService clientService = new ClientService();
        clientService.createClient(lastName, firstName);
        int clientId = getNewClientId(lastName, firstName);
        user.setClient(clientService.readClient(clientId));
    }

    //��������� � ���������� ���� ������ ������������
    private void identifyNewClientBankAccount() {
        //������� ������� ���� � Clever-Bank
        BankAccountService bankAccountService = new BankAccountService();
        bankAccountService.createBankAccount(SQLquery.CLEVER_BANK_ID, user.getClient().getID());
        //�������� ��������������� ����� ����� ��� �����
        int bankAccountNumber = getNewBankAccountId(user.getClient().getID());
        //�������������� ���� ������ ������������ � ����������
        BankAccount bankAccount = bankAccountService.readBankAccount(bankAccountNumber);
        user.getBankAccounts().add(bankAccount);
        menuStatus = MAIN;
    }

    private Menu exitApp() {
        clearConsole();
        System.out.println("Goodbye, Your Clever-Bank");
        menuStatus = EXIT;
        return menuStatus;

    }
}
