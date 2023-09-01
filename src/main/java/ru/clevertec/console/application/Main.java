package ru.clevertec.console.application;

import ru.clevertec.console.application.services.AddPercentBalanceService;
import ru.clevertec.console.application.services.ConsoleService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
//        ConsoleService service = new ConsoleService();
//        service.startApp(true);
        ArrayList<Integer> allBankAccounts = AddPercentBalanceService.getAllBankAccounts();
        Iterator<Integer> iterator = allBankAccounts.iterator();
        while (iterator.hasNext()) {
            System.out.println();
            AddPercentBalanceService.plusBalancePercent(iterator.next());
        }
    }
}
