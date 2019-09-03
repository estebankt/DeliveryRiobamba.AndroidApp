package com.example.proyecto_login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductDetailActivity extends OptionMenuActivity {

    //private static final String TAG = "RestaurantProducts";
    private String TAG = RestaurantProducts.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetails);

        Log.d(TAG, "onCreate: started.");


        CreateMenu();

        final int[] cont = {0};
        cont[0] = 1;
        //extract the extra fields of the previus actiity
        String imageUrl = getIntent().getStringExtra("image_url");
        final String imageName = getIntent().getStringExtra("image_name");
        final String ProductPrice = getIntent().getStringExtra("price_product");
        final String description = getIntent().getStringExtra("description");
        final String restaurantID = getIntent().getStringExtra("rest_id");

        //We set the values and show on the new activity
        TextView NumberofProducts = (TextView) findViewById(R.id.NumberOfProd);
        NumberofProducts.setText("" + cont[0]);
        TextView pricev = findViewById(R.id.pricevalue);
        pricev.setText(ProductPrice);
        TextView nameprod = findViewById(R.id.TextViewProductTitle);
        nameprod.setText(imageName);
        TextView desc = findViewById(R.id.productdescription);
        desc.setText(description);
        final ImageView image = findViewById(R.id.ImageViewProduct);
        Picasso.get().load(imageUrl).into(image);


        //Transform the price of the product into a double
        final double PriceNumber = Double.parseDouble(ProductPrice);

        //actions Button


        final int[] i = new int[1];
        final List<Product> cart = ShoppingCartHelper.getCart();

        ImageButton addToCartButton1 = (ImageButton) findViewById(R.id.add_product);
        ImageButton removeToCartButton = (ImageButton) findViewById(R.id.remove_product);
        Button addToCartButton = (Button) findViewById(R.id.ButtonAddToCart);
        i[0] = cart.size();

        addToCartButton1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                cont[0]++;

                TextView NumberofProducts = (TextView) findViewById(R.id.NumberOfProd);
                NumberofProducts.setText("" + cont[0]);


            }
        });
        removeToCartButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (cont[0] > 0) {

                    cont[0]--;
                    TextView NumberofProducts = (TextView) findViewById(R.id.NumberOfProd);
                    NumberofProducts.setText("" + cont[0]);
                }

            }
        });

        addToCartButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                for (int t = 1; t <= cont[0]; t++) {
                    cart.add(new Product(imageName, description, PriceNumber, restaurantID));


                }
                Toast.makeText(getApplicationContext(), "Producto agregado a su pedido", Toast.LENGTH_LONG).show();
                cont[0]=1;
                TextView NumberofProducts = (TextView) findViewById(R.id.NumberOfProd);
                NumberofProducts.setText("" + cont[0]);
                CreateMenu();

            }
        });


        //ejecutamos el metodo CreateMenu de la clase OptionMenuActivity, para crear el menu principal
        //Est tiene que hacerse para todas las clases que tengan el menu

    }
}
