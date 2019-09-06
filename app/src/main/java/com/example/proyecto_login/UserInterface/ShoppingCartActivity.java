package com.example.proyecto_login.UserInterface;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.proyecto_login.Model_Classes.Product;
import com.example.proyecto_login.Adapters.ProductAdapter;
import com.example.proyecto_login.R;
import com.example.proyecto_login.Adapters.ShoppingCartHelper;

import java.util.List;

public class ShoppingCartActivity extends Activity {

    private List<Product> mCartList;
    private ProductAdapter mProductAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoppingcart);

        mCartList = ShoppingCartHelper.getCart();
        final double[] total = {0};

        // Make sure to clear the selections
        for(int i=0; i<mCartList.size(); i++) {
            mCartList.get(i).selected = false;
            total[0] = mCartList.get(i).price + total[0];
        }
        final TextView totashow = findViewById(R.id.total_value);
        totashow.setText(String.format("   %.2f", total[0]));

        // Create the list
        final ListView listViewCatalog = (ListView) findViewById(R.id.ListViewCatalog);
        mProductAdapter = new ProductAdapter(mCartList, getLayoutInflater(), true);
        listViewCatalog.setAdapter(mProductAdapter);

        listViewCatalog.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Product selectedProduct = mCartList.get(position);
                if(selectedProduct.selected == true)
                    selectedProduct.selected = false;
                else
                    selectedProduct.selected = true;

                mProductAdapter.notifyDataSetInvalidated();

            }
        });

        Button removeButton = (Button) findViewById(R.id.ButtonRemoveFromCart);
        removeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Loop through and remove all the products that are selected
                // Loop backwards so that the remove works correctly
                for(int i=mCartList.size()-1; i>=0; i--) {

                    if(mCartList.get(i).selected) {
                        total[0] = total[0] - mCartList.get(i).price;
                        //TextView totashow = findViewById(R.id.total_value);
                        totashow.setText(String.format("   %.2f", total[0]));
                        mCartList.remove(i);

                    }
                }
                mProductAdapter.notifyDataSetChanged();
            }
        });

    }

}
