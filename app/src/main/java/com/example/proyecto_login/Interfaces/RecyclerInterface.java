package com.example.proyecto_login.Interfaces;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RecyclerInterface {

    @GET()
    Call<String> getString(@Url String url);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.myjson.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build();
}
