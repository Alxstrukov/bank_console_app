package ru.clevertec.console.application.services;

import lombok.NoArgsConstructor;
import ru.clevertec.console.application.services.implementation.OperationService;
import ru.clevertec.console.application.utils.PropertiesManager;
import ru.clevertec.console.application.utils.SQLquery;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

@NoArgsConstructor
public class PercentBalanceService extends Thread implements Runnable {


    public void run() {
        //начисление процентов всем  по очереди
        while (!isInterrupted()) {
            try {
                Thread.sleep(30*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ArrayList<Integer> allBankAccounts = PercentBalanceService.getAllBankAccounts();
            Iterator<Integer> iterator = allBankAccounts.iterator();
            while (iterator.hasNext()) {
                PercentBalanceService.plusBalancePercent(iterator.next());
            }
        }
        System.out.println("PERCENT THREAD FINISHED");
    }

    //получить все номера банковских счетов в Clever-Bank (запускать буду метод только если конец месяца)
    synchronized public static ArrayList<Integer> getAllBankAccounts() {
        ArrayList<Integer> accountNumbers = new ArrayList<>();
        try (ResultSet resultSet = DBService.getQueryResult("SELECT account_number " +
                "FROM bank_accounts WHERE bank_id = " + SQLquery.CLEVER_BANK_ID)) {
            while (resultSet.next()) {
                int number = resultSet.getInt(1);
                accountNumbers.add(number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accountNumbers;
    }

    //начислить процент на баланс
    synchronized public static void plusBalancePercent(int accountNumber) {
        BigDecimal amount = null;
        try (ResultSet resultSet = DBService.getQueryResult("SELECT balance " +
                "FROM bank_accounts WHERE account_number = " + accountNumber);) {
            resultSet.next();
            BigDecimal oldBalance = resultSet.getBigDecimal("balance");
            if (oldBalance.compareTo(BigDecimal.ZERO) == 0) {
                return;
            }
            BigDecimal percent = loadPercentFromConfig();
            amount = addPercentToBalance(oldBalance, percent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        OperationService operationService = new OperationService();
        operationService.addBalanceByPercentage(accountNumber, amount);
    }

    //начислить на баланс n%
    public static BigDecimal addPercentToBalance(BigDecimal value, BigDecimal percent) {
        BigDecimal percentAmount = value.divide(BigDecimal.valueOf(100));
        percentAmount = percentAmount.multiply(percent);
        //BigDecimal result = value.add(delimiter);
        return percentAmount;
    }

    //получить последний день текущего месяца (настоящего, в runtime)
    public static int getLastDay() {
        Calendar calendar = Calendar.getInstance();
        int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        return lastDay;
    }

    //получить (число) день сегодняшней даты
    public static int getToday() {
        String pattern = "dd-MM-yyyy";
        String dateFormat = new SimpleDateFormat(pattern).format(new Date());
        String[] split = dateFormat.split("-");
        int today = Integer.parseInt(split[0]);
        return today;
    }

    //проверка, является ли сегодня последним днем месяца
    public static boolean isLastDayOfMonth() {
        return (getToday() == getLastDay()) ? true : false;
    }

    //загрузить процент из файла конфигурации
    public static BigDecimal loadPercentFromConfig() {
        String percentMoney = PropertiesManager.getConfigProperties("percentMoney");
        return new BigDecimal(percentMoney);
    }
}
