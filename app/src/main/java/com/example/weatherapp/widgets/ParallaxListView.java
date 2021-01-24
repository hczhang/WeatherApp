package com.example.weatherapp.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.weatherapp.R;

/**
 * ParallaxListView, simple implementation.
 */
public class ParallaxListView extends ListView {
    private LinearLayout llHeaderView, llName, llTemperature;

    private float heightOfHeader, heightOfName;

    /** Constructor */
    public ParallaxListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setVerticalScrollBarEnabled(false);

        // Initiates the scroll listener.
        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (llHeaderView == null) return;

                heightOfHeader = llHeaderView.getHeight();
                heightOfName = llName.getHeight();

                float topOfHeader = llHeaderView.getTop();
                float topOfName = llName.getTop();

                // Opacity of temperature layout.
                llTemperature.setAlpha(1 - (-topOfHeader / (heightOfHeader - heightOfName) * 1.5f));
                // Translation of header texts.
                llName.setTranslationY(Math.max(-topOfHeader / 1.5f, -topOfHeader - topOfName));
                llTemperature.setTranslationY(Math.max(-topOfHeader / 1.5f, -topOfHeader - topOfName));
            }
        });
    }


    @Override
    public void addHeaderView(View view) {
        super.addHeaderView(view);
        llHeaderView = (LinearLayout) view;
        llName = view.findViewById(R.id.ll_header_name);
        llTemperature = view.findViewById(R.id.ll_header_temperature);
    }
}
