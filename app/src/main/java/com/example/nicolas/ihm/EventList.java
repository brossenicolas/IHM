package com.example.nicolas.ihm;

import java.util.ArrayList;
import java.util.Arrays;

public class EventList {
    private static ArrayList<Event> eventList = new ArrayList<>(Arrays.asList(
            new Event("0","Rendez-vous", "04/01/2018", "20:00", "Allez chercher Timothée au foot"),
            new Event("1","Menu", "05/01/2018", "12:00", "Entré Plat Désert"),
            new Event("2","Tâche ménagère", "05/01/2018", "10:00", "Faire la vaisselle")));

    public EventList() {}

    static ArrayList<Event> getEventList() {
        return eventList;
    }
}