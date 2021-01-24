package com.example.weatherapp;

import android.app.Application;

/**
 * Application subclass.
 */
public class AAplication extends Application {
    private static AAplication INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }

    /**
     * Uses the application context occasionally for ease of decoupling.
     */
    public static AAplication getInstance() {
        return INSTANCE;
    }
}
