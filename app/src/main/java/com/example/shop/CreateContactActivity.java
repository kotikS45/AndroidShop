package com.example.shop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class CreateContactActivity extends BaseActivity implements View.OnClickListener {

    Button add, back;
    TextInputEditText email, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_contact);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        add = (Button) findViewById(R.id.btnAdd);
        back = (Button) findViewById(R.id.btnBack);
        email = (TextInputEditText) findViewById(R.id.inputEmail);
        name = (TextInputEditText) findViewById(R.id.inputName);

        add.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent resultIntent = new Intent();
        if (v.getId() == back.getId()) {
            setResult(Activity.RESULT_CANCELED, resultIntent);
            finish();
        }
        else if (v.getId() == add.getId()) {
            resultIntent.putExtra("email", email.getText().toString());
            resultIntent.putExtra("name", name.getText().toString());
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }
    }
}