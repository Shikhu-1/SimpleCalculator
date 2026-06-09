package com.example.simplecalculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNumber1;
    private EditText editTextNumber2;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNumber1 = findViewById(R.id.editTextNumber1);
        editTextNumber2 = findViewById(R.id.editTextNumber2);
        textViewResult = findViewById(R.id.textViewResult);

        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnSubtract = findViewById(R.id.btnSubtract);
        Button btnMultiply = findViewById(R.id.btnMultiply);
        Button btnDivide = findViewById(R.id.btnDivide);
        Button btnClear = findViewById(R.id.btnClear);

        btnAdd.setOnClickListener(v -> calculate('+'));
        btnSubtract.setOnClickListener(v -> calculate('-'));
        btnMultiply.setOnClickListener(v -> calculate('*'));
        btnDivide.setOnClickListener(v -> calculate('/'));

        btnClear.setOnClickListener(v -> {
            editTextNumber1.setText("");
            editTextNumber2.setText("");
            textViewResult.setText(getString(R.string.result_placeholder));
        });
    }

    private void calculate(char operator) {
        String s1 = editTextNumber1.getText().toString();
        String s2 = editTextNumber2.getText().toString();

        if (s1.isEmpty() || s2.isEmpty()) {
            textViewResult.setText(getString(R.string.error_empty_fields));
            return;
        }

        double num1, num2;
        try {
            num1 = Double.parseDouble(s1);
            num2 = Double.parseDouble(s2);
        } catch (NumberFormatException e) {
            textViewResult.setText(getString(R.string.error_invalid_input));
            return;
        }

        double result = 0;
        boolean error = false;

        switch (operator) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                if (num2 == 0) {
                    textViewResult.setText(getString(R.string.error_divide_by_zero));
                    error = true;
                } else {
                    result = num1 / num2;
                }
                break;
        }

        if (!error) {
            // Check if result is an integer to display nicely
            if (result == (long) result) {
                textViewResult.setText(String.format("Result: %d", (long) result));
            } else {
                textViewResult.setText(String.format("Result: %s", result));
            }
        }
    }
}
