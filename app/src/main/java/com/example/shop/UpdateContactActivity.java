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

import com.example.shop.dto.Contact;
import com.google.android.material.textfield.TextInputEditText;

public class UpdateContactActivity extends BaseActivity implements View.OnClickListener {

    Button update, back;
    TextInputEditText email, name;
    Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_contact);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        update = (Button) findViewById(R.id.btnUpdate);
        back = (Button) findViewById(R.id.btnBack);
        email = (TextInputEditText) findViewById(R.id.inputEmail);
        name = (TextInputEditText) findViewById(R.id.inputName);

        update.setOnClickListener(this);
        back.setOnClickListener(this);

        Intent intent = getIntent();
        contact = intent.getParcelableExtra("contact");
        if (contact != null){
            email.setText(contact.getEmail());
            name.setText(contact.getName());
        }
    }

    @Override
    public void onClick(View v) {
        Intent resultIntent = new Intent();
        if (v.getId() == back.getId()) {
            setResult(Activity.RESULT_CANCELED, resultIntent);
            finish();
        }
        else if (v.getId() == update.getId()) {
            contact.setEmail(email.getText().toString());
            contact.setName(name.getText().toString());
            resultIntent.putExtra("contact", contact);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }
    }
}