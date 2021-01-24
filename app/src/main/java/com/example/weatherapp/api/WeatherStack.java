package com.example.weatherapp.api;

import com.example.weatherapp.api.bean.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * WeatherStack API interface definitions.
 */
public interface WeatherStack {

    // Uses https to avoid clear text transmission.
    String BASE_URL = "https://api.weatherstack.com/";

    // Parameter constants.
    String UNIT_M = "m";
    String UNIT_F = "f";

    @GET("current")
    Call<Weather> getCurrent(@Query("access_key") String accessKey,
                             @Query("units") String unit,
                             @Query("query") String keyword);

    @GET("historical")
    Call<Weather> getHistorical(@Query("access_key") String accessKey,
                                @Query("query") String keyword,
                                @Query("units") String unit,
                                @Query("historical_date_start") String date);

    @GET("forecast")
    Call<Weather> getForecast(@Query("access_key") String accessKey,
                              @Query("query") String keyword,
                              @Query("units") String unit,
                              @Query("forecast_days") int days);
}
