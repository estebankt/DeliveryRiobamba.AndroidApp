package com.example.proyecto_login;


import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class CheckUsers  {
    private String userdb,passdb;
    private String result="no_accepted";
    private String TAG = LogInActivity.class.getSimpleName();

    public String check(String JSON_STRING, String userN, String Passw){

        if (JSON_STRING != null) {
            try {
                JSONObject jsonObj = new JSONObject(JSON_STRING);
                // cramos un JSON array
                // Pato chupa bolas
                JSONArray contacts = jsonObj.getJSONArray("contacts");

                // looping through All Contacts
                for (int i = 0; i < contacts.length(); i++) {

                    JSONObject c = contacts.getJSONObject(i);
                    userdb = c.getString("email");
                    passdb = c.getString("password");

                    if (userN.equals(userdb) && Passw.equals(passdb)) {

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
