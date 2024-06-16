package com.example.shop;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class RegisterActivity extends BaseActivity {

    ImageView image;
    Button selectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);

        image = (ImageView) findViewById(R.id.ivRegImage);
        selectButton = (Button) findViewById(R.id.button);
        //String url = "http://192.168.0.111:5129/images/img.png";
        String url = "https://uxwing.com/wp-content/themes/uxwing/download/peoples-avatars/corporate-user-icon.png";
        Glide.with(this)
                .load(url)
                .apply(new RequestOptions().override(400))
                .into(image);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent()
                        .setType("*/*")
                        .setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select a file"), 123);
            }
        };

        selectButton.setOnClickListener(listener);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 123 && resultCode == RESULT_OK) {
            Uri selectedfile = data.getData();
            Glide.with(this)
                    .load(selectedfile)
                    .apply(new RequestOptions().override(400))
                    .into(image);

        }
    }
}