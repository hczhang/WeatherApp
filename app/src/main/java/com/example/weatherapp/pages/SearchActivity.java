package com.example.weatherapp.pages;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.weatherapp.R;
import com.example.weatherapp.api.bean.Weather;
import com.example.weatherapp.api.WeatherApi;
import com.example.weatherapp.utils.Preference;
import com.example.weatherapp.widgets.MaterialSearchBar;
import com.example.weatherapp.widgets.SimpleSearchBar;
import com.example.weatherapp.widgets.UnitSwitch;

import java.util.List;

/**
 * The Home/Search activity.
 */
public class SearchActivity extends BaseActivity {
    private MaterialSearchBar msbInput;
//    private SimpleSearchBar ssbInput;
    private RecyclerView rvWeatherList;
    private UnitSwitch usUnitSwitch;
    private SearchAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initEvents();
    }

    private void initView() {
        setContentView(R.layout.activity_search);
        msbInput = findViewById(R.id.msb_search_input);
//        ssbInput = findViewById(R.id.ssb_search_input);
        rvWeatherList = findViewById(R.id.lv_search_result);
        usUnitSwitch = findViewById(R.id.us_search_unitswitch);

        setupKeyboard(findViewById(R.id.cl_search_parent));

        rvWeatherList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mAdapter = new SearchAdapter();
        rvWeatherList.setAdapter(mAdapter);
    }

    private void initEvents() {
        msbInput.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                searchKeyword();
            }

            @Override
            public void onButtonClicked(int buttonCode) {
            }
        });

//        ssbInput.setOnSearchActionListener(text -> {
//            searchKeyword();
//        });

        usUnitSwitch.setOnUnitChangeListener(isCelsius -> {
            reloadKeywords();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        ssbInput.requestFocus();
        hideSoftKeyboard();

        // Reads saved weathers from storage when the app resumes.
        List<Weather> weathers = Preference.getWeathers(this);
        if (weathers != null && weathers.size() > 0) {
            mAdapter.setWeathers(weathers);
            reloadKeywords();
        }
    }

    private String getKeyword() {
        return msbInput.getText();
//        return ssbInput.getText().toString().trim();
    }

    private void clearKeyword() {
        hideSoftKeyboard();
        msbInput.setText(null);
//        ssbInput.setText(null);
    }

    /**
     * Reload data from network, according to the recently saved weather list.
     */
    private void reloadKeywords() {
        List<Weather> weathers = mAdapter.getWeathers();
        for (Weather weather : weathers) {
            WeatherApi.getCurrent(this, weather.getKeyword(), new WeatherApi.OnDataResponse() {
                @Override
                public void onDataAvailable(Weather weather) {
                    // Updates adapter.
                    mAdapter.setWeather(weather);

                    // Saves recent data.
                    Preference.saveWeathers(SearchActivity.this, mAdapter.getWeathers());
                }

                @Override
                public void onDataEmpty() {
                    // Does nothing. To be implemented.
                }

                @Override
                public void onRequestError() {
                    // Does nothing. To be implemented.
                }
            });
        }

    }

    /**
     * Searches one location from the network, and adds the result to the search list.
     */
    private void searchKeyword() {
        String keyword = getKeyword();
        if (keyword.isEmpty()) return;

        showProgress();
        WeatherApi.getCurrent(this, keyword, new WeatherApi.OnDataResponse() {
            @Override
            public void onDataAvailable(Weather weather) {
                dismissProgress();
                clearKeyword();
                mAdapter.pushWeather(weather);
                rvWeatherList.smoothScrollToPosition(0);

                // Saves recent data.
                Preference.saveWeathers(SearchActivity.this, mAdapter.getWeathers());
            }

            @Override
            public void onDataEmpty() {
                dismissProgress();
                Toast.makeText(SearchActivity.this, "Location not found.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRequestError() {
                dismissProgress();
                Toast.makeText(SearchActivity.this, "Request error.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}