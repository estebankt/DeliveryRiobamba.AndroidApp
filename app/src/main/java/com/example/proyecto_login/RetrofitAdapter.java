package com.example.proyecto_login;

import android.content.Context;
//import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RetrofitAdapter extends RecyclerView.Adapter<RetrofitAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<ModelRecycler> dataModelArrayList;

    public RetrofitAdapter(Context ctx, ArrayList<ModelRecycler> dataModelArrayList){

        inflater = LayoutInflater.from(ctx);
        this.dataModelArrayList = dataModelArrayList;
    }

    @Override
    public RetrofitAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.retro_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RetrofitAdapter.MyViewHolder holder, int position) {

        Picasso.get().load(dataModelArrayList.get(position).getImgURL()).into(holder.iv);
        holder.name.setText(dataModelArrayList.get(position).getName());
        holder.type.setText(dataModelArrayList.get(position).getType());
        holder.billtype1.setText(dataModelArrayList.get(position).getsetBillType1());
        holder.billtype2.setText(dataModelArrayList.get(position).getsetBillType2());

    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView type, name, billtype1, billtype2;
        ImageView iv;

        public MyViewHolder(View itemView) {
            super(itemView);

            type = (TextView) itemView.findViewById(R.id.type);
            name = (TextView) itemView.findViewById(R.id.name);
            billtype1 = (TextView) itemView.findViewById(R.id.billtype1);
            billtype2 = (TextView) itemView.findViewById(R.id.billtype2);
            iv = (ImageView) itemView.findViewById(R.id.iv);
        }

    }
}
