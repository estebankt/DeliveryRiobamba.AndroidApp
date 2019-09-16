package com.example.proyecto_login.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_login.Model_Classes.ModelCategories;
import com.example.proyecto_login.R;
import com.example.proyecto_login.UserInterface.LogInActivity;
import com.example.proyecto_login.UserInterface.RestaurantListProductsActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;


public class RecyclerAdapterCategories extends RecyclerView.Adapter<RecyclerAdapterCategories.MyViewHolder> {


    List<ModelCategories> productsList;
    Context mContext;


    public RecyclerAdapterCategories(Context mContext, ArrayList<ModelCategories> productsList)
    {
        this.mContext=mContext;
        this.productsList=productsList;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.categoryitem,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Picasso.get().load(productsList.get(position).getImgURL()).into(holder.categoryItemBt);
        holder.textbutton.setText(productsList.get(position).getName());
        holder.categoryItemBt.setAlpha(240);
        holder.categoryItemBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{



        ImageButton categoryItemBt;
        TextView textbutton;

        public MyViewHolder(View itemView) {
            super(itemView);

            categoryItemBt=(ImageButton)itemView.findViewById(R.id.categoryButton);
            textbutton=(TextView)itemView.findViewById(R.id.textbuttoncategory);

        }
    }
}
