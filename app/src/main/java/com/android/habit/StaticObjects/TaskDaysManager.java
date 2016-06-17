package com.android.habit.StaticObjects;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.habit.Activities.MainActivity;
import com.android.habit.Interfaces.SharedPreferenceConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Oscar_Local on 6/15/2016.
 */
public class TaskDaysManager {
    static GregorianCalendar calendar;
    Context context;
    static SimpleDateFormat formatter;
    static SharedPreferences sp;

    public TaskDaysManager(Context context) {
        this.context = context;
        calendar = new GregorianCalendar();
        sp = context.getSharedPreferences(SharedPreferenceConstants.TIMEDATA, Context.MODE_PRIVATE);
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US);   // lowercase "dd"
    }

    public static boolean isPastPreviousSetMidnight() {
        setCalendarToCurrentTime();
        return (calendar.compareTo(getLastMarkedDate()) == 1);
    }

    public static void markNextMidnight() {
        setCalendarToNextMidnight();
        markDateIntoSharedPreferences(calendar);
    }


    /*
    --------------->Private methods
     */
    private static void setCalendarToNextMidnight() {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
    }

    private static void setCalendarToCurrentTime() {
        calendar = new GregorianCalendar();
    }

    private static void markDateIntoSharedPreferences(GregorianCalendar cal) {
        SharedPreferences.Editor editor = sp.edit();
        Date date = cal.getTime();

        String dateString = formatter.format(date);
        editor.putString(SharedPreferenceConstants.MARKED_DATE, dateString);
        editor.apply();
    }

    private static GregorianCalendar stringToCalendar(String str) {
        Log.i("Cal string in SP: ", str);
        try {
            Date d = formatter.parse(str);
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            return (GregorianCalendar) cal;
        }
        catch (ParseException parseE) {
            parseE.printStackTrace();
        }
        return null;
    }

    private static GregorianCalendar getLastMarkedDate() {
        if(sp.contains(SharedPreferenceConstants.MARKED_DATE)) {
            return stringToCalendar(sp.getString(SharedPreferenceConstants.MARKED_DATE, ""));
        }
        return new GregorianCalendar();
    }

}
