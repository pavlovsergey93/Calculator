package com.gmail.pavlovsv93.view;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.gmail.pavlovsv93.stack.MyStack;
import com.gmail.pavlovsv93.сalculator.CalculatorInterface;
import com.gmail.pavlovsv93.сalculator.CalculatorOperation;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculatorPresenter {

    private static final String ARG_SAVE = "ARG_SAVE";
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
        if (i < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(Double.toString(ch));
        bd = bd.setScale(i, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public void onSaveState(Bundle bundle) {
        bundle.putParcelable(ARG_SAVE, new SaveState(a, b, previousOperator, viewInterface.getHistory(), stack));
    }

    public void resState(Bundle bundle) {
        SaveState stata = bundle.getParcelable(ARG_SAVE);

        a = stata.a;
        b = stata.b;
        previousOperator = stata.previousOperator;
        String historyStr = stata.history;
        viewInterface.showHistory(historyStr);
        if (b != null && previousOperator != CalculatorOperation.EQU) {
            viewInterface.showResult(String.valueOf(b));
        } else {
            viewInterface.showResult(String.valueOf(a));
        }
        stack = stata.stack;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    public class SaveState implements Parcelable {
        private Double a;
        private Double b;
        private CalculatorOperation previousOperator;
        private String history;
        private MyStack<Integer> stack;

        protected SaveState(Parcel in) {
            if (in.readByte() == 0) {
                a = null;
            } else {
                a = in.readDouble();
            }
            if (in.readByte() == 0) {
                b = null;
            } else {
                b = in.readDouble();
            }
            history = in.readString();
        }

        public final Creator<SaveState> CREATOR = new Creator<SaveState>() {
            @Override
            public SaveState createFromParcel(Parcel in) {
                return new SaveState(in);
            }

            @Override
            public SaveState[] newArray(int size) {
                return new SaveState[size];
            }
        };

        public SaveState(Double a, Double b, CalculatorOperation previousOperator, String history, MyStack<Integer> stack) {
            this.a = a;
            this.b = b;
            this.previousOperator = previousOperator;
            this.history = history;
            this.stack = stack;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            if (a == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeDouble(a);
            }
            if (b == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeDouble(b);
            }
            dest.writeString(history);
        }

        public Double getA() {
            return a;
        }

        public Double getB() {
            return b;
        }

        public CalculatorOperation getPreviousOperator() {
            return previousOperator;
        }

        public String getHistory() {
            return history;
        }
    }
}
