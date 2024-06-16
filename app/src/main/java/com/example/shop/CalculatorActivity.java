package com.example.shop;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CalculatorActivity extends BaseActivity implements View.OnClickListener {

    EditText etNum1, etNum2;
    Button btnAdd, btnSub, btnMult, btnDiv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculator);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etNum1 = (EditText) findViewById(R.id.calcNum1);
        etNum2 = (EditText) findViewById(R.id.calcNum2);

        btnAdd = (Button) findViewById(R.id.calcPlus);
        btnSub = (Button) findViewById(R.id.calcMinus);
        btnMult = (Button) findViewById(R.id.calcMultipl);
        btnDiv = (Button) findViewById(R.id.calcDivision);

        btnAdd.setOnClickListener(this);
        btnSub.setOnClickListener(this);
        btnMult.setOnClickListener(this);
        btnDiv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        float num1 = 0;
        float num2 = 0;
        float result = 0;

        if (TextUtils.isEmpty(etNum1.getText().toString())
            || TextUtils.isEmpty(etNum2.getText().toString())){
            return;
        }

        num1 = Float.parseFloat(etNum1.getText().toString());
        num2 = Float.parseFloat(etNum2.getText().toString());

        if (v.getId() == R.id.calcPlus) {
            result = num1 + num2;
        }
        else if (v.getId() == R.id.calcMinus) {
            result = num1 - num2;
        }
        else if (v.getId() == R.id.calcMultipl) {
            result = num1 * num2;
        }
        else if (v.getId() == R.id.calcDivision) {
            if (num2 == 0) {
                Toast.makeText(this, "Error: division by 0", Toast.LENGTH_SHORT).show();
                return;
            }
            result = num1 / num2;
        }

        etNum1.setText(String.valueOf(result));
        etNum2.setText("");
    }
}