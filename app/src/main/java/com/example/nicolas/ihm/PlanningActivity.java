package com.example.nicolas.ihm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class PlanningActivity extends AppCompatActivity {
    private SessionManager session;
    DecimalFormat formater = new DecimalFormat("00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning);

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

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        List<Event> eventListAtDate = getEventsAtDate(year, month, dayOfMonth);
        SimpleAdapter sa = createAdaptater(eventListAtDate);
        eventListView.setAdapter(sa);

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
                TextView txtViewId = (TextView) view.findViewById(R.id.tvId);
                Intent intent = new Intent(PlanningActivity.this, EventActivity.class);
                intent.putExtra("idEvent", txtViewId.getText());
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.findItem(R.id.action_add).setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                Intent intent = new Intent(PlanningActivity.this, AddEventActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.action_logout:
                session.logoutUser();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(PlanningActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        return true;
    }

    public List<Event> getEventsAtDate(int year, int month, int dayOfMonth){
        List<Event> eventListAtDate = new ArrayList<>();
        for(Event e : EventList.getEventList()){
            if(e.getDate().equals(formater.format(dayOfMonth) + "/" + formater.format(month + 1) + "/" + year)) {
                eventListAtDate.add(e);
            }
        }
        return eventListAtDate;
    }

    public SimpleAdapter createAdaptater(List<Event> eventListAtDate){
        ArrayList<HashMap<String,String>> list = new ArrayList<>();
        HashMap<String,String> item;
        for(Event e : eventListAtDate){
            item = new HashMap<String,String>();
            item.put("time", e.getTime());
            item.put("type", e.getType());
            item.put("description", e.getDescription());
            item.put("id", e.getId());
            list.add( item );
        }
        return new SimpleAdapter(PlanningActivity.this, list, R.layout.event_simple_row, new String[]{"time","type", "description", "id"}, new int[]{R.id.tvTime, R.id.tvType, R.id.tvDescription, R.id.tvId});
    }
}
