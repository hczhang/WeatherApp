package com.example.weatherapp.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.weatherapp.R;


/**
 */
public class SimpleSearchBar extends LinearLayout {

    private EditText etInput;
    private ImageView ivClear, ivSearch;

    private OnSearchActionListener listener;

    public interface OnSearchActionListener {
        void onSearchConfirmed(CharSequence text);
    }

    /** Constructor from xml */
    public SimpleSearchBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        initEvents();
    }

    private void initView() {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        inflate(getContext(), R.layout.widget_simple_searchbar, this);
        etInput = findViewById(R.id.et_input);
        ivClear = findViewById(R.id.iv_clear);
        ivSearch = findViewById(R.id.iv_search);
    }

    private void initEvents() {
        // Hides keyboard and make a search.
        etInput.setOnEditorActionListener((view, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (listener != null) listener.onSearchConfirmed(getText());
                return true;
            }
            return false;
        });

        // Disables ENTER key.
        etInput.setOnKeyListener((view, keyCode, event) -> keyCode == KeyEvent.KEYCODE_ENTER);

        // Clears input.
        ivClear.setOnClickListener(view -> {etInput.setText(null);});

        // Hides keyboard and make a search.
        ivSearch.setOnClickListener(view -> {
            if (listener != null) listener.onSearchConfirmed(getText());
        });
    }

    public void requestInputFocus() {
        etInput.requestFocus();
    }

    public void setOnSearchActionListener(OnSearchActionListener listener) {
        this.listener = listener;
    }

    public CharSequence getText() {
        return etInput.getText();
    }

    public void setText(CharSequence text) {
        etInput.setText(text);
    }

}