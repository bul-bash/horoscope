package com.github.bul_bash.horoscope.data;

import android.provider.BaseColumns;

public final class HoroscopeContract {

    private HoroscopeContract(){

    }

    public static final class HoroscopeData implements BaseColumns{
        public final static String TABLE_NAME = "HOROSCOPE_DATA";
        public final static String _ID = BaseColumns._ID;
        public final static String DATA = "DATA";
    }

}
