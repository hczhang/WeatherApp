package com.example.weatherapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.weatherapp.api.bean.Weather;
import com.example.weatherapp.widgets.UnitSwitch;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.weatherapp.api.WeatherStack.UNIT_M;

/**
 * SharedPreferences utility.
 */
public class Preference {
    private static final String RECENT = "recent";
    private static final String WEATHERS = "weathers";
    private static final String UNIT = "unit";

    /**
     * Using static methods only.
     */
    private Preference() {
    }

    public static List<Weather> getWeathers(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(RECENT, MODE_PRIVATE);
        String savedWeathers = prefs.getString(WEATHERS, "");
        Type listType = new TypeToken<ArrayList<Weather>>(){}.getType();
        return new Gson().fromJson(savedWeathers, listType);
    }

    public static void saveWeathers(Context context, List<Weather> weathers) {
        SharedPreferences prefs = context.getSharedPreferences(RECENT, MODE_PRIVATE);
        prefs.edit().putString(WEATHERS, new Gson().toJson(weathers)).apply();
    }

    public static String getUnit(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(RECENT, MODE_PRIVATE);
        return prefs.getString(UNIT, UNIT_M);
    }

    public static void saveUnit(Context context, String unit) {
        SharedPreferences prefs = context.getSharedPreferences(RECENT, MODE_PRIVATE);
        prefs.edit().putString(UNIT, unit).apply();
    }
}
