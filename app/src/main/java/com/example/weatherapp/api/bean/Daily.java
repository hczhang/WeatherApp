package com.example.weatherapp.api.bean;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/** The Daily object inside Forecast/Historical */
public class Daily {

    @SerializedName("date")
    String date;

    @SerializedName("mintemp")
    String minTemp;

    @SerializedName("maxtemp")
    String maxTemp;

    @SerializedName("avgtemp")
    String avgTemp;

    @SerializedName("hourly")
    List<Hourly> listHourly;

    public String getDate() {
        return date;
    }

    public String getWeekday() {
        try {
            Date day = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            String weekday = new SimpleDateFormat("EEEE").format(day);
            return weekday;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public String getAvgTemp() {
        return avgTemp;
    }

    public List<Hourly> getListHourly() {
        return listHourly;
    }
}
