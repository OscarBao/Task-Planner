package com.android.habit.StaticObjects;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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
public class DaysManager {
    static GregorianCalendar nowCal;
    static Date currentDate;
    Context context;
    static SimpleDateFormat machineFormatter;
    static SimpleDateFormat friendlyFormatter;
    static SharedPreferences sp;

    public DaysManager(Context context) {
        this.context = context;
        nowCal = new GregorianCalendar();
        currentDate = (new GregorianCalendar()).getTime();
        sp = context.getSharedPreferences(SharedPreferenceConstants.TIMEDATA, Context.MODE_PRIVATE);
        machineFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);   // lowercase "dd"
        friendlyFormatter = new SimpleDateFormat("EEE, MMM, dd", Locale.US);
    }

    public static boolean isNextDay() {
        return (convertDateToLong(today()) > convertDateToLong(getLastVisitedDate()));
    }

    public static void markTodayVisited() {
        currentDate = today();
        markDateIntoSharedPreferences(currentDate);
    }


    public static Date convertIsolateDay(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }


    /*=========================================================================
        Public getters
     */
    public static String getTodayAsString() {
        return convertDateToFriendlyString(today());
    }
    public static long getTodayAsLong() {
        return convertDateToLong(today());
    }
    public static long getTomorrowAsLong(long todayInt) {
        return convertDateToLong(nextDayDate(convertLongToDate(todayInt)));
    }
    public static String getFriendlyDateString(long dayInt) {
        return convertDateToFriendlyString(convertLongToDate(dayInt));
    }

    /*=========================================================================
        Private methods
    */
    //Convert methods
    private static long convertDateToLong(Date date) {
        return date.getTime();
    }

    private static Date convertLongToDate(long dateNum) {
        Date date = new Date();
        date.setTime(dateNum);
        return date;
    }

    private static Date today() {
        return convertIsolateDay(getCurrentTime());
    }

    private static String convertDateToString(Date date) {
        return machineFormatter.format(date);
    }

    private static Date convertStringToDate(String dateString) {
        try {return machineFormatter.parse(dateString);}
        catch (ParseException e) {e.printStackTrace();}
        return new Date();
    }

    private static String convertDateToFriendlyString(Date date) {
        return friendlyFormatter.format(date);
    }

    //Retrieval methods
    private static Date midnightOfDay(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    private static Date nextDayDate(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, 1);
        return cal.getTime();
    }

    private static Date getCurrentTime() {
        GregorianCalendar cal = new GregorianCalendar();
        return cal.getTime();
    }

    private static Date getLastVisitedDate() {
        if(sp.contains(SharedPreferenceConstants.MARKED_DATE)) {
            return convertLongToDate(sp.getLong(SharedPreferenceConstants.MARKED_DATE, 0));
        }
        return new Date();
    }

    //Control methods
    private static void markDateIntoSharedPreferences(Date date) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(SharedPreferenceConstants.MARKED_DATE, convertDateToLong(date));
        editor.apply();
    }


}
