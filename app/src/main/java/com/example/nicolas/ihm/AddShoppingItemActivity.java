package com.example.nicolas.ihm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddShoppingItemActivity extends AppCompatActivity {
    private EditText inputName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shopping_item);

        /* Récupération de la variable session */
        SessionManager session = new SessionManager(getApplicationContext());
        session.checkLogin();

        /* Création du menu */
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Ajouter un item");
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Button btnAdd = findViewById(R.id.btnAdd);
        inputName = findViewById(R.id.name);

        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                /* Récupération du nom de l'item */
                String name = inputName.getText().toString().trim();
                if (name.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Le nom doit être rempli", Toast.LENGTH_LONG).show();
                } else {
                    /* Ajout d'un item à la liste de course */
                    AddShoppingItemActivity.this.finish();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return true;
    }
}
