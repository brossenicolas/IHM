package com.example.nicolas.ihm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         /* Récupération de la variable session */
        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        /* Création du menu */
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Liste de courses");
        setSupportActionBar(toolbar);

        ImageView planning = findViewById(R.id.imagePlanning);
        planning.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PlanningActivity.class);
                startActivity(intent);
            }
        });

        ImageView shoppingList = findViewById(R.id.imageShoppingList);
        shoppingList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShoppingListActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.findItem(R.id.action_edit).setVisible(false);
        menu.findItem(R.id.action_add).setVisible(false);
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
}