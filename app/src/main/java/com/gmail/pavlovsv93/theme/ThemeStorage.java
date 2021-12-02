package com.gmail.pavlovsv93.theme;

import android.content.Context;
import android.content.SharedPreferences;

public class ThemeStorage {

    private static final String ARG_THEME = "ARG_THEME";

    private Context context;

    public ThemeStorage(Context context) {
        this.context = context;
    }


    public void saveTheme(Theme theme) {
        SharedPreferences sp = context.getSharedPreferences("Theme", Context.MODE_PRIVATE);

        sp.edit()
                .putInt(ARG_THEME, theme.getKey())
                .apply();
    }

    public Theme getSaveTheme() {
        SharedPreferences sp = context.getSharedPreferences("Theme", Context.MODE_PRIVATE);
        int key = sp.getInt(ARG_THEME, Theme.ONE.getKey());
        for (Theme theme : Theme.values()) {
            if (key == theme.getKey()) {
                return theme;
            }
        }
        return Theme.ONE;
    }
}
