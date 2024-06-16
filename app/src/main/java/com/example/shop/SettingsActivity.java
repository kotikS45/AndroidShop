package com.example.shop;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SettingsActivity extends BaseActivity implements View.OnClickListener {

    LinearLayout layout;
    RadioGroup rg;
    TextView text;
    Button btnCreate;
    Button btnClear;
    int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        layout = (LinearLayout) findViewById(R.id.ll2);
        rg = (RadioGroup) findViewById(R.id.rgGravity);
        text = (TextView) findViewById(R.id.etName);

        btnCreate = (Button) findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(this);

        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btnCreate){
            LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(wrapContent, wrapContent);

            if (rg.getCheckedRadioButtonId() == R.id.btnLeft){
                lParams.gravity = Gravity.START;
            }
            else if (rg.getCheckedRadioButtonId() == R.id.btnCenter){
                lParams.gravity = Gravity.CENTER;
            }
            else if (rg.getCheckedRadioButtonId() == R.id.btnRight){
                lParams.gravity = Gravity.END;
            }

            Button btnNew = new Button(this);
            String str = text.getText().toString();
            btnNew.setText(str);
            layout.addView(btnNew, lParams);
        }
        else if (id == R.id.btnClear){
            layout.removeAllViews();
            Toast.makeText(this, R.string.remove, Toast.LENGTH_SHORT).show();
        }
    }
}