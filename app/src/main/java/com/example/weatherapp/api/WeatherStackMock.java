package com.example.weatherapp.api;

import com.example.weatherapp.AAplication;
import com.example.weatherapp.api.bean.Weather;
import com.example.weatherapp.utils.FileUtil;
import com.example.weatherapp.utils.Preference;
import com.google.gson.Gson;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

/**
 * A WeatherStack API interface implementation to mock the data from a json file.
 */
public class WeatherStackMock implements WeatherStack {
    private final BehaviorDelegate<WeatherStack> delegate;

    public WeatherStackMock(BehaviorDelegate<WeatherStack> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Call<Weather> getCurrent(String accessKey, String unit, String keyword) {
        return null; // Not used
    }

    @Override
    public Call<Weather> getHistorical(String accessKey, String keyword, String unit, String date) {
        return null; // Not used
    }

    @Override
    public Call<Weather> getForecast(String accessKey, String keyword, String unit, int days) {
        List<Weather> savedWeathers = Preference.getWeathers(AAplication.getInstance());
        Weather weather = new Weather();
        try {
            String json = FileUtil.getStringFromFile(AAplication.getInstance(), "forecast.json");
            weather = new Gson().fromJson(json, Weather.class);
            weather.setKeyword(keyword); // Mock keyword
            weather.setCurrent(savedWeathers.get(new Random().nextInt(savedWeathers.size())).getCurrent());
            weather.getLocation().setName(keyword); // Mock Name
        } catch (Exception e) {
            e.printStackTrace();
        }

        return delegate.returningResponse(weather).getForecast(accessKey, keyword, unit, days);
    }

}
