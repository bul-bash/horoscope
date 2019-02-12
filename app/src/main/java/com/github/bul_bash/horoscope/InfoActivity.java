package com.github.bul_bash.horoscope;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class InfoActivity extends AppCompatActivity implements View.OnClickListener {

    private int countOfClick=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        View view = findViewById(R.id.infoActivity);
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        countOfClick++;
        if (countOfClick>5){
            setTitle(R.string.app_name_2);
            countOfClick=0;
        }
    }
}
