package com.augmentis.ayp.yeutsen.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Nutdanai on 10/24/2016.
 */

public class YeutSenPreference {
    private static final String PREF_CHECK_FIRST = "PREF_CHECK_FIRST";
    private static final String PREF_TIME_LENGTH = "PREF_TIME_LENGTH";
    private static final String PREF_ALERT_NEXT_TIME = "PREF_ALERT_NEXT_TIME";
    private static final String PREF_DAY_OF_WEEK = "PREF_DAY_OF_WEEK";
    private static final String PREF_CHECK_TUTORAIL = "PREF_CHECK_TUTORAIL";
    private static final String PREF_START_TIME = "PREF_START_TIME";
    private static final String PREF_END_TIME = "PREF_END_TIME";
    private static final String PREF_BTN_ON_START = "PREF_BTN_ON_START";
    private static final String PREF_BTN_ON_STOP = "PREF_BTN_ON_STOP";
    private static final String PREF_BTN_SAVE = "PREF_BTN_SAVE";
    private static final String PREF_SOUND_URI = "PREF_SOUND_URI";
    private static final String PREF_SOUND_ON = "PREF_SOUND_ON";
    private static final String PREF_SAVE_SWITCH = "PREF_SAVE_SWITCH";
    private static final String PREF_SAVE_VIBRATE = "PREF_SAVE_VIBRATE";



    public static void setLengthTimeAlert(Context context, int timeLength) {
        mySharedPref(context).edit().putInt(PREF_TIME_LENGTH, timeLength).apply();
    }

    public static Integer getLengthTimeAlert(Context context) {
        return mySharedPref(context).getInt(PREF_TIME_LENGTH, 0);
    }

    public static void setDateToAlert(Context context, long nextTimeToAlert) {
        mySharedPref(context).edit().putLong(PREF_ALERT_NEXT_TIME, nextTimeToAlert).apply();
    }

    public static Long getDateToAlert(Context context) {
        return mySharedPref(context).getLong(PREF_ALERT_NEXT_TIME,0);
    }

    public static void setDateTimeIn(Context context, long startTime){
        mySharedPref(context).edit().putLong(PREF_START_TIME,startTime).apply();
    }
    public static Long getDateTimeIn(Context context){
        return mySharedPref(context).getLong(PREF_START_TIME,0);
    }

    public static void setDateTimeOut(Context context, long endTime){
        mySharedPref(context).edit().putLong(PREF_END_TIME,endTime).apply();
    }
    public static Long getDateTimeOut(Context context){
        return mySharedPref(context).getLong(PREF_END_TIME,0);
    }

    public static void setDayOfWeek(Context context,String dayOfWeek){
        mySharedPref(context).edit().putString(PREF_DAY_OF_WEEK,dayOfWeek).apply();
    }
    public static String getDayOfWeek(Context context){
        return mySharedPref(context).getString(PREF_DAY_OF_WEEK,null);
    }

    public static boolean isBtnOnStart(Context context){
        return mySharedPref(context).getBoolean(PREF_BTN_ON_START,false);
    }

    public static void setBtnOnStart(Context context,boolean b){
        mySharedPref(context).edit().putBoolean(PREF_BTN_ON_START,b).apply();
    }

    public static boolean isCheckButton(Context context){
        return mySharedPref(context).getBoolean(PREF_BTN_ON_STOP,false);
    }

    public static void setCheckButton(Context context, boolean b){
        mySharedPref(context).edit().putBoolean(PREF_BTN_ON_STOP,b).apply();
    }

    public static boolean isButtonSave(Context context){
        return mySharedPref(context).getBoolean(PREF_BTN_SAVE,false);
    }

    public static void setButtonSave(Context context, boolean b){
        mySharedPref(context).edit().putBoolean(PREF_BTN_SAVE,b).apply();
    }

    public static String getUriSound(Context context){
        return mySharedPref(context).getString(PREF_SOUND_URI, null);
    }

    public static void setUriSound(Context context,String soundUri){
        mySharedPref(context).edit().putString(PREF_SOUND_URI,soundUri).apply();
    }

    public static SharedPreferences mySharedPref(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }


    public static boolean isFirstEnter(Context context){
        return mySharedPref(context).getBoolean(PREF_CHECK_FIRST,false);
    }

    public static void setFirstEnter(Context context,boolean b){
        mySharedPref(context).edit().putBoolean(PREF_CHECK_FIRST,b).apply();
    }

    public static void setSwitchNotification(Context context, boolean b){
        mySharedPref(context).edit().putBoolean(PREF_SAVE_SWITCH,b).apply();

    }
    public static boolean isSwitchNotification(Context context){
        return mySharedPref(context).getBoolean(PREF_SAVE_SWITCH,false);
    }
    public static void setVibrate(Context context, boolean b){
        mySharedPref(context).edit().putBoolean(PREF_SAVE_VIBRATE,b).apply();
    }
    public static boolean isVibrate(Context context){
        return mySharedPref(context).getBoolean(PREF_SAVE_VIBRATE,true);
    }



}
