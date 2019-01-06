package com.github.bul_bash.horoscope;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.bul_bash.horoscope.data.Data;
import com.github.bul_bash.horoscope.data.HoroscopeContract;
import com.github.bul_bash.horoscope.data.DataHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends FragmentActivity {

    private TextView mTextMessage;

    ViewPager pager;
    PagerAdapter pagerAdapter;
    DataHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new DataHelper(this);

        pager = findViewById(R.id.pager);
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);

        pager.setCurrentItem(Data.PAGE_COUNT-2);

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });



        //is worked
        helper.createDb();

//        try {
//            loadDataToDb();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        helper.selectRandomRecord();




//        TextView text = findViewById(R.id.textView);
//        text.setText("olololo");
//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }


    @Override
    protected void onStart() {
        super.onStart();
        helper.showData();
    }

}
