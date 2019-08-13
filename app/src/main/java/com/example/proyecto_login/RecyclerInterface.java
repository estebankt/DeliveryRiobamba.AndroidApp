package com.example.proyecto_login;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RecyclerInterface {

    String JSONURL = "https://api.myjson.com/";
    // String JSONURL = "https://demonuts.com/Demonuts/JsonTest/Tennis/json_parsing.php";

    @GET()
    Call<String> getString(@Url String url);
}
