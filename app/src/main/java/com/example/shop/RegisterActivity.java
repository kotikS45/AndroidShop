package com.example.shop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.shop.page.ViewPagerAdapter;
import com.example.shop.utils.ImageWorker;
import com.example.shop.utils.PasswordUtils;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    ImageView image;
    Button button;
    TextInputEditText email, fullName, phone, password, passwordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);



        Intent intent = getIntent();

        image = (ImageView) findViewById(R.id.image);
        button = (Button) findViewById(R.id.button);
        fullName = (TextInputEditText) findViewById(R.id.inputFullname);
        phone = (TextInputEditText) findViewById(R.id.inputPhone);
        email = (TextInputEditText) findViewById(R.id.inputEmail);
        password = (TextInputEditText) findViewById(R.id.inputPassword);
        passwordConfirm = (TextInputEditText) findViewById(R.id.inputPasswordConfirm);


//        String url = "http://192.168.0.111:5129/images/img.png";
//        String url = "https://uxwing.com/wp-content/themes/uxwing/download/peoples-avatars/corporate-user-icon.png";
//        Glide.with(this)
//                .load(url)
//                .apply(new RequestOptions().override(400))
//                .into(image);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent()
                        .setType("*/*")
                        .setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select a file"), 123);
            }
        };

        image.setOnClickListener(listener);

        String emailStr = intent.getStringExtra("email");
        if (emailStr != null){
            email.setText(emailStr);
        }

        button.setOnClickListener(this);

        loadAvatar();
    }

    private void loadAvatar() {
        try {
            FileInputStream fis = openFileInput("avatar.webp");
            Bitmap bitmap = BitmapFactory.decodeStream(fis);
            image.setImageBitmap(bitmap);
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123 && resultCode == RESULT_OK) {
            Uri selectedfile = data.getData();
            Glide.with(this)
                    .load(selectedfile)
                    .apply(new RequestOptions().override(400))
                    .into(image);

        }
    }

    @Override
    protected Intent mSignIn (Intent i) {
        i = super.mSignIn(i);
        i.putExtra("email", Objects.requireNonNull(email.getText()).toString());
        return i;
    }

    @Override
    public void onClick(View v) {
        if (email.getText().toString().isEmpty()) {
            Toast.makeText(this, "Incorrect email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (fullName.getText().toString().isEmpty()){
            Toast.makeText(this, "Incorrect name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (phone.getText().toString().isEmpty()){
            Toast.makeText(this, "Incorrect phone", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.getText().toString().isEmpty()){
            Toast.makeText(this, "Incorrect password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (passwordConfirm.getText().toString().isEmpty() || !passwordConfirm.getText().toString().equals(password.getText().toString())){
            Toast.makeText(this, "Incorrect password confirm", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences sPref = getSharedPreferences("data", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();

        ed.putString("email", email.getText().toString());
        ed.putString("fullname", fullName.getText().toString());
        ed.putString("phone", phone.getText().toString());

        String salt = PasswordUtils.generateSalt();
        String hashedPassword = PasswordUtils.hashPassword(password.getText().toString(), salt);

        ed.putString("hashedPassword", hashedPassword);
        ed.putString("salt", salt);

        image.setDrawingCacheEnabled(true);
        image.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ed.putString("imageBase64", ImageWorker.encodeImageToBase64(bitmap));

        ed.apply();

        finish();
    }
}