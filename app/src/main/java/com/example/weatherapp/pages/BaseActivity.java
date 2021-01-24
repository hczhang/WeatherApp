package com.example.weatherapp.pages;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weatherapp.R;

/**
 * A base activity to handle some common operations.
 */
public class BaseActivity extends AppCompatActivity {

    private ProgressDialog mProgress;

    protected void setupKeyboard(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard();
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupKeyboard(innerView);
            }
        }
    }

    /** Hides keyboard */
    protected void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    /** Shows ProgressDialog. */
    protected void showProgress() {
        if (mProgress == null) {
            mProgress = new ProgressDialog(this, R.style.ProgressTheme);
            mProgress.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            mProgress.setCancelable(false);
        }

        if (mProgress.isShowing()) return;

        mProgress.show();
    }

    /** Dismisses ProgressDialog. */
    protected void dismissProgress() {
        if (mProgress == null || !mProgress.isShowing()) return;

        mProgress.dismiss();
    }
}
