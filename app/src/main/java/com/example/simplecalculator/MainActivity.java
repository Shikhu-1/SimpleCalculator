package com.example.simplecalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textViewDisplay;
    private StringBuilder currentInput = new StringBuilder();
    private Double firstOperand = null;
    private String operator = null;
    private boolean isNewOp = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewDisplay = findViewById(R.id.textViewDisplay);

        // Numeric Buttons
        int[] numericButtons = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnDot
        };

        View.OnClickListener listener = v -> {
            Button b = (Button) v;
            if (isNewOp) {
                currentInput.setLength(0);
                isNewOp = false;
            }
            currentInput.append(b.getText().toString());
            textViewDisplay.setText(currentInput.toString());
        };

        for (int id : numericButtons) {
            findViewById(id).setOnClickListener(listener);
        }

        // Operator Buttons
        findViewById(R.id.btnAdd).setOnClickListener(v -> setOperator("+"));
        findViewById(R.id.btnSubtract).setOnClickListener(v -> setOperator("-"));
        findViewById(R.id.btnMultiply).setOnClickListener(v -> setOperator("*"));
        findViewById(R.id.btnDivide).setOnClickListener(v -> setOperator("/"));

        findViewById(R.id.btnEquals).setOnClickListener(v -> calculate());

        findViewById(R.id.btnClear).setOnClickListener(v -> {
            currentInput.setLength(0);
            firstOperand = null;
            operator = null;
            isNewOp = true;
            textViewDisplay.setText("0");
        });

        findViewById(R.id.btnBackspace).setOnClickListener(v -> {
            if (currentInput.length() > 0) {
                currentInput.deleteCharAt(currentInput.length() - 1);
                if (currentInput.length() == 0) {
                    textViewDisplay.setText("0");
                } else {
                    textViewDisplay.setText(currentInput.toString());
                }
            }
        });
    }

    private void setOperator(String op) {
        if (currentInput.length() > 0) {
            firstOperand = Double.parseDouble(currentInput.toString());
            operator = op;
            isNewOp = true;
        }
    }

    private void calculate() {
        if (operator != null && currentInput.length() > 0) {
            double secondOperand = Double.parseDouble(currentInput.toString());
            double result = 0;
            switch (operator) {
                case "+": result = firstOperand + secondOperand; break;
                case "-": result = firstOperand - secondOperand; break;
                case "*": result = firstOperand * secondOperand; break;
                case "/":
                    if (secondOperand != 0) {
                        result = firstOperand / secondOperand;
                    } else {
                        textViewDisplay.setText("Error");
                        isNewOp = true;
                        return;
                    }
                    break;
            }
            textViewDisplay.setText(formatResult(result));
            currentInput.setLength(0);
            currentInput.append(result);
            isNewOp = true;
            operator = null;
        }
    }

    private String formatResult(double value) {
        if (value == Math.floor(value) && !Double.isInfinite(value)) {
            return String.valueOf((long) value);
        }
        return String.valueOf(value);
    }
}
