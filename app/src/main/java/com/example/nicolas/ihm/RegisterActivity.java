package com.example.nicolas.ihm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Cette class implémente le controlleur de la vue d'inscription d'un compte utilisateur
 * @author François ADINOLFI Loïc DUFEIL Jessica MARTINEZ Nicolas BROSSE
 */
public class RegisterActivity extends AppCompatActivity {
    /**
     * Champs nom de la vue
     */
    private EditText inputName;
    /**
     * Champs mot de passe de la vue
     */
    private EditText inputPassword;
    /**
     * Champs mot de passe administrateur de la vue
     */
    private EditText inputAdminPassword;

    /**
     * Méthode appelé à la création de l'activité
     * @param savedInstanceState état de l'instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputName = findViewById(R.id.name);
        inputPassword = findViewById(R.id.password);
        inputAdminPassword = findViewById(R.id.adminPassword);
        Button btnRegister = findViewById(R.id.btnRegister);
        TextView linkLogin = findViewById(R.id.linkLogin);

        btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                /* Récupération des valeurs des champs */
                String name = inputName.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String adminPassword = inputAdminPassword.getText().toString().trim();

                /* Vérification que les champs soient remplis */
                if (name.isEmpty() || password.isEmpty() || adminPassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Les champs doivent être remplis", Toast.LENGTH_LONG).show();
                }
                else {
                    /* Création du compte */
                    String id = Integer.toString(Integer.parseInt(Bdd.getAccountList().get(Bdd.getAccountList().size()-1).getId()) + 1);
                    Account account = new Account(id, name, password, adminPassword);
                    Bdd.getAccountList().add(account);

                    /* Création de la liste de course */
                    Bdd.getShoppingLists().add(new ShoppingList(id));

                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        /* Lien vers la page de connexion */
        linkLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
