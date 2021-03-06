package com.github.bul_bash.horoscope.data;

import android.provider.BaseColumns;

public final class HoroscopeContract {

    private HoroscopeContract(){

    }

    public static final class HoroscopeData implements BaseColumns{
        public final static String TABLE_NAME = "HOROSCOPE_DATA";
        public final static String _ID = BaseColumns._ID;
        public final static String DATA = "data";
    }


    public static final class CurrentHoroscopeData implements BaseColumns{
        public final static String TABLE_NAME = "CURRENT_HOROSCOPE_DATA";
        public final static String _ID = BaseColumns._ID;
        public final static String DATA = "data";
        public final static String DATE = "date";
    }

    public static final class SignsData implements BaseColumns{
        public final static String TABLE_NAME = "SIGNS";
        public final static String _ID = BaseColumns._ID;
        public final static String DATA = "data";
    }


    public static final class ProfileData implements BaseColumns{
        public final static String TABLE_NAME = "PROFILE";
        public final static String _ID = BaseColumns._ID;
        public final static String ZODIAC_ID = "zodiac";
    }
}
