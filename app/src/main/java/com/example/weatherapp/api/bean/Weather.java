package com.example.weatherapp.api.bean;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/** The Weather object, which wraps Current, Forecast, and Historical */
public class Weather {

    @SerializedName("location")
    private Location location;

    @SerializedName("current")
    private Current current;

    @SerializedName("historical")
    private Map<String, Daily> historical;

    @SerializedName("forecast")
    private Map<String, Daily> forecast;

    @SerializedName("keyword")
    private String keyword;


    public Location getLocation() {
        return location;
    }

    public Current getCurrent() {
        return current;
    }

    //TODO This is for mock data manipulation only.
    // Not necessary for network data.
    public void setCurrent(Current current) {
        this.current = current;
    }

    public Map<String, Daily> getHistorical() {
        return historical;
    }

    public Map<String, Daily> getForecast() {
        return forecast;
    }

    /**
     * Returns a sorted forecast list.
     */
    public List<Daily> getForecastList() {
        if (forecast == null) return null;

        List<String> days = new ArrayList<>(forecast.keySet());
        Collections.sort(days);
        List<Daily> list = new ArrayList<>();
        for (String day : days) list.add(forecast.get(day));
        return list;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Weather weather = (Weather) obj;
        return Objects.equals(location, weather.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location);
    }

}
