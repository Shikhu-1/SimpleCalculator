package com.example.simplecalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // UI components
    private EditText editTextNumber1;
    private EditText editTextNumber2;
    private TextView textViewResult;
    private Button btnAdd, btnSubtract, btnMultiply, btnDivide, btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link UI components to Java variables
        editTextNumber1 = findViewById(R.id.editTextNumber1);
        editTextNumber2 = findViewById(R.id.editTextNumber2);
        textViewResult  = findViewById(R.id.textViewResult);
        btnAdd          = findViewById(R.id.btnAdd);
        btnSubtract     = findViewById(R.id.btnSubtract);
        btnMultiply     = findViewById(R.id.btnMultiply);
        btnDivide       = findViewById(R.id.btnDivide);
        btnClear        = findViewById(R.id.btnClear);

        // ── Addition ──────────────────────────────────────────
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double[] nums = getInputValues();
                if (nums == null) return;               // validation failed
                double result = nums[0] + nums[1];
                showResult(nums[0] + " + " + nums[1] + " = " + formatResult(result));
            }
        });

        // ── Subtraction ───────────────────────────────────────
        btnSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double[] nums = getInputValues();
                if (nums == null) return;
                double result = nums[0] - nums[1];
                showResult(nums[0] + " - " + nums[1] + " = " + formatResult(result));
            }
        });

        // ── Multiplication ────────────────────────────────────
        btnMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double[] nums = getInputValues();
                if (nums == null) return;
                double result = nums[0] * nums[1];
                showResult(nums[0] + " × " + nums[1] + " = " + formatResult(result));
            }
        });

        // ── Division ──────────────────────────────────────────
        btnDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double[] nums = getInputValues();
                if (nums == null) return;

                // Division by zero check
                if (nums[1] == 0) {
                    textViewResult.setText(getString(R.string.error_divide_by_zero));
                    textViewResult.setTextColor(getColor(R.color.clear_button));
                    return;
                }

                double result = nums[0] / nums[1];
                showResult(nums[0] + " ÷ " + nums[1] + " = " + formatResult(result));
            }
        });

        // ── Clear ─────────────────────────────────────────────
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextNumber1.setText("");
                editTextNumber2.setText("");
                textViewResult.setText(getString(R.string.result_placeholder));
                textViewResult.setTextColor(getColor(R.color.result_text));
            }
        });
    }

    /**
     * Reads both EditText fields and returns a double array [num1, num2].
     * Returns null and shows an error message if either field is empty or invalid.
     */
    private double[] getInputValues() {
        String str1 = editTextNumber1.getText().toString().trim();
        String str2 = editTextNumber2.getText().toString().trim();

        // Check for empty fields
        if (str1.isEmpty() || str2.isEmpty()) {
            textViewResult.setText(getString(R.string.error_empty_fields));
            textViewResult.setTextColor(getColor(R.color.clear_button));
            return null;
        }

        try {
            double num1 = Double.parseDouble(str1);
            double num2 = Double.parseDouble(str2);
            return new double[]{num1, num2};
        } catch (NumberFormatException e) {
            // Should not happen because inputType is numberDecimal, but handled for safety
            textViewResult.setText(getString(R.string.error_invalid_input));
            textViewResult.setTextColor(getColor(R.color.clear_button));
            return null;
        }
    }

    /**
     * Formats a double result — removes unnecessary decimal places.
     * e.g. 4.0 → "4",  4.5 → "4.5",  3.14159 → "3.14159"
     */
    private String formatResult(double value) {
        if (value == Math.floor(value) && !Double.isInfinite(value)) {
            return String.valueOf((long) value);   // whole number → no decimals
        }
        return String.valueOf(value);
    }

    /** Displays a result string in the result TextView with the normal result color. */
    private void showResult(String text) {
        textViewResult.setText(text);
        textViewResult.setTextColor(getColor(R.color.result_text));
    }
}
