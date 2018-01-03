package com.example.nicolas.ihm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShoppingList {
    private static ArrayList<String> shoppingList = new ArrayList<>(Arrays.asList("Jambon", "Beurre", "Pain"));

    public ShoppingList() {}

    static List getShoppingList() {
        return shoppingList;
    }
}
