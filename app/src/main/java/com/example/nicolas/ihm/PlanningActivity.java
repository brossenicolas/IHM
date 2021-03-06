package com.example.nicolas.ihm;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Cette class implémente le controlleur de la vue du planning
 * @author François ADINOLFI Loïc DUFEIL Jessica MARTINEZ Nicolas BROSSE
 */
public class PlanningActivity extends AppCompatActivity {
    /**
     * Variable session de l'application
     */
    private SessionManager session;
    /**
     * Formatage des nombres des heures et dates
     */
    DecimalFormat formater = new DecimalFormat("00");

    /**
     * Méthode appelé à la création de l'activité
     * @param savedInstanceState état de l'instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning);

         /* Récupération de la variable session */
        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        /* Création du menu */
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Planning");
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        CalendarView calendarView = findViewById(R.id.calendarView);
        final ListView eventListView = findViewById(R.id.eventListView);

        /* Récupération de la date actuelle */
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);

        List<Event> eventListAtDate = getEventsAtDate(year, month, dayOfMonth);

        SimpleAdapter sa = createAdaptater(eventListAtDate);
        eventListView.setAdapter(sa);

        /* Mise à jour de la date en fonction de la date séléctionnée */
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                List<Event> eventListAtDate = getEventsAtDate(year, month, dayOfMonth);

                SimpleAdapter sa = createAdaptater(eventListAtDate);
                eventListView.setAdapter(sa);
            }
        });

        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final TextView txtViewId = view.findViewById(R.id.tvId);
                if(!session.isAdmin()) {
                    LayoutInflater li = LayoutInflater.from(PlanningActivity.this);
                    View promptsView = li.inflate(R.layout.input_dialog, null);

                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PlanningActivity.this);

                    alertDialogBuilder.setView(promptsView);

                    final EditText inputName = promptsView.findViewById(R.id.inputTextDialog);
                    inputName.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    inputName.setHint("Mot de passe");

                    /* Création de l'alert pour la demande du mot de passe administrateur */
                    alertDialogBuilder
                            .setTitle("Mot de passe administrateur")
                            .setPositiveButton("Annuler",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    })
                            .setNegativeButton("Valider",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            String name = inputName.getText().toString().trim();
                                            if (name.equals(Bdd.getAccount(session.getUserDetails().get(SessionManager.KEY_ID)).getAdminPassword())) {
                                                session.setAdmin();
                                                Intent intent = new Intent(PlanningActivity.this, EventActivity.class);
                                                intent.putExtra("idEvent", txtViewId.getText());
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Mot de passe incorrect", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else {
                    Intent intent = new Intent(PlanningActivity.this, EventActivity.class);
                    intent.putExtra("idEvent", txtViewId.getText());
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    /**
     * Méthode appelé à la création de l'activité pour construire le menu
     * @param menu menu de l'activité
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.findItem(R.id.action_add).setVisible(true);
        return true;
    }

    /**
     * Méthode qui implémente les boutons du menu
     * @param item item sélectionné dans le menu
     * @return true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                if(!session.isAdmin()) {
                    LayoutInflater li = LayoutInflater.from(this);
                    View promptsView = li.inflate(R.layout.input_dialog, null);

                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

                    alertDialogBuilder.setView(promptsView);

                    final EditText inputName = promptsView.findViewById(R.id.inputTextDialog);
                    inputName.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    inputName.setHint("Mot de passe");

                    /* Création de l'alert pour la demande du mot de passe administrateur */
                    alertDialogBuilder
                            .setTitle("Mot de passe administrateur")
                            .setPositiveButton("Annuler",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    })
                            .setNegativeButton("Valider",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            String name = inputName.getText().toString().trim();
                                            if (name.equals(Bdd.getAccount(session.getUserDetails().get(SessionManager.KEY_ID)).getAdminPassword())) {
                                                session.setAdmin();
                                                Intent intent = new Intent(PlanningActivity.this, AddEventActivity.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Mot de passe incorrect", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else {
                    Intent intent = new Intent(PlanningActivity.this, AddEventActivity.class);
                    startActivity(intent);
                    finish();
                }
                return true;
            case R.id.action_logout:
                session.logoutUser();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Méthode qui implémente le boutton retour du menu
     * @return true
     */
    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(PlanningActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        return true;
    }

    /**
     * Méthode qui récupère les événements à une date donnée
     * @param year année de la date
     * @param month mois de la date
     * @param dayOfMonth jour de la date
     * @return liste des événements
     */
    public List<Event> getEventsAtDate(int year, int month, int dayOfMonth){
        List<Event> eventListAtDate = new ArrayList<>();
        for(Event e : Bdd.getEventList(session.getUserDetails().get(SessionManager.KEY_ID))){
            if(e.getDate().equals(formater.format(dayOfMonth) + "/" + formater.format(month + 1) + "/" + year)) {
                eventListAtDate.add(e);
            }
        }
        return eventListAtDate;
    }

    /**
     * Méthode qui crée l'adaptateur pour la liste des événements de la vue
     * @param eventListAtDate liste des événement à la date séléctionné
     * @return l'adaptateur
     */
    public SimpleAdapter createAdaptater(List<Event> eventListAtDate){
        ArrayList<HashMap<String,String>> list = new ArrayList<>();
        HashMap<String,String> item;
        for(Event e : eventListAtDate){
            item = new HashMap<>();
            item.put("time", e.getTime());
            item.put("type", e.getType());
            item.put("description", e.getDescription());
            item.put("id", e.getId());
            list.add( item );
        }
        return new SimpleAdapter(PlanningActivity.this, list, R.layout.event_simple_row, new String[]{"time","type", "description", "id"}, new int[]{R.id.tvTime, R.id.tvType, R.id.tvDescription, R.id.tvId});
    }
}
