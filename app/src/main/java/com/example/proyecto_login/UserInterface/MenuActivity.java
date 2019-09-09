package com.example.proyecto_login.UserInterface;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_login.Model_Classes.ModelRecycler;
import com.example.proyecto_login.ToolBarMenu.OptionMenuActivity;
import com.example.proyecto_login.R;
import com.example.proyecto_login.Interfaces.RecyclerInterface;
import com.example.proyecto_login.Adapters.RetrofitAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuActivity extends OptionMenuActivity {

    private RetrofitAdapter retrofitAdapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDoalog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        //Ejecutamos el metodo CreateMenu de la clase OptionMenuActivity, para crear el menu principal
        //Est tiene que hacerse para todas las clases que tengan el menu
        CreateMenu(1);
        recyclerView = findViewById(R.id.recycler);
        fetchJSON();
    }

    private void fetchJSON(){

        progressDoalog = new ProgressDialog(MenuActivity.this);
        progressDoalog.setMessage("Cargando....");
        progressDoalog.show();
        RecyclerInterface api = RecyclerInterface.retrofit.create(RecyclerInterface.class);
        Call<String> call = api.getString("https://api.myjson.com/bins/1h4xs5");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                progressDoalog.dismiss();
                String jsonresponse = response.body().toString();
                insertmenuitems(jsonresponse);
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(MenuActivity.this, "Algo salio mal, intentelo mas tarde", Toast.LENGTH_SHORT).show();

            }
        });

    }


     public void insertmenuitems(String response){

        try {
            //getting the whole json object from the response
                JSONObject obj = new JSONObject(response);
                ArrayList<ModelRecycler> modelRecyclerArrayList = new ArrayList<>();
                JSONArray dataArray  = obj.getJSONArray("Restaurants");

                for (int i = 0; i < dataArray.length(); i++) {

                    ModelRecycler modelRecycler = new ModelRecycler();
                    JSONObject dataobj = dataArray.getJSONObject(i);
                    modelRecycler.setImgURL(dataobj.getString("imgURL"));
                    modelRecycler.setName(dataobj.getString("name"));
                    modelRecycler.setType(dataobj.getString("type"));
                    modelRecycler.setBillType1(dataobj.getString("Bill_type_1"));
                    modelRecycler.setBillType2(dataobj.getString("Bill_type_2"));
                    modelRecycler.setId(dataobj.getString("id"));

                    modelRecyclerArrayList.add(modelRecycler);

                }

                retrofitAdapter = new RetrofitAdapter(this,modelRecyclerArrayList);
                recyclerView.setAdapter(retrofitAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}