package com.example.nicolas.ihm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

class Bdd {
    private static ArrayList<Account> accountList = new ArrayList<>(Collections.singletonList(new Account("0", "famille", "famille", "admin")));

    private static ArrayList<ShoppingList> shoppingLists = new ArrayList<>(Collections.singletonList(new ShoppingList("0", new ArrayList<>(Arrays.asList("Jambon", "Beurre", "Pain")))));

    private static ArrayList<Event> eventList = new ArrayList<>(Arrays.asList(
            new Event("0","0","Rendez-vous", "06/01/2018", "20:00", "Allez chercher Timothée au foot"),
            new Event("0","1","Menu", "05/01/2018", "12:00", "Entré Plat Désert"),
            new Event("0","2","Tâche ménagère", "05/01/2018", "10:00", "Faire la vaisselle")));

    static ArrayList<Account> getAccountList() {
        return accountList;
    }

    static Account getAccount(String id) {
        for(Account account : accountList){
            if(id.equals(account.getId())){
                return account;
            }
        }
        return null;
    }

    static ArrayList<ShoppingList> getShoppingLists() {
        return shoppingLists;
    }

    static ArrayList<String> getShoppingList(String id) {
        for(ShoppingList sl : shoppingLists){
            if(id.equals(sl.getId())){
                return sl.getShoppingList();
            }
        }
        return null;
    }

    static ArrayList<Event> getEventList() {
        return eventList;
    }

    static ArrayList<Event> getEventList(String idUser) {
        ArrayList<Event> events = new ArrayList<>();
        for(Event e : eventList){
            if(idUser.equals(e.getIdUser())){
                events.add(e);
            }
        }
        return events;
    }

    static Event getEvent(String id) {
        for(Event event : eventList){
            if(id.equals(event.getId())) {
                return event;
            }
        }
        return null;
    }
}
