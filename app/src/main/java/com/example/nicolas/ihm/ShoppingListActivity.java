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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ShoppingListActivity extends AppCompatActivity {
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        /* Récupération de la variable session */
        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        /* Création du menu */
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Liste de courses");
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        ListView shoppingListView = findViewById(R.id.shoppingListView);

        /* Création de la liste */
        ArrayAdapter<String> adapter = new ArrayAdapter<>(ShoppingListActivity.this, android.R.layout.simple_list_item_1, Bdd.getShoppingList(session.getUserDetails().get(SessionManager.KEY_ID)));
        shoppingListView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.findItem(R.id.action_edit).setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_edit:
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
                                                Intent intent = new Intent(ShoppingListActivity.this, EditShoppingListActivity.class);
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
                    Intent intent = new Intent(ShoppingListActivity.this, EditShoppingListActivity.class);
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

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(ShoppingListActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        return true;
    }
}
