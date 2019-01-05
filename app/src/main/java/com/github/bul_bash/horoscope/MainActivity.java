package com.github.bul_bash.horoscope;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.bul_bash.horoscope.data.HoroscopeContract;
import com.github.bul_bash.horoscope.data.HoroscopeDBHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private HoroscopeDBHelper horoscopeDBHelper;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    mTextMessage.setTextColor(Color.RED);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        horoscopeDBHelper = new HoroscopeDBHelper(this);
        horoscopeDBHelper.createDataBase();

//        try {
//            loadDataToDb();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        selectRandomRecord();
        mTextMessage = (TextView) findViewById(R.id.message);

        Button newButton = findViewById(R.id.newButton);

        View.OnClickListener onClickListenerBtn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectRandomRecord();
                showData();
            }
        };

        newButton.setOnClickListener(onClickListenerBtn);

//        TextView text = findViewById(R.id.textView);
//        text.setText("olololo");
//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }


    @Override
    protected void onStart() {
        super.onStart();
        showData();
    }

    private void selectRandomRecord() {
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
        ContentValues values = new ContentValues();
        values.put(HoroscopeContract.CurrentHoroscopeData.DATA, data);

        db.insert(HoroscopeContract.CurrentHoroscopeData.TABLE_NAME,
                null,
                values);


        cursor.close();


        db.delete(HoroscopeContract.HoroscopeData.TABLE_NAME,
                HoroscopeContract.HoroscopeData._ID + " = ?",
                new String[]{Integer.toString(id)});
    }

    private void showData() {

        SQLiteDatabase db = horoscopeDBHelper.getReadableDatabase();


        String[] projection = {
                HoroscopeContract.CurrentHoroscopeData._ID,
                HoroscopeContract.CurrentHoroscopeData.DATA
        };


        String selection = HoroscopeContract.CurrentHoroscopeData._ID + "=?";

        String[] selectionArgs = {"1"};

        String orderBy = HoroscopeContract.CurrentHoroscopeData._ID + " DESC LIMIT 1";

        //SELECT * FROM TABLE_NAME ORDER BY RANDOM() LIMIT 1;


        Cursor cursor = db.query(
                HoroscopeContract.CurrentHoroscopeData.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                orderBy);

        TextView textView = findViewById(R.id.textView);
        try {
            int idColumnIndex = cursor.getColumnIndex(HoroscopeContract.CurrentHoroscopeData._ID);
            int dataColumnIndex = cursor.getColumnIndex(HoroscopeContract.CurrentHoroscopeData.DATA);


            while (cursor.moveToNext()) {
                int currentId = cursor.getInt(idColumnIndex);
                String data = cursor.getString(dataColumnIndex);
                textView.setText(data + "\n\n" + currentId);
            }
        } finally {
            cursor.close();
        }
    }

    private void loadDataToDb() throws IOException {
        SQLiteDatabase db =horoscopeDBHelper.getWritableDatabase();
        InputStream input = this.getAssets().open("db.txt");

        String outFileName = "/data/data/com.github.bul_bash.horoscope/databases/db.txt";
        OutputStream output = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = input.read(buffer))>0){
            output.write(buffer,0,length);
        }
        output.flush();
        output.close();
        input.close();

        File file =new File(outFileName);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String str=null;


        while ((str = reader.readLine())!=null){
            if (!str.contains("$$")){
                ContentValues values = new ContentValues();
                values.put(HoroscopeContract.HoroscopeData.DATA, str);

                db.insert(HoroscopeContract.HoroscopeData.TABLE_NAME,
                        null,
                        values);
            }
        }
    }
}
