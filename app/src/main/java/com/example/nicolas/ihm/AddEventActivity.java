package com.example.nicolas.ihm;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class AddEventActivity extends AppCompatActivity implements View.OnClickListener {
    private SessionManager session;
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
        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        /* Création du menu */
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Ajouter un événement");
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        /* Création du spinner */
        final Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddEventActivity.this, android.R.layout.simple_spinner_item, new ArrayList<>(Arrays.asList("Rendez-vous", "Menu", "Tâche ménagère")));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        tvDisplayDate = findViewById(R.id.tvDate);
        tvDisplayTime = findViewById(R.id.tvTime);

        /* Récupération de la date actuelle */
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);

        tvDisplayDate.setText(new StringBuilder().append(formater.format(day)).append("/").append(formater.format(month + 1)).append("/").append(year));
        tvDisplayTime.setText(new StringBuilder().append(formater.format(hour)).append(":").append(formater.format(minute)));

        tvDisplayDate.setOnClickListener(this);
        tvDisplayTime.setOnClickListener(this);

        final TextView tvDescription = findViewById(R.id.tvDescription);

        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String type = spinner.getSelectedItem().toString().trim();
                String date = tvDisplayDate.getText().toString().trim();
                String time = tvDisplayTime.getText().toString().trim();
                String description = tvDescription.getText().toString().trim();

                if(type.isEmpty() || date.isEmpty() || time.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Les champs doivent être remplis", Toast.LENGTH_LONG).show();
                } else {
                    /* Création du nouvel événement */
                    Event event;
                    if (Bdd.getEventList().isEmpty()) {
                        event = new Event(session.getUserDetails().get(SessionManager.KEY_ID), "0", type, date, time, description);
                    } else {
                        event = new Event(session.getUserDetails().get(SessionManager.KEY_ID), Integer.toString(Integer.parseInt(Bdd.getEventList().get(Bdd.getEventList().size() - 1).getId()) + 1), type, date, time, description);
                    }
                    Bdd.getEventList().add(event);

                    Intent intent = new Intent(AddEventActivity.this, PlanningActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_logout:
                session.logoutUser();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(AddEventActivity.this, PlanningActivity.class);
        startActivity(intent);
        finish();
        return true;
    }

    /* Ouverture des dialogs pour la sélection de la date et de l'heure */
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
                    }, hour, minute, true);
            timePickerDialog.show();
        }
    }
}
