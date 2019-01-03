package com.github.bul_bash.horoscope.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HoroscopeDBHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = HoroscopeDBHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "horoscope.db";

    //version db.  need increment when db is changed
    private static final int DATABASE_VERSION = 1;


    public HoroscopeDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE = "CREATE TABLE "+HoroscopeContract.HoroscopeData.TABLE_NAME
                + " ("
                + HoroscopeContract.HoroscopeData._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HoroscopeContract.HoroscopeData.DATA + " NOT NULL);";
        db.execSQL(SQL_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
