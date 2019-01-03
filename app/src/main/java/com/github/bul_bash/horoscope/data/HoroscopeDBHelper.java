package com.github.bul_bash.horoscope.data;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HoroscopeDBHelper extends SQLiteOpenHelper {

    private static String DB_PATH = "/data/data/com.github.bul_bash.horoscope/databases/";

    private static final String DB_NAME = "horoscope.db";

    private SQLiteDatabase database;

    private final Context context;

    //version db.  need increment when db is changed
    private static final int DATABASE_VERSION = 1;


    public HoroscopeDBHelper(Context context){
        super(context, DB_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    public void createDataBase(){
        boolean dbExist = checkDataBase();
        if (!dbExist){
            this.getReadableDatabase();
            try {
                copyDataBase();
            }
            catch (IOException e){
                throw  new Error("Error copying database");
            }
        }
    }

    private boolean checkDataBase(){
        SQLiteDatabase checkDb = null;
        try {
            String path = DB_PATH+DB_NAME;
            checkDb = SQLiteDatabase.openDatabase(path, null,  SQLiteDatabase.OPEN_READONLY);

        }
        catch (SQLiteException e){
            int a=10;
        }
        if (checkDb!=null){
            checkDb.close();
        }

        return checkDb != null ? true : false;
    }


    private void copyDataBase() throws IOException {
        InputStream input = context.getAssets().open(DB_NAME);

        String outFileName = DB_PATH+DB_NAME;
        OutputStream output = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = input.read(buffer))>0){
            output.write(buffer,0,length);
        }
        output.flush();
        output.close();
        input.close();
    }

    public void openDataBase() throws SQLException{
        String path = DB_PATH + DB_NAME;
        database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close(){
        if (database!=null){
            database.close();
        }
        super.close();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
//        String SQL_CREATE = "CREATE TABLE "+HoroscopeContract.HoroscopeData.TABLE_NAME
//                + " ("
//                + HoroscopeContract.HoroscopeData._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//                + HoroscopeContract.HoroscopeData.DATA + " NOT NULL);";
//        db.execSQL(SQL_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
