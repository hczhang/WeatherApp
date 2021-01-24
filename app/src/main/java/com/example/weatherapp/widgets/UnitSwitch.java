package com.example.weatherapp.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.utils.Preference;

import static com.example.weatherapp.api.WeatherStack.UNIT_F;
import static com.example.weatherapp.api.WeatherStack.UNIT_M;


/**
 * Degree unit switch. Switch between celsius and fahrenheit.
 * Default is celsius.
 */
public class UnitSwitch extends LinearLayout {

    private TextView tvCelsius, tvFahrenheit;
    private boolean isCelsius = true;
    private OnUnitChangeListener mListener;

    public interface OnUnitChangeListener {
        void onUnitChanged(boolean isCelsius);
    }

    /** Constructor from xml */
    public UnitSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        initEvents();
    }

    private void initView() {
        inflate(getContext(), R.layout.widget_unit_switch, this);
        tvCelsius = findViewById(R.id.tv_unitswitch_celsius);
        tvFahrenheit = findViewById(R.id.tv_unitswitch_fahrenheit);

        // Loads the recent status from the storage.
        isCelsius = UNIT_M.equals(Preference.getUnit(getContext()));
        updateChange();
    }

    private void initEvents() {
        setOnClickListener(v -> {
            toggleUnit();

            if (mListener != null)
                mListener.onUnitChanged(isCelsius);
        });
    }

    private void toggleUnit() {
        isCelsius = !isCelsius;
        updateChange();
    }

    private void updateChange() {
        tvCelsius.setTextColor(getResources().getColor(isCelsius ? R.color.white : R.color.degree_gray));
        tvFahrenheit.setTextColor(getResources().getColor(isCelsius ? R.color.degree_gray : R.color.white));
        Preference.saveUnit(getContext(), getUnit());
    }

    public String getUnit() {
        return isCelsius ? UNIT_M : UNIT_F;
    }

    public void setOnUnitChangeListener(OnUnitChangeListener listener) {
        mListener = listener;
    }
}