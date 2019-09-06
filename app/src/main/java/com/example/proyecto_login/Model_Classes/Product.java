package com.example.proyecto_login.Model_Classes;


public class Product {

    public String title;
    public String description;
    public double price;
    public String RestaurantID;
    public boolean selected;


    public Product(String title, String description,
                   double price, String RestaurantID) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.RestaurantID = RestaurantID;
    }

}