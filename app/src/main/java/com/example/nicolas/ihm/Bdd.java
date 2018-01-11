package com.example.nicolas.ihm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Cette class implémente toutes les données à stocker à l'aide d'instance static
 * @author François ADINOLFI Loïc DUFEIL Jessica MARTINEZ Nicolas BROSSE
 */
class Bdd {
    /**
     * Liste de comptes utilisateur
     */
    private static ArrayList<Account> accountList = new ArrayList<>(Collections.singletonList(new Account("0", "famille", "famille", "admin")));
    /**
     * Liste des liste de courses
     */
    private static ArrayList<ShoppingList> shoppingLists = new ArrayList<>(Collections.singletonList(new ShoppingList("0", new ArrayList<>(Arrays.asList("Jambon", "Beurre", "Pain")))));
    /**
     * Liste d'événements
     */
    private static ArrayList<Event> eventList = new ArrayList<>(Arrays.asList(
            new Event("0","0","Rendez-vous", "06/01/2018", "20:00", "Allez chercher Timothée au foot"),
            new Event("0","1","Menu", "05/01/2018", "12:00", "Entré Plat Désert"),
            new Event("0","2","Tâche ménagère", "05/01/2018", "10:00", "Faire la vaisselle")));

    /**
     * Méthode permettant de récupérer la liste des comptes utilisateur
     * @return la liste des comptes utilisateur
     */
    static ArrayList<Account> getAccountList() {
        return accountList;
    }

    /**
     * Méthode permettant de récupérer le compte utilisateur correspondant à l'identifiant
     * @param id identifiant du compte utilisateur
     * @return le compte utilisateur
     */
    static Account getAccount(String id) {
        for(Account account : accountList){
            if(id.equals(account.getId())){
                return account;
            }
        }
        return null;
    }

    /**
     * Méthode permettant de récupérer la liste des listes de courses
     * @return la liste des listes de courses
     */
    static ArrayList<ShoppingList> getShoppingLists() {
        return shoppingLists;
    }

    /**
     * Méthode permettant de récupérer la liste de courses correspondant à l'identifiant
     * @param id identifiant de la liste de courses
     * @return la liste de courses
     */
    static ArrayList<String> getShoppingList(String id) {
        for(ShoppingList sl : shoppingLists){
            if(id.equals(sl.getId())){
                return sl.getShoppingList();
            }
        }
        return null;
    }

    /**
     * Méthode permettant de récupérer la liste des événements
     * @return la liste des événements
     */
    static ArrayList<Event> getEventList() {
        return eventList;
    }

    /**
     * Méthode permettant de récupérer la liste des événements de l'utilisateur
     * @param idUser identifiant de l'utilisateur
     * @return la liste des événements
     */
    static ArrayList<Event> getEventList(String idUser) {
        ArrayList<Event> events = new ArrayList<>();
        for(Event e : eventList){
            if(idUser.equals(e.getIdUser())){
                events.add(e);
            }
        }
        return events;
    }

    /**
     * Méthode permettant de récupérer l'événement correspondant à l'identifiant
     * @param id identifiant de l'événement
     * @return l'évenement
     */
    static Event getEvent(String id) {
        for(Event event : eventList){
            if(id.equals(event.getId())) {
                return event;
            }
        }
        return null;
    }
}
