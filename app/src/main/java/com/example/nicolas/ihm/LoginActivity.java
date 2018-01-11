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
 * Cette class implémente le controlleur de la vue de connexion d'un compte utilisateur
 * @author François ADINOLFI Loïc DUFEIL Jessica MARTINEZ Nicolas BROSSE
 */
public class LoginActivity extends AppCompatActivity {
    /**
     * Variable session de l'application
     */
    private SessionManager session;
    /**
     * Champs nom de la vue
     */
    private EditText inputName;
    /**
     * Champs mot de passe de la vue
     */
    private EditText inputPassword;

    /**
     * Méthode appelé à la création de l'activité
     * @param savedInstanceState état de l'instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /* Récupération de la variable session */
        session = new SessionManager(getApplicationContext());

        /* Redirection si l'utilisateur est déjà connecté */
        if (session.isLoggedIn()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        inputName = findViewById(R.id.name);
        inputPassword = findViewById(R.id.password);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView linkRegister = findViewById(R.id.linkRegister);

        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                /* Récupération des valeurs des champs */
                String name = inputName.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (name.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Les champs doivent être remplis", Toast.LENGTH_LONG).show();
                } else {
                    for(Account account : Bdd.getAccountList()) {
                        /* Vérification des identifiants */
                        if(name.equals(account.getName()) && password.equals(account.getPassword())){
                            /* Création de la variable session */
                            session.createLoginSession(account.getId(), name);

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            return;
                        }
                    }
                    Toast.makeText(getApplicationContext(), "Identifiants incorrects", Toast.LENGTH_LONG).show();
                }
            }
        });

        linkRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
