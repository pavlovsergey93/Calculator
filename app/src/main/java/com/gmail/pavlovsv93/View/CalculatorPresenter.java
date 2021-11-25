package com.gmail.pavlovsv93.View;

import android.widget.TextView;

import com.gmail.pavlovsv93.Calculator.CalculatorInterface;
import com.gmail.pavlovsv93.Calculator.CalculatorOperation;
import com.gmail.pavlovsv93.Stack.MyStack;

import java.math.RoundingMode;

import javax.xml.transform.sax.SAXResult;

public class CalculatorPresenter {

    private CalculatorViewInterface viewInterface;
    private CalculatorInterface calculatorInterface;
    private MyStack<Integer> stack = new MyStack<>();
    private MyStack<Double> stackDi = new MyStack<>();
    private Double a = 0.0;
    private Double b = null;
    private CalculatorOperation previousOperator = null;

    public CalculatorOperation getPreviousOperator() {
        return previousOperator;
    }

    public CalculatorPresenter(CalculatorViewInterface viewInterface, CalculatorInterface calculatorInterface) {
        this.viewInterface = viewInterface;
        this.calculatorInterface = calculatorInterface;
    }

    public boolean onDotPressed() {
        return true;
    }

    public void onNumberPressed(int number) {
        stack.push(number);
        if (previousOperator != null) {
            b = Double.valueOf(b * 10 + number);
            viewInterface.showResult(String.valueOf(b));
        } else {
            a = a * 10 + number;
            viewInterface.showResult(String.valueOf(a));
        }
    }

    public void onNumberDiPressed(int number, int i) {
        stackDi.push(Double.valueOf(number));
        if (previousOperator != null) {
            b = Double.valueOf(b + (number / Math.pow(10, i)));
            Math.round(b * Math.pow(10, i));
            viewInterface.showResult(String.valueOf(b));
        } else {
            a = a + (number / Math.pow(10, i));
            Math.round(a * Math.pow(10, i));
            viewInterface.showResult(String.valueOf(a));
        }
    }

    public void onOperationPressed(CalculatorOperation operation) {
        if (operation == null) {
            viewInterface.showResult(String.valueOf(a));
        } else if (clickSuMB(operation)) {
            if (b != null) {
                b = calculatorInterface.resultOperation(Double.valueOf(b), -1, operation);
                viewInterface.showResult(String.valueOf(b));
            } else {
                a = calculatorInterface.resultOperation(a, -1, operation);
                viewInterface.showResult(String.valueOf(a));
            }
        } else {
            if (b != null && previousOperator != null) {
                showHistoryView(String.valueOf(previousOperator));
                a = calculatorInterface.resultOperation(a, b, previousOperator);
                showHistoryView(String.valueOf(b));
                b = null;
            } else {
                b = 0.0;
                if (viewInterface.getHistory() == "") {
                    showHistoryView(String.valueOf(a));
                }
            }
            previousOperator = operation;
            viewInterface.showResult(String.valueOf(a));
        }
        metod();
    }

    private boolean clickSuMB(CalculatorOperation operation) {
        if (CalculatorOperation.SUMB == operation) {
            return true;
        }
        return false;
    }

    public void cleanCalculator() {
        a = 0.0;
        b = null;
        previousOperator = null;
        metod();
        viewInterface.showHistory(null);
        viewInterface.showResult(String.valueOf(a));
    }

    public void deleteLastElement() {
        if (stackDi.size() == 1) {
            MainActivity.click = false;
        }
        if (!stackDi.isEmpty()) {
            if (previousOperator != null) {
                b = (b - stackDi.peek() / Math.pow(10, stackDi.size() + 1));
                MainActivity.i--;
                viewInterface.showResult(String.valueOf(b));
            } else {
                a = (a - stackDi.peek() / Math.pow(10, stackDi.size() + 1));
                MainActivity.i--;
                viewInterface.showResult(String.valueOf(a));
            }
        } else if (!stack.isEmpty()) {
            if (previousOperator != null) {
                b = (b - stack.peek()) / 10;
                viewInterface.showResult(String.valueOf(b));
            } else {
                a = (a - stack.peek()) / 10;
                viewInterface.showResult(String.valueOf(a));
            }
        }
    }

    private void metod() {
        stack.clean();
        stackDi.clean();
        //MainActivity.click = false;
        MainActivity.i = 0;
    }

    private void showHistoryView(String str) {
        if ("SUM" == str) {
            str = "+";
        } else if ("SUB" == str) {
            str = "-";
        } else if ("MULT" == str) {
            str = "*";
        } else if ("DIV" == str) {
            str = "/";
        }
        str = viewInterface.getHistory() + " " + str + "\n";
        viewInterface.showHistory(str);
    }
}
