package com.example.proyecto_login.CommonClasses;

import android.util.Log;

import com.example.proyecto_login.UserInterface.RegisterActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class InsertUsers {

    private String resu;
    private String TAG = RegisterActivity.class.getSimpleName();

    public String InsertUsr(String url, String Name, String Password, String Mail, String DelivAddr, String InvvAddr, String Phone){

        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.accumulate("name", Name);
            jsonObj.accumulate("password", Password);
            jsonObj.accumulate("email", Mail);
            jsonObj.accumulate("deladdress", DelivAddr);
            jsonObj.accumulate("inaddress", InvvAddr);
            jsonObj.accumulate("phone", Phone);
            // 4. make POST request to the given URL
            // 5. return response message
            HttpPost shp = new HttpPost();
            resu = shp.HttpPost(url, jsonObj);

        } catch (final JSONException | IOException e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
            resu = "Json parsing error: " + e.getMessage();
        }
        return resu;
    }
}
