package com.example.weatherapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;

public class AppUtil {

    /** Get the related Activity from a View Context. */
    public static Activity getActivity(Context context) {
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity)
                return (Activity) context;

            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }
}
