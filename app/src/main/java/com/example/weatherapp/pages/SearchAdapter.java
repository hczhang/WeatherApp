package com.example.weatherapp.pages;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherapp.R;
import com.example.weatherapp.api.bean.Weather;
import com.example.weatherapp.utils.AppUtil;
import com.example.weatherapp.widgets.ImageFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter used to the search list.
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private final List<Weather> mWeathers = new ArrayList<>();

    /** ViewHolder */
    static class ViewHolder extends RecyclerView.ViewHolder {
        final View vItemView;
        final ImageFlowLayout ifLayout;
        final ImageView ivIcon;
        final TextView tvTime, tvName, tvTemperature, tvDesc;

        public ViewHolder(View view) {
            super(view);
            vItemView = view;
            ifLayout = view.findViewById(R.id.if_item_image);
            ivIcon = view.findViewById(R.id.iv_item_icon);
            tvTime = view.findViewById(R.id.tv_item_time);
            tvName = view.findViewById(R.id.tv_item_name);
            tvTemperature = view.findViewById(R.id.tv_item_temperature);
            tvDesc = view.findViewById(R.id.tv_item_desc);
        }

    }

    public SearchAdapter() {}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_current, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.tvTime.setText(mWeathers.get(position).getLocation().getLocalTime());
        viewHolder.tvName.setText(mWeathers.get(position).getLocation().getName());
        viewHolder.tvTemperature.setText(mWeathers.get(position).getCurrent().getTemperature());
        viewHolder.tvDesc.setText(mWeathers.get(position).getCurrent().getDesc());

        viewHolder.ifLayout.setImage(mWeathers.get(position).getCurrent().getIconUrl(),
                mWeathers.get(position).getCurrent().getAnimDuration());

        try {
            Glide.with(viewHolder.vItemView)
                    .load(mWeathers.get(position).getCurrent().getIconUrl())
                    .centerCrop()
                    .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                    .into(viewHolder.ivIcon);
        } catch (Exception e) {
            e.printStackTrace();
        }

        viewHolder.vItemView.setOnClickListener(v -> {
            Activity activity = AppUtil.getActivity(viewHolder.vItemView.getContext());
            Intent intent = new Intent(activity, DetailActivity.class);
            intent.putExtra("position", position);
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.activity_enter_right, R.anim.activity_exit_left);
        });
    }

    @Override
    public int getItemCount() {
        return mWeathers.size();
    }

    @Override
    public long getItemId(int position) {
        return mWeathers.get(position).getLocation().hashCode();
    }

    public void setWeathers(List<Weather> weathers) {
        if (weathers == null || weathers.size() == 0) return;

        mWeathers.clear();
        mWeathers.addAll(weathers);
        notifyDataSetChanged();
    }

    public List<Weather> getWeathers() {
        return mWeathers;
    }

    /**
     * Sets a weather object to the existing list.
     * This will replace the one which is the same location without changing the list order.
     */
    public void setWeather(Weather weather) {
        int position = mWeathers.indexOf(weather);
        if (position >= 0) {
            mWeathers.remove(position);
            mWeathers.add(position, weather);
            notifyItemChanged(position);
        }
    }

    /**
     * Pushes a weather object to the existing list.
     * If the location already exists, removes it and inserts the new result to the top.
     */
    public void pushWeather(Weather weather) {
        // Removes this location if it already exists.
        int position = mWeathers.indexOf(weather);
        if (position >= 0) {
            mWeathers.remove(position);
            notifyItemRemoved(position);
        }

        mWeathers.add(0, weather);
        notifyItemInserted(0);
    }
}
