package com.gmail.pavlovsv93.Calculator;

public class Calculator implements CalculatorInterface {
    @Override
    public double resultOperation(double a, double b, CalculatorOperation operation) {
        switch (operation) {
            case SUM:
                return a + b;
            case MULT:
            case SUMB:
                return a * b;
            case SUB:
                return a - b;
            case DIV:
                return a / b;
        }
        return 0;
    }
}
