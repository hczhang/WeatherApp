package com.example.weatherapp.api.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/** The Hourly object inside Daily */
public class Hourly {

    @SerializedName("time")
    String time;

    @SerializedName("temperature")
    String temperature;

    @SerializedName("weather_descriptions")
    List<String> descs;

    @SerializedName("weather_icons")
    List<String> icons;

    @SerializedName("wind_speed")
    int windSpeed;

    public String getTime() {
        return time;
    }

    public String getTemperature() {
        return temperature + "\u00B0";
    }

    public String getDesc() {
        return descs != null && descs.size() != 0 ? descs.get(0) : "Unknown";
    }

    public String getIconUrl() {
        return icons != null && icons.size() != 0 ? icons.get(0) : "";
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    /**
     * Animation duration time for wind speed simulation.
     */
    public long getAnimDuration() {
        return Math.max(5000, 30000 - windSpeed * 1000);
    }
}
