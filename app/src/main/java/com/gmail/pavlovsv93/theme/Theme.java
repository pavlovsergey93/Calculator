package com.gmail.pavlovsv93.theme;

import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;

import com.gmail.pavlovsv93.R;


public enum Theme {

    ONE(R.style.Theme_Calculator, R.string.theme_one, "one"),
    TWO(R.style.Theme_Calculator_two, R.string.theme_two, "two"),
    THREE(R.style.Theme_Calculator_three, R.string.theme_three, "three");

    @StringRes
    private final int name;

    @StyleRes
    private final int theme;

    private String key;

    Theme(int theme, int name, String key) {
        this.name = name;
        this.theme = theme;
        this.key = key;
    }

    public int getName() {
        return name;
    }

    public int getTheme() {
        return theme;
    }
    public String getKey() {
        return key;
    }
}
