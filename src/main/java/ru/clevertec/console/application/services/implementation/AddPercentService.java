package ru.clevertec.console.application.services.implementation;

import ru.clevertec.console.application.services.DBService;
import ru.clevertec.console.application.services.PercentCalculatable;
import ru.clevertec.console.application.utils.PropertiesManager;
import ru.clevertec.console.application.utils.SQLquery;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;

public class AddPercentService extends Thread implements PercentCalculatable, Runnable {
    @Override
    public void run() {
        runCycleAddMoney();
    }

    //��������� ���� ���������� �����
    private void runCycleAddMoney() {
        ArrayList<Integer> allBankAccounts = getAllBankAccounts();
        Iterator<Integer> iterator = allBankAccounts.iterator();
        while (iterator.hasNext()) {
            plusBalancePercent(iterator.next());
        }
    }

    //�������� ��� ������ ���������� ������ � Clever-Bank (��������� ���� ����� ������ ���� ����� ������)
    synchronized private ArrayList<Integer> getAllBankAccounts() {
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

    //��������� ������� �� ������
    synchronized public void plusBalancePercent(int accountNumber) {
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

    //��������� ������ �� % (�������� �������� � ����� config.properties)
    public BigDecimal addPercentToBalance(BigDecimal value, BigDecimal percent) {
        BigDecimal percentAmount = value.divide(BigDecimal.valueOf(100));
        percentAmount = percentAmount.multiply(percent);
        //BigDecimal result = value.add(delimiter);
        return percentAmount;
    }

    //��������� ������� �� ����� ������������
    public BigDecimal loadPercentFromConfig() {
        String percentMoney = PropertiesManager.getConfigProperties("percentMoney");
        return new BigDecimal(percentMoney);
    }
}
