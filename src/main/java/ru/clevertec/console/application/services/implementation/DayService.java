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

    //проверяет начислялись ли в этом месяце в последний день проценты
    // (если нет, возвращает true, это говорит о том, что нужно вызвать поток который начислит проценты)
    /*Сначала проверяет, последний ли день и было ли начисление в этом месяце.
     * Затем проверяет, если сегодня не последний день месяца, то переводит флаг status в положение false, что означает,
     * что в новом месяце ещё проценты не начислялись. Как только наступит последний день, первая проверка переведёт флаг,
     * в положение false*/
    private void isAddMoneyPercent() {
        if (isLastDayOfMonth() && !status) {
            addPercentService = new AddPercentService();
            addPercentService.start();
            status = true;
        }
        if (!isLastDayOfMonth()) status = false;
    }

}
