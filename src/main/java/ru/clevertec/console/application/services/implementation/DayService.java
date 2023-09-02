package ru.clevertec.console.application.services.implementation;

import lombok.NoArgsConstructor;
import ru.clevertec.console.application.services.DateCalculatable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@NoArgsConstructor
public class DayService extends Thread implements DateCalculatable, Runnable {
    private AddPercentService addPercentService = new AddPercentService();

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                Thread.sleep(30 * 1000);
                if (isLastDayOfMonth()) addPercentService.start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    //получить последний день текущего месяца (настоящего, в runtime)
    public int getLastDay() {
        Calendar calendar = Calendar.getInstance();
        int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        return lastDay;
    }

    //получить (число) день сегодняшней даты
    public int getToday() {
        String pattern = "dd-MM-yyyy";
        String dateFormat = new SimpleDateFormat(pattern).format(new Date());
        String[] split = dateFormat.split("-");
        int today = Integer.parseInt(split[0]);
        return today;
    }

    //проверка, является ли сегодня последним днем месяца
    public boolean isLastDayOfMonth() {
        return (getToday() == getLastDay()) ? true : false;
    }
}
