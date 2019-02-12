package com.github.bul_bash.horoscope;

import android.content.SharedPreferences;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.github.bul_bash.horoscope.data.DataHelper;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getFragmentManager()
                .beginTransaction()
                .add(R.id.prefs_content, new SettingsFragment())
                .commit();

    }


    public static class SettingsFragment extends PreferenceFragment   {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref);

        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        boolean enabledNotifiction;
        String sign;

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        enabledNotifiction=preferences.getBoolean(getString(R.string.notificationKey), false);
        sign=preferences.getString(getString(R.string.signKey), "");

        new DataHelper(this)
                .putProfileInDB(sign);

    }
}
