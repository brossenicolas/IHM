package com.example.nicolas.ihm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

/**
 * Cette class implémente le controlleur de la vue de la page d'accueil de l'application
 * @author François ADINOLFI Loïc DUFEIL Jessica MARTINEZ Nicolas BROSSE
 */
public class MainActivity extends AppCompatActivity {
    /**
     * Variable session de l'application
     */
    private SessionManager session;

    /**
     * Méthode appelé à la création de l'activité
     * @param savedInstanceState état de l'instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         /* Récupération de la variable session */
        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        /* Création du menu */
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Agenda familial");
        setSupportActionBar(toolbar);

        ImageView planning = findViewById(R.id.imagePlanning);
        planning.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PlanningActivity.class);
                startActivity(intent);
                finish();
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

    /**
     * Méthode appelé à la création de l'activité pour construire le menu
     * @param menu menu de l'activité
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
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
            case R.id.action_logout:
                session.logoutUser();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
