package com.example.proyecto_login.UserInterface;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.proyecto_login.Adapters.RecyclerAdapterCategories;
import com.example.proyecto_login.Interfaces.RecyclerInterface;
import com.example.proyecto_login.Model_Classes.ModelCategories;
import com.example.proyecto_login.R;
import com.example.proyecto_login.ToolBarMenu.OptionMenuActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CategoriesActivity extends OptionMenuActivity {

    ProgressDialog progressDoalog;
    RecyclerView mRecyclerView;
    RecyclerAdapterCategories mAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        //Ejecutamos el metodo CreateMenu de la clase OptionMenuActivity, para crear el menu principal
        //Est tiene que hacerse para todas las clases que tengan el menu
        CreateMenu(1);
        fetchJSON();
    }

    private void fetchJSON(){

        progressDoalog = new ProgressDialog(CategoriesActivity.this);
        progressDoalog.setMessage("Cargando....");
        progressDoalog.show();
        RecyclerInterface api = RecyclerInterface.retrofit.create(RecyclerInterface.class);
        Call<String> call = api.getString("https://api.myjson.com/bins/z131d");
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
                Toast.makeText(CategoriesActivity.this, "Algo salio mal, intentelo mas tarde", Toast.LENGTH_SHORT).show();

            }
        });

    }
    public void insertmenuitems(String response){

        try {
            //getting the whole json object from the response
            JSONObject obj = new JSONObject(response);
            ArrayList<ModelCategories> modelRecyclerArrayList = new ArrayList<>();
            JSONArray dataArray  = obj.getJSONArray("categories");

            for (int i = 0; i < dataArray.length(); i++) {

                ModelCategories modelCategories = new ModelCategories();
                JSONObject dataobj = dataArray.getJSONObject(i);
                modelCategories.setImgURL(dataobj.getString("imgURL"));
                modelCategories.setName(dataobj.getString("name"));
                modelRecyclerArrayList.add(modelCategories);

            }

            mRecyclerView = (RecyclerView) findViewById(R.id.recycler1);
            mRecyclerView.setHasFixedSize(true);
            GridLayoutManager mLayoutManager = new GridLayoutManager(this,2);
            mRecyclerView.setLayoutManager(mLayoutManager);

            // specify an adapter (see also next example)
            mAdapter = new RecyclerAdapterCategories(this,modelRecyclerArrayList);
            mRecyclerView.setAdapter(mAdapter);



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }




}





