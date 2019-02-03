package com.github.bul_bash.horoscope.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;

import com.github.bul_bash.horoscope.PageFragment;
import com.github.bul_bash.horoscope.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DataHelper {


    private HoroscopeDBHelper horoscopeDBHelper;

    private Context context;

    public HoroscopeDBHelper getHoroscopeDBHelper() {
        return horoscopeDBHelper;
    }

    public DataHelper(Context context) {
        this.context = context;
    }

    public DataHelper() {

    }

    public void createDb() {
        horoscopeDBHelper = new HoroscopeDBHelper(context);
        horoscopeDBHelper.createDataBase();
        putSignsInDB(horoscopeDBHelper.getWritableDatabase());

    }

    public void putSignsInDB(SQLiteDatabase db) {
        horoscopeDBHelper = new HoroscopeDBHelper(context);
        ContentValues values = new ContentValues();
        String[] signs = context.getResources().getStringArray(R.array.signs);
        for (String sign : signs) {
            values.put(HoroscopeContract.SignsData.DATA, sign);

            db.insert(HoroscopeContract.SignsData.TABLE_NAME,
                    null,
                    values);
        }

    }


    public void putProfileInDB(String sign) {
        horoscopeDBHelper = new HoroscopeDBHelper(context);
        SQLiteDatabase db = horoscopeDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HoroscopeContract.ProfileData.ZODIAC_ID, sign);

        db.insert(HoroscopeContract.ProfileData.TABLE_NAME,
                null,
                values);

    }

    public void selectRandomRecord() {
        SQLiteDatabase db = horoscopeDBHelper.getWritableDatabase();

        String[] projection = {
                HoroscopeContract.HoroscopeData._ID,
                HoroscopeContract.HoroscopeData.DATA
        };


        String orderBy = "RANDOM() LIMIT 1";

        //SELECT * FROM TABLE_NAME ORDER BY RANDOM() LIMIT 1;


        Cursor cursor = db.query(
                HoroscopeContract.HoroscopeData.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                orderBy);

        int idColumnIndex = cursor.getColumnIndex(HoroscopeContract.HoroscopeData._ID);
        int dataColumnIndex = cursor.getColumnIndex(HoroscopeContract.HoroscopeData.DATA);


        String data = "";
        int id = 0;
        while (cursor.moveToNext()) {
            data = cursor.getString(dataColumnIndex);
            id = cursor.getInt(idColumnIndex);

        }

        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());


        ContentValues values = new ContentValues();
        values.put(HoroscopeContract.CurrentHoroscopeData.DATA, data);
        values.put(HoroscopeContract.CurrentHoroscopeData.DATE, date);

        db.insert(HoroscopeContract.CurrentHoroscopeData.TABLE_NAME,
                null,
                values);


        cursor.close();


        db.delete(HoroscopeContract.HoroscopeData.TABLE_NAME,
                HoroscopeContract.HoroscopeData._ID + " = ?",
                new String[]{Integer.toString(id)});
    }

    public void showData() {

        SQLiteDatabase db = horoscopeDBHelper.getReadableDatabase();


        String[] projection = {
                HoroscopeContract.CurrentHoroscopeData._ID,
                HoroscopeContract.CurrentHoroscopeData.DATA,
                HoroscopeContract.CurrentHoroscopeData.DATE
        };


        String selection = HoroscopeContract.CurrentHoroscopeData._ID + "=?";

        String[] selectionArgs = {"1"};

        String orderBy = HoroscopeContract.CurrentHoroscopeData._ID + " DESC LIMIT " + (Data.PAGE_COUNT - 1);

        //SELECT * FROM TABLE_NAME ORDER BY RANDOM() LIMIT 1;


        Cursor cursor = db.query(
                HoroscopeContract.CurrentHoroscopeData.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                orderBy);


        try {
            int idColumnIndex = cursor.getColumnIndex(HoroscopeContract.CurrentHoroscopeData._ID);
            int dataColumnIndex = cursor.getColumnIndex(HoroscopeContract.CurrentHoroscopeData.DATA);
            int dateColumnIndex = cursor.getColumnIndex(HoroscopeContract.CurrentHoroscopeData.DATE);


            while (cursor.moveToNext()) {
                int currentId = cursor.getInt(idColumnIndex);
                String data = cursor.getString(dataColumnIndex);
                String date = cursor.getString(dateColumnIndex);
                Data.getForecasts().add(data);
                Data.getDates().add(date);

                //TODO add show data
            }

//            Data.reverse();


        } finally {
            cursor.close();
        }
    }

    public void loadDataToDb() throws IOException {
        SQLiteDatabase db = horoscopeDBHelper.getWritableDatabase();
        InputStream input = context.getAssets().open("db.txt");

        String outFileName = "/data/data/com.github.bul_bash.horoscope/databases/db.txt";
        OutputStream output = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = input.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }
        output.flush();
        output.close();
        input.close();

        File file = new File(outFileName);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String str = null;


        while ((str = reader.readLine()) != null) {
            if (!str.contains("$$")) {
                ContentValues values = new ContentValues();
                values.put(HoroscopeContract.HoroscopeData.DATA, str);

                db.insert(HoroscopeContract.HoroscopeData.TABLE_NAME,
                        null,
                        values);
            }
        }
    }
}
