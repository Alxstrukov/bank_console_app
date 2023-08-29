package ru.clevertec.console.application;

import ru.clevertec.console.application.services.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
//        BankAccountService bas = new BankAccountService();
//        BankAccount account = bas.readBankAccount(1030);
//        System.out.println(account);
//        OperationService os = new OperationService();
//        os.receiveMoney(account, BigDecimal.valueOf(140.30));
        ConsoleService consoleService = new ConsoleService();
        consoleService.startApp();
        System.out.println("main===main===main===main===main===main===main===main===main");



    }


}
