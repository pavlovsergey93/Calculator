package com.gmail.pavlovsv93.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gmail.pavlovsv93.Calculator.Calculator;
import com.gmail.pavlovsv93.Calculator.CalculatorOperation;
import com.gmail.pavlovsv93.R;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements CalculatorViewInterface {

    private TextView viewResult;
    private TextView viewHistory;
    private CalculatorPresenter presenter;
    public static int i = 0;
    public static boolean click = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                presenter.onOperationPressed(presenter.getPreviousOperator());
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
                    presenter.onNumberDiPressed(mapNumber.get(v.getId()), i);
                    return;
                }
                presenter.onNumberPressed(mapNumber.get(v.getId()));

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
        mapOperation.put(R.id.key_plus_minus, CalculatorOperation.SUMB);

        View.OnClickListener operationClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onOperationPressed(mapOperation.get(v.getId()));
                click = false;
            }
        };

        findViewById(R.id.key_summa).setOnClickListener(operationClickListener);
        findViewById(R.id.key_minus).setOnClickListener(operationClickListener);
        findViewById(R.id.key_multiply).setOnClickListener(operationClickListener);
        findViewById(R.id.key_divide).setOnClickListener(operationClickListener);
        findViewById(R.id.key_plus_minus).setOnClickListener(operationClickListener);

        findViewById(R.id.key_clean).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.cleanCalculator();
            }
        });

        findViewById(R.id.key_del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.deleteLastElement();
            }
        });

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