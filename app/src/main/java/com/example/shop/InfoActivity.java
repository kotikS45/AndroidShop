package com.example.shop;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class InfoActivity extends BaseActivity implements SeekBar.OnSeekBarChangeListener {

    SeekBar sb;
    Button b1;
    Button b2;

    LinearLayout.LayoutParams params1;
    LinearLayout.LayoutParams params2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sb = (SeekBar) findViewById(R.id.sbWeight);
        b1 = (Button) findViewById(R.id.infoButton1);
        b2 = (Button) findViewById(R.id.infoButton2);

        params1 = (LinearLayout.LayoutParams) b1.getLayoutParams();
        params2 = (LinearLayout.LayoutParams) b2.getLayoutParams();

        sb.setOnSeekBarChangeListener(this);

        b1.setText(R.string.number_50);
        b2.setText(R.string.number_50);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        params1.weight = progress;
        params2.weight = sb.getMax() - progress;

        b1.setText(String.valueOf(progress));
        b2.setText(String.valueOf(sb.getMax() - progress));

        b1.requestLayout();
        b2.requestLayout();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}