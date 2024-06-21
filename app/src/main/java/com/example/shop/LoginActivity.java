package com.example.shop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class LoginActivity extends BaseActivity {

    Button button;
    TextInputEditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intent = getIntent();

        button = (Button) findViewById(R.id.button);
        email = (TextInputEditText) findViewById(R.id.inputEmail);
        String emailStr = intent.getStringExtra("email");
        if (emailStr != null){
            email.setText(emailStr);
        }

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAuthorized(true);
                invalidateOptionsMenu();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("email", email.getText().toString());
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        };

        button.setOnClickListener(listener);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected Intent mSignUp (Intent i) {
        i = super.mSignUp(i);
        i.putExtra("email", Objects.requireNonNull(email.getText()).toString());
        return i;
    }
}