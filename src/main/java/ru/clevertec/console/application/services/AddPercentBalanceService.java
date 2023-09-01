package ru.clevertec.console.application.services;

import lombok.NoArgsConstructor;
import ru.clevertec.console.application.model.BankAccount;
import ru.clevertec.console.application.utils.SQLquery;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@NoArgsConstructor
public class AddPercentBalanceService extends Thread {


    public void run() {

    }

    //�������� ��� ������ ���������� ������ � Clever-Bank (��������� ���� ����� ������ ���� ����� ������)
    public static ArrayList<Integer> getAllBankAccounts() {
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
    public static void plusBalancePercent(int accountNumber) {
        try (ResultSet resultSet = DBService.getQueryResult("SELECT balance " +
                "FROM bank_accounts WHERE account_number = " + accountNumber)) {
            resultSet.next();
            BigDecimal oldBalance = resultSet.getBigDecimal("balance");
            BigDecimal amount = addPercent(oldBalance, BigDecimal.valueOf(1));
            OperationService operationService = new OperationService();
            operationService.addBalanceByPercentage(accountNumber, amount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //=======================���������� ��������=========================\\
    //�������� ������ �� 1%

    public static BigDecimal addPercent(BigDecimal value, BigDecimal percent) {
        BigDecimal percentAmount = value.divide(BigDecimal.valueOf(100));
        percentAmount = percentAmount.multiply(percent);
        //BigDecimal result = value.add(delimiter);
        return percentAmount;
    }

    //=======================���������� ��������=========================\\


    //=========================��������=================================\\
    //�������� ��������� ���� �������� ������ (����������, � runtime)

    public static int getLastDay() {
        Calendar calendar = Calendar.getInstance();
        int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        return lastDay;
    }
    //�������� (�����) ���� �� ����������� ����

    public static int getToday() {
        String pattern = "dd-MM-yyyy";
        String dateFormat = new SimpleDateFormat(pattern).format(new Date());
        String[] split = dateFormat.split("-");
        int today = Integer.parseInt(split[0]);
        return today;
    }
    //��������, �������� �� ������� ��������� ���� ������

    public static boolean isLastDayOfMonth() {
        return (getToday() == getLastDay()) ? true : false;
    }

    //=========================��������=================================\\
}
