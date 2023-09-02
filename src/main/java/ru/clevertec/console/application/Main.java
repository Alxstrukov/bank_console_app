package ru.clevertec.console.application;

import ru.clevertec.console.application.services.PercentBalanceService;

import java.util.ArrayList;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
//        ConsoleService service = new ConsoleService();
//        service.startApp(DataBase.OLD);
        smth();
    }

    //начисление процентов всем  по очереди
    private static void smth(){
        ArrayList<Integer> allBankAccounts = PercentBalanceService.getAllBankAccounts();
        Iterator<Integer> iterator = allBankAccounts.iterator();
        while (iterator.hasNext()) {
            System.out.println();
            PercentBalanceService.plusBalancePercent(iterator.next());
        }
    }
}
