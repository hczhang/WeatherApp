package com.example.weatherapp.api.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/** The Current object inside Weather */
public class Current {

    @SerializedName("temperature")
    private String temperature;

    @SerializedName("observation_time")
    private String observationTime;

    @SerializedName("weather_descriptions")
    private List<String> descs;

    @SerializedName("weather_icons")
    private List<String> icons;

    @SerializedName("wind_speed")
    int windSpeed;

    public String getTemperature() {
        return temperature + "\u00B0";
    }

    public String getObservationTime() {
        return observationTime;
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
