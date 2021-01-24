package com.example.weatherapp.widgets;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.example.weatherapp.R;
import com.example.weatherapp.api.bean.Weather;
import com.example.weatherapp.utils.AppUtil;

public class ForecastPage extends ConstraintLayout {
    private Activity mActivity;
    private ImageFlowLayout ifLayout;
    private ListView lvForecast;
    private ListRowAdapter mListAdapter;
    private LinearLayout llHeaderView;
    private TextView tvName, tvCountry, tvDesc, tvTemperature, tvMaxTemp, tvMinTemp;

    private Weather mWeather;

    public ForecastPage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
    }

    private void initView() {
        mActivity = AppUtil.getActivity(getContext());
        ifLayout = findViewById(R.id.if_detail_image);
        lvForecast = findViewById(R.id.lv_detail_forecast);
        llHeaderView = (LinearLayout) mActivity.getLayoutInflater().inflate(R.layout.activity_detail_header, null);
        tvName = llHeaderView.findViewById(R.id.tv_header_name);
        tvCountry = llHeaderView.findViewById(R.id.tv_header_country);
        tvDesc = llHeaderView.findViewById(R.id.tv_header_desc);
        tvTemperature = llHeaderView.findViewById(R.id.tv_header_temperature);
        tvMaxTemp = llHeaderView.findViewById(R.id.tv_header_maxtemp);
        tvMinTemp = llHeaderView.findViewById(R.id.tv_header_mintemp);

        mListAdapter = new ListRowAdapter();
        lvForecast.setAdapter(mListAdapter);
        lvForecast.addHeaderView(llHeaderView);
    }

    public void setData(Weather weather) {
        mWeather = weather;
        mListAdapter.notifyDataSetChanged();

        ifLayout.setImage(weather.getCurrent().getIconUrl(), weather.getCurrent().getAnimDuration());
        tvName.setText(weather.getLocation().getName());
        tvCountry.setText(weather.getLocation().getCountry());
        tvDesc.setText(weather.getCurrent().getDesc());
        tvTemperature.setText(weather.getCurrent().getTemperature());
        if (weather.getForecastList() != null && weather.getForecastList().size() > 0) {
            tvMaxTemp.setText("H: " + weather.getForecastList().get(0).getMaxTemp()); // TODO To optimize String.
            tvMinTemp.setText("L: " + weather.getForecastList().get(0).getMinTemp());
        }
    }

    // TODO Could be a separate class.
    /** Adapter class. */
    private class ListRowAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // The first list item is today, forecast list starts from tomorrow.
            return mWeather == null || mWeather.getForecastList() == null || mWeather.getForecastList().size() <= 1
                    ? 0 : mWeather.getForecastList().size() - 1;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            final View view = convertView != null ? convertView :
                    mActivity.getLayoutInflater().inflate(R.layout.list_item_forecast, parent, false);
            TextView tvWeekday = view.findViewById(R.id.tv_item_weekday);
            TextView tvHighTemp = view.findViewById(R.id.tv_item_hightemp);
            TextView tvLowTemp = view.findViewById(R.id.tv_item_lowtemp);
            ImageView ivIcon = view.findViewById(R.id.iv_item_icon);

            // The first list item is today, forecast list starts from tomorrow.
            tvWeekday.setText(mWeather.getForecastList().get(position + 1).getWeekday());
            tvHighTemp.setText(mWeather.getForecastList().get(position + 1).getMaxTemp());
            tvLowTemp.setText(mWeather.getForecastList().get(position + 1).getMinTemp());

            try {
                Glide.with(mActivity)
                        .load(mWeather.getForecastList().get(position + 1).getListHourly().get(0).getIconUrl())
                        .centerCrop()
                        .into(ivIcon);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return view;
        }
    }

}
