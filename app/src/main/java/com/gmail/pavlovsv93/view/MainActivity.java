package com.gmail.pavlovsv93.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gmail.pavlovsv93.сalculator.Calculator;
import com.gmail.pavlovsv93.сalculator.CalculatorOperation;
import com.gmail.pavlovsv93.R;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements CalculatorViewInterface {

    private static final String ARG_THEME = "ARG_THEME";
    private TextView viewResult;
    private TextView viewHistory;
    private CalculatorPresenter presenter;
    private static int i = 0;
    private static boolean click = false;
    private int currentTheme = R.style.Theme_Calculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(getSaveTheme());
        setContentView(R.layout.activity_main);

        presenter = new CalculatorPresenter(this, new Calculator());

        viewResult = findViewById(R.id.view_result);
        viewHistory = findViewById(R.id.history);

        findViewById(R.id.key_dot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click = presenter.onDotPressed();
            }
        });

        findViewById(R.id.key_equally).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onOperationEqually();
                click = false;
                i = 0;
            }
        });

        HashMap<Integer, Integer> mapNumber = new HashMap<>();
        mapNumber.put(R.id.key_0, 0);
        mapNumber.put(R.id.key_1, 1);
        mapNumber.put(R.id.key_2, 2);
        mapNumber.put(R.id.key_3, 3);
        mapNumber.put(R.id.key_4, 4);
        mapNumber.put(R.id.key_5, 5);
        mapNumber.put(R.id.key_6, 6);
        mapNumber.put(R.id.key_7, 7);
        mapNumber.put(R.id.key_8, 8);
        mapNumber.put(R.id.key_9, 9);

        View.OnClickListener numClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click) {
                    i++;
                }
                presenter.onNumberPressed(mapNumber.get(v.getId()), click, i);

            }
        };

        findViewById(R.id.key_0).setOnClickListener(numClickListener);
        findViewById(R.id.key_1).setOnClickListener(numClickListener);
        findViewById(R.id.key_2).setOnClickListener(numClickListener);
        findViewById(R.id.key_3).setOnClickListener(numClickListener);
        findViewById(R.id.key_4).setOnClickListener(numClickListener);
        findViewById(R.id.key_5).setOnClickListener(numClickListener);
        findViewById(R.id.key_6).setOnClickListener(numClickListener);
        findViewById(R.id.key_7).setOnClickListener(numClickListener);
        findViewById(R.id.key_8).setOnClickListener(numClickListener);
        findViewById(R.id.key_9).setOnClickListener(numClickListener);

        HashMap<Integer, CalculatorOperation> mapOperation = new HashMap<>();
        mapOperation.put(R.id.key_summa, CalculatorOperation.SUM);
        mapOperation.put(R.id.key_minus, CalculatorOperation.SUB);
        mapOperation.put(R.id.key_multiply, CalculatorOperation.MULT);
        mapOperation.put(R.id.key_divide, CalculatorOperation.DIV);

        View.OnClickListener operationClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onOperationPressed(mapOperation.get(v.getId()));
                click = false;
                i = 0;
            }
        };

        findViewById(R.id.key_summa).setOnClickListener(operationClickListener);
        findViewById(R.id.key_minus).setOnClickListener(operationClickListener);
        findViewById(R.id.key_multiply).setOnClickListener(operationClickListener);
        findViewById(R.id.key_divide).setOnClickListener(operationClickListener);

        findViewById(R.id.key_plus_minus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onOperationPlusMinus(CalculatorOperation.SUMB);
            }
        });

        findViewById(R.id.key_clean).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.cleanCalculator();
                click = false;
                i = 0;

            }
        });

        findViewById(R.id.key_del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.deleteLastElement(click, i);
                if (click && i > 0) {
                    i--;
                } else {
                    click = false;
                }
            }
        });

        findViewById(R.id.setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getSaveTheme() == R.style.Theme_Calculator) {
                    saveTheme(R.style.Theme_Calculator_two);
                } else {
                    saveTheme(R.style.Theme_Calculator);
                }
                recreate();
            }
        });
    }

    private void saveTheme(int theme) {
        SharedPreferences sp = getSharedPreferences("Theme", Context.MODE_PRIVATE);
        sp.edit().putInt(ARG_THEME, theme).apply();
    }

    private int getSaveTheme() {
        SharedPreferences sp = getSharedPreferences("Theme", Context.MODE_PRIVATE);
        return sp.getInt(ARG_THEME, R.style.Theme_Calculator);
    }

    @Override
    public void showResult(String result) {
        viewResult.setText(result);
    }

    @Override
    public void showHistory(String history) {
        viewHistory.setText(history);
    }

    @Override
    public String getHistory() {
        return String.valueOf(viewHistory.getText());
    }
}