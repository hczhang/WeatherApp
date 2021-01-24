package com.example.weatherapp.pages;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weatherapp.R;
import com.example.weatherapp.api.WeatherApi;
import com.example.weatherapp.api.bean.Weather;
import com.example.weatherapp.utils.Preference;
import com.example.weatherapp.widgets.ForecastViewPager;
import com.example.weatherapp.widgets.PagerNavLayout;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Activity for the detail screen.
 */
public class DetailActivity extends AppCompatActivity {
    private PagerNavLayout mNavLayout;
    private ForecastViewPager mViewPager;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initEvents();
    }

    private void initView() {
        setContentView(R.layout.activity_detail);
        List<Weather> weathers = Preference.getWeathers(this);
        position = getIntent().getIntExtra("position", 0);

        mViewPager = findViewById(R.id.vp_detail_pager);
        mNavLayout = findViewById(R.id.pn_trade_tabs);
        for (int i = 0; i < weathers.size(); i++) {
            TextView textView = (TextView) getLayoutInflater().inflate(R.layout.widget_nav_text, null);
            textView.setText(String.valueOf(i + 1));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(40, WRAP_CONTENT);
            mNavLayout.addView(textView, lp);
        }
        mNavLayout.setupWithViewPager(mViewPager);
        mNavLayout.setActivePage(position);
    }

    private void initEvents() {
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Reads saved weathers from storage when the app resumes.
        final List<Weather> weathers = Preference.getWeathers(this);
        if (weathers == null || weathers.size() == 0) return;

        for (int i = 0; i < weathers.size(); i++) {
            Weather weather = weathers.get(i);
            final int j = i;
            WeatherApi.getForecast(this, weather.getKeyword(), new WeatherApi.OnDataResponse() {
                @Override
                public void onDataAvailable(Weather weather) {
                    mViewPager.setPageData(j, weather);

                    // Saves recent data.
                    weathers.remove(j);
                    weathers.add(j, weather);
                    Preference.saveWeathers(DetailActivity.this, weathers);
                }

                @Override
                public void onDataEmpty() {
                    // To be implemented.
                }

                @Override
                public void onRequestError() {
                    Toast.makeText(DetailActivity.this, "Request error.", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_enter_left, R.anim.activity_exit_right);
    }

}