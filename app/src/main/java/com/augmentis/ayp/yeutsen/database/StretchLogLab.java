package com.augmentis.ayp.yeutsen.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.augmentis.ayp.yeutsen.database.StretchLogDBSchama.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Noppharat on 10/12/2016.
 */

public class StretchLogLab {
    private static StretchLogLab instance;

    private final String TAG = "StretchLogLab";

    public static StretchLogLab getInstance(Context context) {
        if(instance == null){
            instance = new StretchLogLab(context);
        }
        return instance;
    }

    public static ContentValues getContentValues(StretchLog stretchLog){
        ContentValues contentValues = new ContentValues();
        contentValues.put(StretchLogTable.Cols.ID, stretchLog.getId().toString());
        contentValues.put(StretchLogTable.Cols.USERID, stretchLog.getUserid().toString());
        contentValues.put(StretchLogTable.Cols.STRETCHDATE, stretchLog.getDate().getTime());
        contentValues.put(StretchLogTable.Cols.STRETCHID, stretchLog.getStretchid());
        return contentValues;
    }

    private Context context;
    private SQLiteDatabase database;

    private StretchLogLab(Context context){
        this.context = context.getApplicationContext();
        StretchLogDB stretchLogDB = new StretchLogDB(context);
        database = stretchLogDB.getWritableDatabase();
    }

    public StretchLogCursorWrapper queryStretchLog(String whereCause, String[] whereArgs){
        Cursor cursor = database.query(StretchLogTable.NAME,
                null,
                whereCause,
                whereArgs,
                null,
                null,
                null);
        return new StretchLogCursorWrapper(cursor);
    }

    public List<StretchLog> getStretchLog(){
        List<StretchLog> stretchLogList = new ArrayList<>();
        StretchLogCursorWrapper cursorWrapper = queryStretchLog(null, null);
        try {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()){
                stretchLogList.add(cursorWrapper.getStretchLog());
                cursorWrapper.moveToNext();
            }
        } finally {
            cursorWrapper.close();
        }
        return stretchLogList;
    }

    public void insertStretchLog(StretchLog stretchLog){
        ContentValues contentValues = getContentValues(stretchLog);
        database.insert(StretchLogTable.NAME, null, contentValues);
    }

    public String queryTotalStretch(){
        String stringSQL = "SELECT COUNT(*) FROM stretches";
        Cursor cursor = database.rawQuery(stringSQL, null);
        cursor.moveToFirst();
        String queryResult = cursor.getString(0);
        cursor.close();
        return queryResult;
    }

    public String queryMonthlyStretch(){
        Calendar date = new GregorianCalendar();
        String stringSQL = "select count(*) from stretches where strftime('%m', datetime(stretchdate/1000, 'unixepoch')) = '"
                + String.valueOf(date.get(Calendar.MONTH) + 1)
                + "' and strftime('%Y', datetime(stretchdate/1000, 'unixepoch')) = '"
                + date.get(Calendar.YEAR)
                + "' ";
        Log.d(TAG, "queryMonthlyStretch: " + stringSQL);
        Cursor cursor = database.rawQuery(stringSQL, null);
        cursor.moveToFirst();
        String queryResult = cursor.getString(0);
        cursor.close();
        return queryResult;
    }

    public String queryWeeklyStretch(){
        Calendar date = new GregorianCalendar();
        String stringSQL = "select count(*) from stretches where strftime ('%W', datetime(stretchdate/1000, 'unixepoch')) = '"
                + String.valueOf(date.get(Calendar.WEEK_OF_YEAR) - 1)
                +"'";
        Log.d(TAG, "queryWeeklyStretch: " + stringSQL);
        Log.d(TAG, "queryWeeklyStretch: " + String.valueOf(date.get(Calendar.WEEK_OF_YEAR) - 1));
        Cursor cursor = database.rawQuery(stringSQL, null);
        cursor.moveToFirst();
        String queryResult = cursor.getString(0);
        cursor.close();
        return queryResult;
    }

    public String queryTodayStretch(){
        Calendar date = new GregorianCalendar();
        String stringSQL = "select count(*) from stretches where strftime('%d', datetime(stretchdate/1000, 'unixepoch')) = '"
                + date.get(Calendar.DAY_OF_MONTH)
                +"' and strftime('%m', datetime(stretchdate/1000, 'unixepoch')) = '"
                + String.valueOf(date.get(Calendar.MONTH) + 1)
                +"' and strftime('%Y', datetime(stretchdate/1000, 'unixepoch')) = '"
                + date.get(Calendar.YEAR)
                +"'";
        Log.d(TAG, "queryTodayStretch: " + stringSQL);
        Log.d(TAG, "queryTodayStretch: " + date.get(Calendar.DAY_OF_WEEK));
        Cursor cursor = database.rawQuery(stringSQL, null);
        cursor.moveToFirst();
        String queryResult = cursor.getString(0);
        cursor.close();
        return queryResult;
    }

        public String[] queryWeekdayStetch(){
            Calendar date = new GregorianCalendar();
            String weekday[] = new String[7];

            for(int i = 0; i < 7 ; i++){
                String stringSQL = "select count(*) from stretches where strftime ('%W', datetime(stretchdate/1000, 'unixepoch')) = '"
                        + String.valueOf(date.get(Calendar.WEEK_OF_YEAR) - 1)
                        +"' and strftime('%w', datetime(stretchdate/1000, 'unixepoch')) = '"
                        + i
                        +"'";
                Cursor cursor = database.rawQuery(stringSQL, null);
                cursor.moveToFirst();
                weekday[i] = cursor.getString(0);
                Log.d(TAG, "queryWeekdayStetch: " + weekday[i] + "\n");
                cursor.close();
            }

            return weekday;
    }

}
