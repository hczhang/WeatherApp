package com.example.weatherapp.widgets;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.weatherapp.R;
import com.example.weatherapp.utils.AppUtil;


/**
 * Implementation of an effect like an image is flowing slowly in the background.
 */
public class ImageFlowLayout extends FrameLayout {
    private ImageView ivImage1, ivImage2;
    private String mUrl = "";
    private long mDuration;

    /** Constructor from xml */
    public ImageFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.widget_image_flow, this);
        ivImage1 = findViewById(R.id.iv_image1);
        ivImage2 = findViewById(R.id.iv_image2);
    }

    public void setImage(String url, long duration) {
        if (url == null || url.isEmpty()) return;
        if (mUrl.equals(url) && mDuration == duration) return;

        mUrl = url;
        mDuration = duration;

        Activity activity = AppUtil.getActivity(getContext());
        try {
            Glide.with(activity)
                    .load(url)
                    .centerCrop()
                    .into(ivImage1);

            Glide.with(activity)
                    .load(url)
                    .centerCrop()
                    .into(ivImage2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float width = ivImage1.getWidth();
                final float translationX = width * progress;
                ivImage1.setTranslationX(translationX);
                ivImage2.setTranslationX(translationX - width);
            }
        });
        animator.start();

    }

}