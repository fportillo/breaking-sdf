package br.com.dextra;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class RandomDate {

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    private Calendar cal;
    private int year;
    private int month;
    private int day;

    public RandomDate() {
        year = new Random().nextInt(2000);
        month = new Random().nextInt(11);
        day = new Random().nextInt(28);
        cal = Calendar.getInstance();
    }

    public Date get() {
        updateCal();
        return cal.getTime();
    }

    public String getFormatted() {
        updateCal();
        return new SimpleDateFormat(DATE_FORMAT).format(cal.getTime());
    }

    private void updateCal() {
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DATE, day == 0 ? 1 : day);
    }
}
