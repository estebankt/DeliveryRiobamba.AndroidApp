package com.example.proyecto_login.UserInterface;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.proyecto_login.Adapters.ListViewAdapter;
import com.example.proyecto_login.Interfaces.RecyclerInterface;
import com.example.proyecto_login.R;
import com.example.proyecto_login.ToolBarMenu.OptionMenuActivity;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;




public class RestaurantListProductsActivity extends OptionMenuActivity {

    ProgressDialog progressDoalog;
    private String TAG = RestaurantListProductsActivity.class.getSimpleName();

    ListView listview;
    ListViewAdapter adapter;
    ArrayList<HashMap<String, String>> RestaurantItems;
    String restaurantID;
    String imageUrl;
    String imageName;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_products);

        CreateMenu(2);

         Log.d(TAG, "onCreate: started.");
        getIncomingIntent();
        RestaurantItems = new ArrayList<>();
        progressDoalog = new ProgressDialog(RestaurantListProductsActivity.this);
        progressDoalog.setMessage("Cargando....");
        progressDoalog.show();
        fetchJSON();

    }

    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");

        if(getIntent().hasExtra("image_url") && getIntent().hasExtra("image_name")){
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            imageUrl = getIntent().getStringExtra("image_url");
            imageName = getIntent().getStringExtra("image_name");
            restaurantID= getIntent().getStringExtra("rest_id");

            setImage(imageUrl, imageName);
        }
    }


    private void setImage(String imageUrl, String imageName){
        Log.d(TAG, "setImage: setting te image and name to widgets.");

        TextView name = findViewById(R.id.image_description);
        name.setText(imageName);
        ImageView image = findViewById(R.id.image);
        Picasso.get().load(imageUrl).into(image);


    }


    private void fetchJSON(){


        RecyclerInterface api = RecyclerInterface.retrofit.create(RecyclerInterface.class);
        Call<String> call = api.getString("https://api.myjson.com/bins/sxvin");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                progressDoalog.dismiss();
                String jsonresponse = response.body().toString();
                getProducts(jsonresponse);
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(RestaurantListProductsActivity.this, "Algo salio mal, intentelo mas tarde", Toast.LENGTH_SHORT).show();

            }
        });

    }



    public void getProducts (String jsonstr)   {

        String jsonStr = jsonstr;
        try {
            JSONObject jsonObj = new JSONObject(jsonStr);

            // Getting JSON Array node
            JSONArray restaurants = jsonObj.getJSONArray(restaurantID);

            // looping through All Contacts
            for (int i = 0; i < restaurants.length(); i++) {
                JSONObject c = restaurants.getJSONObject(i);
                String productid = c.getString("productid");
                String productname = c.getString("productname");
                String description = c.getString("description");
                String productprice = c.getString("productprice");
                String icon = c.getString("icon");


                // tmp hash map for single contact
                HashMap<String, String> restaurant = new HashMap<>();

                // adding each child node to HashMap key => value
                restaurant.put("productid", productid);
                restaurant.put("productname", productname);
                restaurant.put("description", description);
                restaurant.put("productprice", productprice);
                restaurant.put("icon", icon);
                restaurant.put("rest_id", restaurantID);

                // adding contact to contact list
                RestaurantItems.add(restaurant);
            }
            listview = (ListView) findViewById(R.id.listview);
            // Pass the results into ListViewAdapter.java
            adapter = new ListViewAdapter(RestaurantListProductsActivity.this,  RestaurantItems);
            // Set the adapter to the ListView
            listview.setAdapter(adapter);
            // Close the progressdialog
        } catch (JSONException e) {
            e.printStackTrace();
        }


        }



}
