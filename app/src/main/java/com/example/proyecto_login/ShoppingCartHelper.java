package com.example.proyecto_login;

import java.util.List;
import java.util.Vector;

public class ShoppingCartHelper {

    private static List<Product> cart;

       public static List<Product> getCart() {
        if(cart == null) {
            cart = new Vector<Product>();
        }

        return cart;
    }

}