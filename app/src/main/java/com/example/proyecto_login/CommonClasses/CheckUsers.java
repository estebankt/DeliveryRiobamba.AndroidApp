package com.example.proyecto_login.CommonClasses;


import android.content.SharedPreferences;
import android.util.Log;

import com.example.proyecto_login.Adapters.ProfileHelper;
import com.example.proyecto_login.Adapters.ShoppingCartHelper;
import com.example.proyecto_login.Model_Classes.ModelMenu;
import com.example.proyecto_login.Model_Classes.ModelUser;
import com.example.proyecto_login.Model_Classes.Product;
import com.example.proyecto_login.ToolBarMenu.OptionMenuActivity;
import com.example.proyecto_login.UserInterface.LogInActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class CheckUsers extends OptionMenuActivity {
    private String userdb,passdb;
    private String result="no_accepted";
    private String TAG = LogInActivity.class.getSimpleName();
    final List<ModelUser> cart = ProfileHelper.getProfile();


    public String check(String JSON_STRING, String userN, String Passw){

        if (JSON_STRING != null) {
            try {
                JSONObject jsonObj = new JSONObject(JSON_STRING);
                // cramos un JSON array

                JSONArray contacts = jsonObj.getJSONArray("contacts");

                // looping through All Contacts
                for (int i = 0; i < contacts.length(); i++) {

                    JSONObject c = contacts.getJSONObject(i);
                    userdb = c.getString("email");
                    passdb = c.getString("password");

                    if (userN.equals(userdb) && Passw.equals(passdb))

                    {
                        final List<ModelUser> profile = ProfileHelper.getProfile();

                       String mail = c.getString("email");
                       String name = c.getString("name");
                       String pass = c.getString("password");
                      String deladd = c.getString("deladdress");
                      String inadd = c.getString("inaddress");
                       String phone = c.getString("phone");
                        profile.add(new ModelUser(name, pass,mail,deladd,inadd,phone));

                        i = contacts.length();
                        result="accept";

                    }else if (userN.equals(userdb)&& !Passw.equals(passdb)){
                        i = contacts.length();
                        result="invalid_pass";
                    }

                }
            } catch (final JSONException e) {

                Log.e(TAG, "Json parsing error: " + e.getMessage());
                result="Json parsing error: " + e.getMessage();
            }
            return result;

        } else {

            Log.e(TAG, "Couldn't get json from server.");
            result="Couldn't get json from server.";

        }

        return result;
    }
}
