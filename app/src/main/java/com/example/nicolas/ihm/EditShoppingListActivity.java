package com.example.nicolas.ihm;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class EditShoppingListActivity extends AppCompatActivity {
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_shopping_list);

        /* Récupération de la variable session */
        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        /* Création du menu */
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Editer la liste de courses");
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        /* Création de la liste */
        ListView shoppingListView = findViewById(R.id.shoppingListView);
        shoppingListView.setAdapter(new MyListAdaper(this, R.layout.edit_shopping_list_simple_row, Bdd.getShoppingList(session.getUserDetails().get(SessionManager.KEY_ID))));
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
                LayoutInflater li = LayoutInflater.from(this);
                View promptsView = li.inflate(R.layout.input_dialog, null);

                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

                alertDialogBuilder.setView(promptsView);

                final EditText inputName = promptsView.findViewById(R.id.inputTextDialog);
                inputName.setHint("Nom");

                /* Création de l'alert pour l'ajout d'un item à la liste de courses */
                alertDialogBuilder
                        .setTitle("Ajouter")
                        .setPositiveButton("Annuler",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                })
                        .setNegativeButton("Ajouter",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        String name = inputName.getText().toString().trim();
                                        if (name.isEmpty()) {
                                            Toast.makeText(getApplicationContext(), "Le nom doit être rempli", Toast.LENGTH_LONG).show();
                                        } else {
                                            Bdd.getShoppingList(session.getUserDetails().get(SessionManager.KEY_ID)).add(name);
                                            finish();
                                            startActivity(getIntent());
                                        }
                                    }
                                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                return true;
            case R.id.action_logout:
                session.logoutUser();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(EditShoppingListActivity.this, ShoppingListActivity.class);
        startActivity(intent);
        finish();
        return true;
    }

    private class MyListAdaper extends ArrayAdapter<String> {
        private int layout;

        private MyListAdaper(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            layout = resource;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            ViewHolder mainViewholder;
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.title = convertView.findViewById(R.id.textListItem);
                viewHolder.image = convertView.findViewById(R.id.deleteListItem);
                convertView.setTag(viewHolder);
            }
            mainViewholder = (ViewHolder) convertView.getTag();
            mainViewholder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   new AlertDialog.Builder(EditShoppingListActivity.this)
                        .setTitle("Supprimer")
                        .setMessage("Supprimer " + Bdd.getShoppingList(session.getUserDetails().get(SessionManager.KEY_ID)).get(position) + " ?")
                        .setPositiveButton("Non",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                       .setNegativeButton("Oui",
                           new DialogInterface.OnClickListener() {
                               public void onClick(DialogInterface dialog, int which) {
                                   Bdd.getShoppingList(session.getUserDetails().get(SessionManager.KEY_ID)).remove(position);
                                   finish();
                                   startActivity(getIntent());
                               }
                           })
                       .setIcon(android.R.drawable.ic_dialog_alert)
                       .show();
                }
            });
            mainViewholder.title.setText(getItem(position));

            return convertView;
        }
        class ViewHolder {
            TextView title;
            ImageView image;
        }
    }
}
