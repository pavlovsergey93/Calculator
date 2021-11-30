package com.gmail.pavlovsv93.сalculator;

import androidx.annotation.StringRes;

public enum CalculatorOperation {
    SUM('+'),
    SUB('-'),
    MULT('*'),
    DIV('÷'),
    SUMB('±'),
    EQU('=');

    @StringRes
    private int operation;

    CalculatorOperation(int operation) {
        this.operation = operation;
    }
}
