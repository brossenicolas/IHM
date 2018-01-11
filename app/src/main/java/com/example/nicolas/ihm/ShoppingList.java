package com.example.nicolas.ihm;

import java.util.ArrayList;

/**
 * Cette class implémente une liste de course
 * @author François ADINOLFI Loïc DUFEIL Jessica MARTINEZ Nicolas BROSSE
 */
class ShoppingList {
    /**
     * Identifiant de la liste de courses
     */
    private String id;
    /**
     * Liste de produits
     */
    private ArrayList<String> shoppingList;

    /**
     * Constructeur de la liste de courses
     * @param id identifiant de la liste de courses
     */
    ShoppingList(String id) {
        this.id = id;
        this.shoppingList = new ArrayList<>();
    }

    /**
     * Constructeur de la liste de courses
     * @param id identifiant de la liste de courses
     * @param shoppingList liste de produits
     */
    ShoppingList(String id, ArrayList<String> shoppingList) {
        this.id = id;
        this.shoppingList = shoppingList;
    }

    /**
     * Méthode permettant de récupérer l'identifiant de la liste de courses
     * @return retourne l'identifiant de la liste de courses
     */
    String getId() {
        return id;
    }

    /**
     * Méthode permettant de récupérer la liste de produits
     * @return retourne la liste de produits
     */
    ArrayList<String> getShoppingList() {
        return shoppingList;
    }
}
