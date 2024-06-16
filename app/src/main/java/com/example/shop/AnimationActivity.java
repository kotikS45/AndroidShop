package com.example.shop;

import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AnimationActivity extends BaseActivity {

    final int MENU_ALPHA_ID = 1;
    final int MENU_SCALE_ID = 2;
    final int MENU_TRANSLATE_ID = 3;
    final int MENU_ROTATE_ID = 4;
    final int MENU_COMBO_ID = 5;

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_animation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tv = (TextView) findViewById(R.id.tv);
        registerForContextMenu(tv);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.tv) {
            menu.add(0, MENU_ALPHA_ID, 0, "Alpha");
            menu.add(0, MENU_SCALE_ID, 0, "Scale");
            menu.add(0, MENU_TRANSLATE_ID, 0, "Translate");
            menu.add(0, MENU_ROTATE_ID, 0, "Rotate");
            menu.add(0, MENU_COMBO_ID, 0, "Combo");
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        Animation anim = null;

        if (item.getItemId() == MENU_ALPHA_ID) {
            anim = AnimationUtils.loadAnimation(this, R.anim.myalpha);
        }
        else if (item.getItemId() == MENU_SCALE_ID) {
            anim = AnimationUtils.loadAnimation(this, R.anim.myscale);
        }
        else if (item.getItemId() == MENU_TRANSLATE_ID) {
            anim = AnimationUtils.loadAnimation(this, R.anim.mytranslate);
        }
        else if (item.getItemId() == MENU_ROTATE_ID) {
            anim = AnimationUtils.loadAnimation(this, R.anim.myrotate);
        }
        else if (item.getItemId() == MENU_COMBO_ID) {
            anim = AnimationUtils.loadAnimation(this, R.anim.mycombo);
        }
        tv.startAnimation(anim);

        return super.onContextItemSelected(item);
    }
}