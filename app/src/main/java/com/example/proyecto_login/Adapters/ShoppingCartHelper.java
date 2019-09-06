package com.example.proyecto_login.Adapters;

import com.example.proyecto_login.Model_Classes.Product;

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