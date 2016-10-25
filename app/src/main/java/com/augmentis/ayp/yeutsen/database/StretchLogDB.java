package com.augmentis.ayp.yeutsen.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.augmentis.ayp.yeutsen.database.StretchLogDBSchama.*;

/**
 * Created by Noppharat on 10/10/2016.
 */

public class StretchLogDB extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "StretchLogDB.db";

    public StretchLogDB(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + StretchLogTable.NAME
            + "("
            + StretchLogTable.Cols.ID + " primary key, "
            + StretchLogTable.Cols.USERID + ", "
            + StretchLogTable.Cols.STRETCHDATE + ", "
            + StretchLogTable.Cols.STRETCHID + ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
