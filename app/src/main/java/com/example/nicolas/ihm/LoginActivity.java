package com.example.nicolas.ihm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText inputName;
    private EditText inputPassword;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /* Récupération de la variable session */
        session = new SessionManager(getApplicationContext());

        /* Redirection si l'utilisateur est déjà connecté */
        if (session.isLoggedIn()) {
            Intent intent = new Intent(LoginActivity.this, PlanningActivity.class);
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
                String name = inputName.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (name.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Les champs doivent être remplis", Toast.LENGTH_LONG).show();
                } else {
                    /* Création de la variable session */
                    session.createLoginSession(name);
                    Intent intent = new Intent(LoginActivity.this, PlanningActivity.class);
                    startActivity(intent);
                    finish();
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
