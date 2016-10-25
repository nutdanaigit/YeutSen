package com.augmentis.ayp.yeutsen.fragment.main.firststretch;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;

import com.augmentis.ayp.yeutsen.preference.YeutSenPreference;
import com.augmentis.ayp.yeutsen.service.YeutSenService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Nutdanai on 10/24/2016.
 */

public class CheckTime {
    private static final String TAG = "CheckTime";
    private static final int REQUEST_CODE_INT_ONE = 1;
    private static final int REQUEST_CODE_INT_TWO = 2;
    private static CheckTime instance;
    private String stringBufDayOfWeek;
    private String[] stringIsDayOfWeek;
    private List<Boolean> isDayOfWeek;
    private Context context;
    private int tempLengthOfTime;
    private int seconds;
    private int hourEdit;
    private int minuteEdit;
    private int secondEdit;
    private int hourTimeIn, minuteTimeIn, secondTimeIn;
    private int hourTimeOut, minuteTimeOut, secondTimeOut;
    private Date timeCurrent, timeIn, timeOut;
    private Calendar calCurrent, calTimeIn, calTimeOut;

    public CheckTime(Context ctx) {
        this.context = ctx.getApplicationContext();
    }

    public static CheckTime newInstance(Context context) {
        if (instance == null) {
            instance = new CheckTime(context);
        }
        return instance;
    }

    public void setDayOfWeek() {
        stringBufDayOfWeek = YeutSenPreference.getDayOfWeek(context);
        if (isDayOfWeek == null) {
            isDayOfWeek = new ArrayList<>();
        } else {
            isDayOfWeek.clear();
        }
        stringIsDayOfWeek = stringBufDayOfWeek.split(",");
        for (String s : stringIsDayOfWeek) {
            if (s.equals("true")) {
                isDayOfWeek.add(true);
            } else {
                isDayOfWeek.add(false);
            }
        }

        Log.d(TAG, "getLoopTime: List " + isDayOfWeek);
    }

    public boolean isDayOfWeekFunction(int i) {
        Log.d(TAG, "isDayOfWeekFunction: " + i + " list: " + isDayOfWeek);
        if(isDayOfWeek == null){
            setDayOfWeek();
        }
        return isDayOfWeek.get(i - 1);
    }

    public int getDayOfWeekFuture(int i) {
        for (int temp = i; temp <= isDayOfWeek.size(); temp++) {
            Log.d(TAG, "getDayOfWeekFuture:one " +i +" "+ temp + " size list "+ isDayOfWeek.size());
            if ( i < temp) {
                if (isDayOfWeek.get(temp-1)) return temp;
            }
        }
        for(int temp2 = 1; temp2 <= i ; temp2++){
            if(isDayOfWeek.get(temp2-1)) {
                Log.d(TAG, "getDayOfWeekFuture:two "+ i+ " " + temp2 + " check "+isDayOfWeek.get(temp2-1));
                return temp2;
            }
        }
        Log.d(TAG, "getDayOfWeekFuture: 3");
        return i;
    }

    public void setLoopTime(){
        hourTimeIn = Integer.parseInt(DateFormat.format("HH", YeutSenPreference.getDateTimeIn(context)).toString());
        minuteTimeIn = Integer.parseInt(DateFormat.format("mm", YeutSenPreference.getDateTimeIn(context)).toString());
        secondTimeIn = Integer.parseInt(DateFormat.format("ss", YeutSenPreference.getDateTimeIn(context)).toString());

        hourTimeOut = Integer.parseInt(DateFormat.format("HH", YeutSenPreference.getDateTimeOut(context)).toString());
        minuteTimeOut = Integer.parseInt(DateFormat.format("mm", YeutSenPreference.getDateTimeOut(context)).toString());
        secondTimeOut = Integer.parseInt(DateFormat.format("ss", YeutSenPreference.getDateTimeOut(context)).toString());

        calCurrent = Calendar.getInstance();

        calTimeIn = Calendar.getInstance();
        calTimeIn.set(calTimeIn.get(Calendar.YEAR), calTimeIn.get(Calendar.MONTH), calTimeIn.get(Calendar.DATE), hourTimeIn, minuteTimeIn, secondTimeIn);

        calTimeOut = Calendar.getInstance();
        calTimeOut.set(calTimeOut.get(Calendar.YEAR), calTimeOut.get(Calendar.MONTH), calTimeOut.get(Calendar.DATE), hourTimeOut, minuteTimeOut, secondTimeOut);

    }

    public void getLoopTime() {
        setLoopTime();
        timeCurrent = calCurrent.getTime();
        timeIn = calTimeIn.getTime();
        timeOut = calTimeOut.getTime();
        if (isDayOfWeekFunction(Calendar.getInstance().get(Calendar.DAY_OF_WEEK))) {
            if (timeCurrent.after(timeIn) || timeCurrent.equals(timeIn)) {
                Log.d(TAG, "onCreate: After or Equals " + timeCurrent.after(timeIn) + " , " + timeCurrent.equals(timeIn));
                Log.d(TAG, "getLoopTime: TimeIn " + timeIn);
                Log.d(TAG, "getLoopTime: TimeOut " + timeOut);
                Log.d(TAG, "getLoopTime: TimeCurrent " + timeCurrent);
                checkTimeAfterTimeOne();
            } else {
                Log.d(TAG, "onCreate: Before " + timeCurrent.before(timeIn));
                YeutSenPreference.setCheckButton(context, false);
            }
        } else {
            YeutSenPreference.setCheckButton(context, false);
            setTimeToAlertNextDay();
        }
    }

    public void checkTimeAfterTimeOne() {

        if (timeCurrent.before(timeOut) || timeCurrent.equals(timeOut)) {
            Log.d(TAG, "checkTimeAfterTimeOne: Before or Equals " + timeCurrent.before(timeOut) + " , " + timeCurrent.equals(timeOut));

            if (YeutSenPreference.getDateToAlert(context) > YeutSenPreference.getDateTimeOut(context)) {
                YeutSenPreference.setCheckButton(context, false);
            } else {
                Log.d(TAG, "checkTimeAfterTimeOne: " + YeutSenPreference.isBtnOnStart(context));
                if (!YeutSenPreference.isBtnOnStart(context)) {
                    YeutSenPreference.setBtnOnStart(context, true);
                    YeutSenPreference.setCheckButton(context, false);
                    tempLengthOfTime = 1;
                    calTimeFinish(tempLengthOfTime);
                    YeutSenService.setServiceAlarm(context, REQUEST_CODE_INT_TWO);
                } else {
                    YeutSenPreference.setCheckButton(context, true);
                    Log.d(TAG, "checkTimeAfterTimeOne: else ");
                    // Do somethings
                }
            }
        } else {
            YeutSenPreference.setCheckButton(context, false);
            setTimeToAlertNextDay();
            // setTimeIn next times
        }
    }

    public void setTimeToAlertNextDay(){
        setLoopTime();
        int temp1 = getDayOfWeekFuture(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
        int temp2 = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        int tempUse;
        Log.d(TAG, "TimeOut temp0 "+ temp1 +"  temp2: " + temp2 );
        if(temp1>temp2){
            tempUse = temp1-temp2;
            Log.d(TAG, "TimeOut temp1 "+ temp1 +"  temp2: " + temp2 + " tempUse " + tempUse);
        }else{
            tempUse = (7 - temp2)+temp1;
            Log.d(TAG, "TimeOut temp1 "+ temp1 +"  temp2: " + temp2 + " tempUse " + tempUse);
        }
        Log.d(TAG, "TimeOut isCheck "+ isDayOfWeekFunction(temp1));

        if (isDayOfWeekFunction(temp1)) {
            calTimeIn.set(calTimeIn.get(Calendar.YEAR), calTimeIn.get(Calendar.MONTH), calTimeIn.get(Calendar.DATE) + tempUse, hourTimeIn, minuteTimeIn, secondTimeIn);
            calTimeOut.set(calTimeIn.get(Calendar.YEAR), calTimeIn.get(Calendar.MONTH), calTimeIn.get(Calendar.DATE) + tempUse, hourTimeOut, minuteTimeOut, secondTimeOut);
        }

        YeutSenPreference.setDateTimeIn(context,calTimeIn.getTime().getTime());
        YeutSenPreference.setDateTimeOut(context,calTimeOut.getTime().getTime());

        YeutSenService.setServiceAlarm(context, REQUEST_CODE_INT_ONE);
        Log.d(TAG, "checkTimeAfterTimeOne: After" + calTimeIn.getTime());
    }

    public void calTimeFinish(int i) {
        getTime(Calendar.HOUR, Calendar.MINUTE, Calendar.SECOND);
        minuteEdit = minuteEdit + i;
        calCurrent.set(Calendar.HOUR, hourEdit);
        calCurrent.set(Calendar.MINUTE, minuteEdit);
        calCurrent.set(Calendar.SECOND, secondEdit);

        YeutSenPreference.setDateToAlert(context, calCurrent.getTime().getTime());

        Log.d(TAG, "calTimeFinish: " + new Date(YeutSenPreference.getDateToAlert(context)));
    }

    public void getTime(int hour, int minute, int second) {
        hourEdit = calCurrent.get(hour);
        minuteEdit = calCurrent.get(minute);
        secondEdit = calCurrent.get(second);
    }

}
