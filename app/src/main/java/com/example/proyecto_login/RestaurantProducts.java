package com.example.proyecto_login;

import android.os.AsyncTask;
import android.os.Bundle;
import com.squareup.picasso.Picasso;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public class RestaurantProducts extends AppCompatActivity {
    //private static final String TAG = "RestaurantProducts";
    private String TAG = RestaurantProducts.class.getSimpleName();
   // private ListView lv;
    ListView listview;
    ListViewAdapter adapter;
    ArrayList<HashMap<String, String>> RestaurantItems;
    String restaurantID = "2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_products);
        Log.d(TAG, "onCreate: started.");
        getIncomingIntent();
        RestaurantItems = new ArrayList<>();
        String restaurantID="1";
        new GetContacts().execute();

    }
    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");

        if(getIntent().hasExtra("image_url") && getIntent().hasExtra("image_name")){
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            String imageUrl = getIntent().getStringExtra("image_url");
            String imageName = getIntent().getStringExtra("image_name");
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

    private class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(RestaurantProducts.this,"Cargando",Toast.LENGTH_LONG).show();

        }
        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String url = "https://api.myjson.com/bins/sxvin";
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
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
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            listview = (ListView) findViewById(R.id.listview);
            // Pass the results into ListViewAdapter.java
            adapter = new ListViewAdapter(RestaurantProducts.this,  RestaurantItems);
            // Set the adapter to the ListView
            listview.setAdapter(adapter);
            // Close the progressdialog
        }
    }

}
