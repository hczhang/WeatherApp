package com.example.weatherapp.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Displaying navigation positions.
 */
public class PagerNavLayout extends LinearLayout {

    /**
     * ViewPager to be associated with.
     */
    private ViewPager mViewPager;

    /**
     * To be called inside OnPageChangeListener.
     */
    private OnTabChangeListener mOnTabChangeListener;

    /** Constructor */
    public PagerNavLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Associates the PagerNavLayout with a ViewPager.
     *
     * @param viewPager
     */
    public void setupWithViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
        if (mViewPager == null) return;

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }

            @Override
            public void onPageSelected(int i) {
                for (int j = 0; j < getChildCount(); j++) {
                    getChildAt(j).setSelected(j == i);
                }

                if (mOnTabChangeListener != null) {
                    mOnTabChangeListener.onTabChanged(i);
                }
            }
        });

        for (int i = 0; i < getChildCount(); i++) {
            final int j = i;
            getChildAt(j).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mViewPager.getAdapter() == null || mViewPager.getAdapter().getCount() <= j)
                        return;
                    mViewPager.setCurrentItem(j, true);
                }
            });
        }
    }

    /**
     * Sets the current tab as well as the viewPager position.
     * Must be called after setupWithViewPager().
     *
     * @param i
     */
    public void setActivePage(int i) {
        if (mViewPager == null) {
            throw new IllegalStateException("ViewPager has not been setup.");
        } else if (mViewPager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager is empty.");
        }

        if (i < 0 || getChildCount() <= i || mViewPager.getAdapter().getCount() <= i) return;
        getChildAt(i).setSelected(true);
        mViewPager.setCurrentItem(i, false);
    }

    public int getCurrentIndex() {
        return mViewPager.getCurrentItem();
    }

    public void setOnTabChangeListener(OnTabChangeListener listener) {
        mOnTabChangeListener = listener;
    }

    public interface OnTabChangeListener {
        void onTabChanged(int seleted);
    }


    /**
     * PagerAdapter subclass.
     */
    public static class TabPagerAdapter extends PagerAdapter {
        private List<ForecastPage> mList;

        public TabPagerAdapter(List<ForecastPage> viewList) {
            mList = viewList != null ? viewList : new ArrayList<>();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View item = mList.get(position);
            container.addView(item);
            return item;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return this.mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
