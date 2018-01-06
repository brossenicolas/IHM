package com.example.nicolas.ihm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

class SessionManager {
    private SharedPreferences pref;
    private Editor editor;
    private Context _context;
    private static final String PREF_NAME = "AndroidHivePref";
    private static final String IS_LOGIN = "IsLoggedIn";
    static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";

    @SuppressLint("CommitPrefEdits")
    SessionManager(Context context){
        this._context = context;
        int PRIVATE_MODE = 0;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Création de la session
     * */
    void createLoginSession(String id, String name){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_ID, id);
        editor.putString(KEY_NAME, name);
        editor.commit();
    }

    /**
     * Récupération des détails de l'utilisateur
     * */
    HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_ID, pref.getString(KEY_ID, null));
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        return user;
    }

    /**
     * Vérifie si l'utilisateur est connecté
     * Si non, redirection vers la page login
     * */
    void checkLogin(){
        if(!this.isLoggedIn()){
            Intent i = new Intent(_context, LoginActivity.class);
            // Fermeture de toutes les activités
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Ajout un nouveau Flag pour commencer une nouvelle activité
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Ouverture de l'activité login
            _context.startActivity(i);
        }

    }

    /**
     * Vide les détails de l'utilisateur
     * */
    void logoutUser(){
        // Vide les préférences de Shared Preferences
        editor.clear();
        editor.commit();

        Intent i = new Intent(_context, LoginActivity.class);
        // Fermeture de toutes les activités
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Ajout un nouveau Flag pour commencer une nouvelle activité
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Ouverture de l'activité login
        _context.startActivity(i);
    }

    /**
     * Récupération de l'état de IS_LOGIN
     * **/
    boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}