package com.example.shop;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends BaseActivity {

    Button b1, b2, b3;
    TextView text;
    ArrayList<View> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button) findViewById(R.id.Button1);
        b2 = (Button) findViewById(R.id.Button2);
        b3 = (Button) findViewById(R.id.Button3);

        text = (TextView) findViewById(R.id.textSelected);

        registerForContextMenu(b1);
        registerForContextMenu(b2);
        registerForContextMenu(b3);
        registerForContextMenu(text);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        int viewId = v.getId();
        if (viewId == R.id.Button1 || viewId == R.id.Button2 || viewId == R.id.Button3) {
            getMenuInflater().inflate(R.menu.context_select_button, menu);
        } else if (viewId == R.id.textSelected) {
            getMenuInflater().inflate(R.menu.context_clear_text, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        View view = null;
        Button button = null;

        if (view != null) {
            if (view.getId() == R.id.Button1) {
                button = findViewById(R.id.Button1);
            } else if (view.getId() == R.id.Button2) {
                button = findViewById(R.id.Button2);
            } else if (view.getId() == R.id.Button3) {
                button = findViewById(R.id.Button3);
            } else if (view.getId() == R.id.textSelected) {
                ((TextView)findViewById(R.id.textSelected)).setText("");
            }
        }

        if (button != null) {
            if (itemId == R.id.m_contextSelect) {
                button.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
            } else if (itemId == R.id.m_contextUnselect) {
                button.setBackgroundColor(ContextCompat.getColor(this, R.color.black));
            }
            if (!list.contains(button)){
                list.add(button);
                StringBuilder newText = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    newText.append(list.get(i)).append(" ");
                }
                text.setText(newText.toString());
            }
        }

        return super.onContextItemSelected(item);
    }
}