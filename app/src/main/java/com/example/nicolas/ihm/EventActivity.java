package com.example.nicolas.ihm;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class EventActivity extends AppCompatActivity implements View.OnClickListener {
    private SessionManager session;
    private Event event;
    TextView tvDisplayDate;
    TextView tvDisplayTime;

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    DecimalFormat formater = new DecimalFormat("00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        /* Récupération de la variable session */
        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        /* Création du menu */
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Modifier événement");
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        /* Récupération de l'événement */
        event = Bdd.getEvent(getIntent().getStringExtra("idEvent"));

        /* Création du spinner */
        final Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(EventActivity.this, android.R.layout.simple_spinner_item, new ArrayList<>(Arrays.asList("Rendez-vous", "Menu", "Tâche ménagère")));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        /* Sélection du type correspondant à l'événement */
        for(int i = 0; i < adapter.getCount(); i++) {
            if(event.getType().equals(adapter.getItem(i))){
                spinner.setSelection(i);
                break;
            }
        }

        /* Récupération de la date actuelle */
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);

        tvDisplayDate = findViewById(R.id.tvDate);
        tvDisplayDate.setText(event.getDate());

        tvDisplayTime = findViewById(R.id.tvTime);
        tvDisplayTime.setText(event.getTime());

        final TextView tvDescription = findViewById(R.id.tvDescription);
        tvDescription.setText(event.getDescription());

        tvDisplayDate.setOnClickListener(this);
        tvDisplayTime.setOnClickListener(this);

        Button btnModify = findViewById(R.id.btnModify);
        btnModify.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                event.setType(spinner.getSelectedItem().toString().trim());
                event.setDate(tvDisplayDate.getText().toString().trim());
                event.setTime(tvDisplayTime.getText().toString().trim());
                event.setDescription(tvDescription.getText().toString().trim());

                Intent intent = new Intent(EventActivity.this, PlanningActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.findItem(R.id.action_delete).setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_delete:
                new AlertDialog.Builder(EventActivity.this)
                        .setTitle("Supprimer")
                        .setMessage("Supprimer l'événement ?")
                        .setPositiveButton("Non",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                })
                        .setNegativeButton("Oui",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Bdd.getEventList().remove(event);
                                        Intent intent = new Intent(EventActivity.this, PlanningActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return true;
            case R.id.action_logout:
                session.logoutUser();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(EventActivity.this, PlanningActivity.class);
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
