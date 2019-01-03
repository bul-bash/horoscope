package com.github.bul_bash.horoscope;

import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.bul_bash.horoscope.data.HoroscopeContract;
import com.github.bul_bash.horoscope.data.HoroscopeDBHelper;

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

        mTextMessage = (TextView) findViewById(R.id.message);


//        TextView text = findViewById(R.id.textView);
//        text.setText("olololo");
//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        horoscopeDBHelper = new HoroscopeDBHelper(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        displayDBInfo();

    }

    private void displayDBInfo() {
        SQLiteDatabase db = horoscopeDBHelper.getReadableDatabase();

        String[] projection = {
                HoroscopeContract.HoroscopeData._ID,
                HoroscopeContract.HoroscopeData.DATA
        };
        Cursor cursor = db.query(
                HoroscopeContract.HoroscopeData.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);


        TextView textView = findViewById(R.id.textView);
        try{
            int idColumnIndex = cursor.getColumnIndex(HoroscopeContract.HoroscopeData._ID);
            int dataColumnIndex = cursor.getColumnIndex(HoroscopeContract.HoroscopeData.DATA);


            while (cursor.moveToNext()){
                int currentId = cursor.getInt(idColumnIndex);
                String data = cursor.getString(dataColumnIndex);
                textView.setText(data+"\n\n"+currentId);
            }
        }
        finally {
            cursor.close();
        }
    }
}
