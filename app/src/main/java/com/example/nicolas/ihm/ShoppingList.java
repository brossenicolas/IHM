package com.example.nicolas.ihm;

import java.util.ArrayList;

class ShoppingList {
    private String id;
    private ArrayList<String> shoppingList;

    ShoppingList(String id) {
        this.id = id;
        this.shoppingList = new ArrayList<>();
    }

    ShoppingList(String id, ArrayList<String> shoppingList) {
        this.id = id;
        this.shoppingList = shoppingList;
    }

    String getId() {
        return id;
    }

    ArrayList<String> getShoppingList() {
        return shoppingList;
    }
}
