package com.example.shop.page;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.shop.R;
import com.example.shop.fragment.DatePickerFragment;
import com.example.shop.fragment.TimePickerFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainFragment1 extends Fragment implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    Button b1, b2, b3, btnGitHub;
    TextView text, timeSelector, dateSelector;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_1, container, false);

        b1 = view.findViewById(R.id.Button1);
        b2 = view.findViewById(R.id.Button2);
        b3 = view.findViewById(R.id.Button3);
        btnGitHub = view.findViewById(R.id.btnGitHub);

        text = view.findViewById(R.id.textSelected);
        timeSelector = view.findViewById(R.id.tvTimeSelector);
        timeSelector.setOnClickListener(v -> {
            DialogFragment newFragment = new TimePickerFragment();
            newFragment.setTargetFragment(this, 0);
            newFragment.show(getParentFragmentManager(), "timePicker");
        });

        dateSelector = view.findViewById(R.id.tvDateSelector);
        dateSelector.setOnClickListener(v -> {
            DialogFragment newFragment = new DatePickerFragment();
            newFragment.setTargetFragment(this, 0);
            newFragment.show(getParentFragmentManager(), "datePicker");
        });

        registerForContextMenu(b1);
        registerForContextMenu(b2);
        registerForContextMenu(b3);
        registerForContextMenu(text);

        btnGitHub.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/kotikS45/AndroidShop"));
            startActivity(i);
        });

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(new Date(System.currentTimeMillis()));

        Intent intent = getActivity().getIntent();
        String action = intent.getAction();
        if (action != null && action.equals("shop.app.action.showTime")){
            TextView tvTime = view.findViewById(R.id.tvTime);
            tvTime.setText(time);
        }

        return view;
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("time", timeSelector.getText().toString());
        outState.putString("date", dateSelector.getText().toString());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            timeSelector.setText(savedInstanceState.getString("time"));
            dateSelector.setText(savedInstanceState.getString("date"));
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String time = hourOfDay + ":" + String.format("%02d", minute);
        timeSelector.setText(time);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = dayOfMonth + "." + (month + 1) + "." + year;
        dateSelector.setText(date);
    }
}