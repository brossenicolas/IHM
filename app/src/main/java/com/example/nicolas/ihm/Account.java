package com.example.nicolas.ihm;

/**
 * Cette class implémente un compte utilisateur
 * @author François ADINOLFI Loïc DUFEIL Jessica MARTINEZ Nicolas BROSSE
 */
class Account {
    /**
     * Identifiant du compte
     */
    private String id;
    /**
     * Nom du compte
     */
    private String name;
    /**
     * Mot de passe du compte
     */
    private String password;
    /**
     * Mot de passe administrateur du compte
     */
    private String adminPassword;

    /**
     * Constructeur du compte
     * @param id identifiant administrateur du compte
     * @param name nom administrateur du compte
     * @param password mot de passe du compte
     * @param adminPassword mot de passe administrateur du compte
     */
    Account(String id, String name, String password, String adminPassword) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.adminPassword = adminPassword;
    }

    /**
     * Méthode permettant de récupérer l'identifiant du compte
     * @return retourne l'identifiant du compte
     */
    String getId() {
        return id;
    }

    /**
     * Méthode permettant de récupérer le nom du compte
     * @return retourne le nom du compte
     */
    String getName() {
        return name;
    }

    /**
     * Méthode permettant de récupérer le mot de passe du compte
     * @return retourne le mot de passe du compte
     */
    String getPassword() {
        return password;
    }

    /**
     * Méthode permettant de récupérer le mot de passe administrateur du compte
     * @return retourne le mot de passe administrateur du compte
     */
    String getAdminPassword() {
        return adminPassword;
    }
}