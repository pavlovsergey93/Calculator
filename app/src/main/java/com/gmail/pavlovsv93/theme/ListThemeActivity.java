package com.gmail.pavlovsv93.theme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.gmail.pavlovsv93.R;

public class ListThemeActivity extends AppCompatActivity {

    public static final String EXTRA_THEME = "EXTRA_THEME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_theme);

        Intent launch = getIntent();
//        if(launch.hasExtra(EXTRA_THEME)){

//        }
        Theme themeSelect = (Theme) launch.getSerializableExtra(EXTRA_THEME);

        LinearLayout themeContainer = findViewById(R.id.ll_themes);

        for (Theme theme : Theme.values()) {

            View view = LayoutInflater.from(this).inflate(R.layout.item_theme, themeContainer, false);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent data = new Intent();
                    data.putExtra(EXTRA_THEME, theme);
                    setResult(Activity.RESULT_OK, data);

                    finish();
                }
            });

            TextView titleTextTheme = view.findViewById(R.id.theme_item_id);
            titleTextTheme.setText(theme.getName());

            ImageView check = view.findViewById(R.id.check_ic);
            if (theme.equals(themeSelect)) {
                check.setVisibility(View.VISIBLE);
            } else {
                check.setVisibility(View.GONE);
            }

            themeContainer.addView(view);
        }
    }
}