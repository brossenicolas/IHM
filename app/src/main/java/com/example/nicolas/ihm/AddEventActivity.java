package com.example.nicolas.ihm;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class AddEventActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvDisplayDate;
    private TextView tvDisplayTime;

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    DecimalFormat formater = new DecimalFormat("00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

         /* Récupération de la variable session */
        SessionManager session = new SessionManager(getApplicationContext());
        session.checkLogin();

        /* Création du menu */
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Liste de courses");
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddEventActivity.this, android.R.layout.simple_spinner_item, new ArrayList<>(Arrays.asList("Rendez-vous", "Menu", "Tâche ménagère")));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        tvDisplayDate = findViewById(R.id.tvDate);
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        tvDisplayDate.setText(new StringBuilder().append(formater.format(day)).append("/").append(formater.format(month + 1)).append("/").append(year));

        tvDisplayTime = findViewById(R.id.tvTime);
        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);
        tvDisplayTime.setText(new StringBuilder().append(formater.format(hour)).append(":").append(formater.format(minute)));

        tvDisplayDate.setOnClickListener(this);
        tvDisplayTime.setOnClickListener(this);

        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddEventActivity.this, PlanningActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(AddEventActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v == tvDisplayDate) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int day) {
                            tvDisplayDate.setText(new StringBuilder().append(formater.format(day)).append("/").append(formater.format(month + 1)).append("/").append(year));
                        }
                    }, year, month, day);
            datePickerDialog.show();
        }
        if (v == tvDisplayTime) {
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hour, int minute) {
                            tvDisplayTime.setText(new StringBuilder().append(formater.format(hour)).append(":").append(formater.format(minute)));
                        }
                    }, hour, minute, false);
            timePickerDialog.show();
        }
    }
}
