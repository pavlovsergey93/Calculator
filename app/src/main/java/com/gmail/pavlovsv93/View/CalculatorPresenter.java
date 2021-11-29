package com.gmail.pavlovsv93.View;

import android.widget.TextView;

import com.gmail.pavlovsv93.Calculator.CalculatorInterface;
import com.gmail.pavlovsv93.Calculator.CalculatorOperation;
import com.gmail.pavlovsv93.Stack.MyStack;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.xml.transform.sax.SAXResult;

public class CalculatorPresenter {

    private CalculatorViewInterface viewInterface;
    private CalculatorInterface calculatorInterface;
    private MyStack<Integer> stack = new MyStack<>();
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

    public void onNumberPressed(int number, boolean click, int i) {
        stack.push(number);
        if (previousOperator == CalculatorOperation.EQU) {
            a = 0.0;
            b = null;
            previousOperator = null;
        }
        if (!click) {
            if (previousOperator != null) {
                b = Double.valueOf(b * 10 + number);
                viewInterface.showResult(String.valueOf(b));
            } else {
                a = a * 10 + number;
                viewInterface.showResult(String.valueOf(a));
            }
        } else {
            if (previousOperator != null) {
                b = round((b + (number / Math.pow(10, i))), i);
                if (number == 0) {
                    viewInterface.showResult(String.valueOf(b) + "0");
                } else {
                    viewInterface.showResult(String.valueOf(b));
                }
            } else {
                a = round((a + (number / Math.pow(10, i))), i);
                if (number == 0 && i != 1) {
                    viewInterface.showResult(String.valueOf(a) + "0");
                } else {
                    viewInterface.showResult(String.valueOf(a));
                }
            }
        }
    }

    public void onOperationEqually() {
        if (b != null && previousOperator != null && previousOperator != CalculatorOperation.EQU) {
            Double result = calculatorInterface.resultOperation(a, b, getPreviousOperator());
            String str = String.valueOf(a) + " " + convert(previousOperator) + " " + String.valueOf(b) + " = " + result;
            a = result;
            showHistoryView(str);
            viewInterface.showResult(String.valueOf(a));
            previousOperator = CalculatorOperation.EQU;
            b = 0.0;
            stack.clean();

        }
    }

    public void onOperationPlusMinus(CalculatorOperation operation) {
        if (previousOperator != null) {
            b = calculatorInterface.resultOperation(Double.valueOf(b), -1, operation);
            viewInterface.showResult(String.valueOf(b));
        } else {
            a = calculatorInterface.resultOperation(a, -1, operation);
            viewInterface.showResult(String.valueOf(a));
        }
    }

    public void onOperationPressed(CalculatorOperation operation) {
        if (operation == CalculatorOperation.EQU || operation == previousOperator) {
            viewInterface.showResult(String.valueOf(a));
        } else if (previousOperator != null && previousOperator != CalculatorOperation.EQU) {
            showHistoryView(convert(previousOperator));
            a = calculatorInterface.resultOperation(a, b, previousOperator);
            showHistoryView(String.valueOf(b));
            b = 0.0;
        } else {
            if (String.valueOf(viewInterface.getHistory()) == "") {
                showHistoryView(String.valueOf(a));
            }
            b = 0.0;
        }
        previousOperator = operation;
        viewInterface.showResult(String.valueOf(a));
        stack.clean();
    }

    public void cleanCalculator() {
        a = 0.0;
        b = null;
        previousOperator = null;
        stack.clean();
        viewInterface.showHistory(null);
        viewInterface.showResult(String.valueOf(a));
    }

    public void deleteLastElement(boolean click, int i) {
        if (click && !stack.isEmpty() && i > 0) {
            if (previousOperator != null) {
                b = round((b - stack.pop() / Math.pow(10, i)), i);
                viewInterface.showResult(String.valueOf(b));
            } else {
                a = round((a - stack.pop() / Math.pow(10, i)), i);
                viewInterface.showResult(String.valueOf(a));
            }
        } else if (!stack.isEmpty()) {
            if (previousOperator != null) {
                b = (b - stack.pop()) / 10;
                viewInterface.showResult(String.valueOf(b));
            } else {
                a = (a - stack.pop()) / 10;
                viewInterface.showResult(String.valueOf(a));
            }
        }
    }

    private void showHistoryView(String str) {
        str = viewInterface.getHistory() + " " + str + "\n";
        viewInterface.showHistory(str);
    }

    private String convert(CalculatorOperation operation) {
        String str = null;
        if (CalculatorOperation.SUM == operation) {
            str = "+";
        } else if (CalculatorOperation.SUB == operation) {
            str = "-";
        } else if (CalculatorOperation.MULT == operation) {
            str = "*";
        } else if (CalculatorOperation.DIV == operation) {
            str = "÷";
        }
        return str;
    }

    private double round(Double ch, int i) {
        if (i<0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(Double.toString(ch));
        bd = bd.setScale(i, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
