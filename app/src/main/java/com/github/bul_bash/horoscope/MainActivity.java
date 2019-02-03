package com.github.bul_bash.horoscope;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.AppComponentFactory;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
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

public class MainActivity extends AppCompatActivity {

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
//            helper.loadDataToDb();
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
    protected void onResume() {
        super.onResume();
        setTitle(PreferenceManager.getDefaultSharedPreferences(this).getString("sign", ""));
    }

    @Override
    protected void onStart() {
        super.onStart();
        helper.showData();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // получим идентификатор выбранного пункта меню
        int id = item.getItemId();


        // Операции для выбранного пункта меню
        switch (id) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_info:

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
