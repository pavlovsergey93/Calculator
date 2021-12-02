package com.gmail.pavlovsv93.theme;

import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;

import com.gmail.pavlovsv93.R;


public enum Theme {

    ONE(R.style.Theme_Calculator, R.string.theme_one, 1),
    TWO(R.style.Theme_Calculator_two, R.string.theme_two, 2),
    THREE(R.style.Theme_Calculator_three, R.string.theme_three, 3);

    @StringRes
    private final int name;

    @StyleRes
    private final int theme;

    private int key;

    Theme(int theme, int name, int key) {
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
    public int getKey() {
        return key;
    }
}
