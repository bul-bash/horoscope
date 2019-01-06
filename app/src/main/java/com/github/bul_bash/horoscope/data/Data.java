package com.github.bul_bash.horoscope.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Data {

    static public final int PAGE_COUNT = 7;

    static private List<String> forecasts = new ArrayList<>();
    static private List<String> dates = new ArrayList<>();


    public static void setForecasts(List<String> forecasts) {
        Data.forecasts = forecasts;
    }
    public static List<String> getForecasts() {

        return forecasts;
    }

    public static void setDates(List<String> dates) {
        Data.dates = dates;
    }

    public static List<String> getDates() {

        return dates;
    }

    public static void reverse(){
        Collections.reverse(forecasts);
        Collections.reverse(dates);
    }


}
