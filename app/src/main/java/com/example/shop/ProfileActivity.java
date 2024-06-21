package com.example.shop;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.shop.utils.ImageWorker;

public class ProfileActivity extends BaseActivity implements View.OnClickListener {

    ConstraintLayout loginLayout, profileLayout;
    Button btnLogin, btnProfileEmail;
    ImageView image;
    SharedPreferences sPref;
    private ActivityResultLauncher<Intent> getResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loginLayout = (ConstraintLayout) findViewById(R.id.loginLayout);
        profileLayout = (ConstraintLayout) findViewById(R.id.profileLayout);
        btnLogin = (Button) findViewById(R.id.btnAuth);
        btnProfileEmail = (Button) findViewById(R.id.btnProfileEmail);
        image = (ImageView) findViewById(R.id.image);

        btnLogin.setOnClickListener(this);

        getResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            String value = data.getStringExtra("email");
                            btnProfileEmail.setText(value);
                        }
                    }
                }
        );
    }

    @Override
    protected void onResume(){
        super.onResume();
        if (getAuthorized()) {
            loginLayout.setVisibility(View.GONE);
            profileLayout.setVisibility(View.VISIBLE);

            sPref = getSharedPreferences("data", MODE_PRIVATE);
            String base64 = sPref.getString("imageBase64", "");
            Bitmap bitmap = ImageWorker.decodeBase64ToBitmap(base64);

            if (bitmap != null){
                image.setImageBitmap(bitmap);
            }
        }
        else {
            loginLayout.setVisibility(View.VISIBLE);
            profileLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnLogin.getId()){
            getResult.launch(mSignIn(new Intent()));
        }
    }
}