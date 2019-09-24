package com.example.proyecto_login.Adapters;

import com.example.proyecto_login.Model_Classes.ModelUser;
import com.example.proyecto_login.Model_Classes.Product;

import java.util.List;
import java.util.Vector;

public class ProfileHelper {

    private static List<ModelUser> profile;

       public static List<ModelUser> getProfile() {
        if(profile == null) {
            profile = new Vector<ModelUser>();
        }

        return profile;
    }

}