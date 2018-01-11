package com.example.nicolas.ihm;

/**
 * Cette class implémente un événement
 * @author François ADINOLFI Loïc DUFEIL Jessica MARTINEZ Nicolas BROSSE
 */
public class Event {
    /**
     * Identifiant de l'utilisateur qui crée l'événement
     */
    private String id_user;
    /**
     * Identifiant de l'événement
     */
    private String id;
    /**
     * Type de l'événement
     */
    private String type;
    /**
     * Date de l'événement
     */
    private String date;
    /**
     * Heure de l'événement
     */
    private String time;
    /**
     * Description de l'événement
     */
    private String description;

    /**
     * Constructeur de l'événement
     * @param id_user identifiant de l'utilisateur qui crée l'événement
     * @param id identifiant de l'événement
     * @param type type de l'événement
     * @param date date de l'événement
     * @param time heure de l'événement
     * @param description description de l'événement
     */
    Event(String id_user, String id, String type, String date, String time, String description) {
        this.id_user = id_user;
        this.id = id;
        this.type = type;
        this.date = date;
        this.time = time;
        this.description = description;
    }

    /**
     * Méthode permettant de récupérer l'identifiant de l'utilisateur qui crée l'événement
     * @return retourne l'identifiant de l'utilisateur qui crée l'événement
     */
    String getIdUser() { return id_user; }

    /**
     * Méthode permettant de récupérer l'identifiant de l'événement
     * @return retourne l'identifiant de l'événement
     */
    public String getId() { return id; }

    /**
     * Méthode permettant de récupérer le type de l'événement
     * @return retourne le type de l'événement
     */
    String getType() {
        return type;
    }

    /**
     * Méthode permettant de récupérer la date de l'événement
     * @return retourne la date de l'événement
     */
    String getDate() {
        return date;
    }

    /**
     * Méthode permettant de récupérer l'heure de l'événement
     * @return retourne l'heure de l'événement
     */
    public String getTime() {
        return time;
    }

    /**
     * Méthode permettant de récupérer la description de l'événement
     * @return retourne la description de l'événement
     */
    String getDescription() {
        return description;
    }

    /**
     * Méthode permettant d'affecter le type de l'événement
     * @param type type de l'événement
     */
    void setType(String type) {
        this.type = type;
    }

    /**
     * Méthode permettant d'affecter la date de l'événement
     * @param date date de l'événement
     */
    void setDate(String date) {
        this.date = date;
    }

    /**
     * Méthode permettant d'affecter l'heure de l'événement
     * @param time heure de l'événement
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Méthode permettant d'affecter la description de l'événement
     * @param description description de l'événement
     */
    void setDescription(String description) {
        this.description = description;
    }
}