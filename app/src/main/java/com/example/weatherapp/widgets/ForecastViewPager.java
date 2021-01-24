package com.example.weatherapp.widgets;

import android.content.Context;
import android.util.AttributeSet;

import androidx.viewpager.widget.ViewPager;

import com.example.weatherapp.R;
import com.example.weatherapp.api.bean.Weather;
import com.example.weatherapp.utils.Preference;

import java.util.ArrayList;
import java.util.List;

public class ForecastViewPager extends ViewPager {
    private List<ForecastPage> mForecastPages = new ArrayList<>();

    /** Constructor */
    public ForecastViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
    }

    private void initView() {
        List<Weather> weathers = Preference.getWeathers(getContext());

        // ViewPager pages.
        for (int i = 0; i < weathers.size(); i++) {
            ForecastPage forecastPage = (ForecastPage) inflate(getContext(), R.layout.activity_detail_page, null);
            // Set initial data from storage.
            forecastPage.setData(weathers.get(i));
            mForecastPages.add(forecastPage);
        }
        setAdapter(new PagerNavLayout.TabPagerAdapter(mForecastPages));
    }

    public void setPageData(int position, Weather weather) {
        mForecastPages.get(position).setData(weather);
    }

}
