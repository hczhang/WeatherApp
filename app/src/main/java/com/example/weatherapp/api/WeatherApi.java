package com.example.weatherapp.api;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.example.weatherapp.api.bean.Weather;
import com.example.weatherapp.utils.Preference;

import java.nio.charset.StandardCharsets;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

/**
 * WeatherApi, using Retrofit and WeatherStack interfaces.
 */
public class WeatherApi {
    private static Retrofit RETROFIT_INSTANCE;

    public interface OnDataResponse {
        void onDataAvailable(Weather weather);
        void onDataEmpty();
        void onRequestError();
    }

    private static Retrofit getRetrofit() {
        if (RETROFIT_INSTANCE != null) return RETROFIT_INSTANCE;

        RETROFIT_INSTANCE = new Retrofit.Builder()
                .baseUrl(WeatherStack.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return RETROFIT_INSTANCE;
    }

    private static WeatherStack getApi() {
        return getRetrofit().create(WeatherStack.class);
    }

    /**
     * Create a MockRetrofit object with a NetworkBehavior which manages the fake behavior of calls.
     */
    private static WeatherStack getMockApi() {
        NetworkBehavior behavior = NetworkBehavior.create();
        MockRetrofit mockRetrofit = new MockRetrofit.Builder(getRetrofit()).networkBehavior(behavior).build();
        BehaviorDelegate<WeatherStack> delegate = mockRetrofit.create(WeatherStack.class);
        return new WeatherStackMock(delegate);
    }

    // TODO
    //  AccessKey could be changed from time to time, and should be retrieved from a server
    //  dynamically in an encrypted form. The demonstrated Base64 here is weak.
    private static String getAccessKey() {
        String encodedKey = "M2E3ODcwMDExNzVjYjZhMzk0M2YwZDg4NTYwOGEzZGU=";
        return new String(Base64.decode(encodedKey, Base64.DEFAULT), StandardCharsets.UTF_8);
    }

    public static void getCurrent(Context context, String keyword, OnDataResponse onDataResponse) {

        Call<Weather> call = getApi().getCurrent(getAccessKey(), Preference.getUnit(context), keyword);
        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                if (response.body().getCurrent() != null) {
                    response.body().setKeyword(keyword);
                    onDataResponse.onDataAvailable(response.body());
//                    Log.d("weatherapi", response.body().toString());
                } else {
                    onDataResponse.onDataEmpty();
                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                onDataResponse.onRequestError();
                t.printStackTrace();
            }
        });
    }

    public static void getForecast(Context context, String keyword, OnDataResponse onDataResponse) {

        // TODO Change getMockApi() to getApi() if WeatherStack subscription plan supports forecast request.
        Call<Weather> call = getMockApi().getForecast(getAccessKey(), keyword,  Preference.getUnit(context), 7);

        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                if (response.body().getCurrent() != null) {
                    response.body().setKeyword(keyword);
                    onDataResponse.onDataAvailable(response.body());
//                    Log.d("weatherapi", response.body().toString());
                } else {
                    onDataResponse.onDataEmpty();
                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                onDataResponse.onRequestError();
                t.printStackTrace();
            }
        });
    }
}
