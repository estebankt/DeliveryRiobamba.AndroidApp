package com.example.proyecto_login.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_login.Model_Classes.ModelMenu;
import com.example.proyecto_login.R;
import com.example.proyecto_login.UserInterface.RestaurantListProductsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class RecyclerAdapterMenu extends RecyclerView.Adapter<RecyclerAdapterMenu.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<ModelMenu> dataModelArrayList;
    private Context mContext;

    public RecyclerAdapterMenu(Context ctx, ArrayList<ModelMenu> dataModelArrayList){

        inflater = LayoutInflater.from(ctx);
        this.dataModelArrayList = dataModelArrayList;
        mContext=ctx;
    }

    @Override
    public RecyclerAdapterMenu.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.retro_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapterMenu.MyViewHolder holder, final int position) {

        Picasso.get().load(dataModelArrayList.get(position).getImgURL()).into(holder.iv);
        holder.name.setText(dataModelArrayList.get(position).getName());
        holder.type.setText(dataModelArrayList.get(position).getType());
        holder.billtype1.setText(dataModelArrayList.get(position).getsetBillType1());
        holder.billtype2.setText(dataModelArrayList.get(position).getsetBillType2());
        holder.cardview.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, RestaurantListProductsActivity.class);
                intent.putExtra("image_url", dataModelArrayList.get(position).getImgURL());
                intent.putExtra("image_name", dataModelArrayList.get(position).getName());
                intent.putExtra("rest_id", dataModelArrayList.get(position).getId());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView type, name, billtype1, billtype2;
        ImageView iv;
        CardView cardview;

        public MyViewHolder(View itemView) {
            super(itemView);

            type = (TextView) itemView.findViewById(R.id.type);
            name = (TextView) itemView.findViewById(R.id.name);
            billtype1 = (TextView) itemView.findViewById(R.id.billtype1);
            billtype2 = (TextView) itemView.findViewById(R.id.billtype2);
            iv = (ImageView) itemView.findViewById(R.id.iv);
            cardview = (CardView) itemView.findViewById(R.id.card_view);
        }

    }
}
