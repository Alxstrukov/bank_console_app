package ru.clevertec.console.application.services.implementation;

import lombok.NoArgsConstructor;
import ru.clevertec.console.application.services.DateCalculatable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@NoArgsConstructor
public class DayService extends Thread implements DateCalculatable, Runnable {
    private AddPercentService addPercentService;
    private boolean status = false;

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                Thread.sleep(1 * 1000);
                isAddMoneyPercent();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    //�������� ��������� ���� �������� ������ (����������, � runtime)
    public int getLastDay() {
        Calendar calendar = Calendar.getInstance();
        int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        return lastDay;
    }

    //�������� (�����) ���� ����������� ����
    public int getToday() {
        String pattern = "dd-MM-yyyy";
        String dateFormat = new SimpleDateFormat(pattern).format(new Date());
        String[] split = dateFormat.split("-");
        int today = Integer.parseInt(split[0]);
        return today;
    }

    //��������, �������� �� ������� ��������� ���� ������
    public boolean isLastDayOfMonth() {
        return (getToday() == getLastDay()) ? true : false;
    }

    //��������� ����������� �� � ���� ������ � ��������� ���� ��������
    // (���� ���, ���������� true, ��� ������� � ���, ��� ����� ������� ����� ������� �������� ��������)
    /*������� ���������, ��������� �� ���� � ���� �� ���������� � ���� ������.
     * ����� ���������, ���� ������� �� ��������� ���� ������, �� ��������� ���� status � ��������� false, ��� ��������,
     * ��� � ����� ������ ��� �������� �� �����������. ��� ������ �������� ��������� ����, ������ �������� �������� ����,
     * � ��������� false*/
    private void isAddMoneyPercent() {
        if (isLastDayOfMonth() && !status) {
            addPercentService = new AddPercentService();
            addPercentService.start();
            status = true;
        }
        if (!isLastDayOfMonth()) status = false;
    }

}
