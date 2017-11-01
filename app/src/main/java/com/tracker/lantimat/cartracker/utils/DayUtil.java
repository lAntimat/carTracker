package com.tracker.lantimat.cartracker.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by GabdrakhmanovII on 27.10.2017.
 */

public class DayUtil {

    public DayUtil() {
    }

    public Date getStartOfDayInMillis(Date date){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Log.d("DayUtils", "Начало дня" + calendar.getTimeInMillis());
        date.setTime(calendar.getTimeInMillis());
        return date;
    }

    /**
     * @param date the date in the format "yyyy-MM-dd"
     */
    public Date getEndOfDayInMillis(Date date) {
        // Add one day's time to the beginning of the day.
        // 24 hours * 60 minutes * 60 seconds * 1000 milliseconds = 1 day
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        date.setTime(calendar.getTimeInMillis() + (24 * 60 * 60 * 1000) );
        return date;
    }

}
