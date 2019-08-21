package com.example.proyecto_login;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.HashMap;

public class ListViewAdapter extends BaseAdapter{
    // Declare Variables
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> resultp = new HashMap<String, String>();

    public ListViewAdapter(Context context, ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;

    }
    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        // Declare Variables
        TextView productname;
        TextView description;
        TextView productprice;
        TextView header;
        ImageView icon;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        resultp = data.get(position);
        String isheader =resultp.get("description");
        View itemView;
        if (isheader.equals(" ")){
            itemView = inflater.inflate(R.layout.lv_header, parent, false);
            header = (TextView) itemView.findViewById(R.id.submenu);
            header.setText(resultp.get("productname"));

        }else {

        itemView = inflater.inflate(R.layout.list_item, parent, false);
        // Get the position


        // Locate the TextViews in listview_item.xml
        productname = (TextView) itemView.findViewById(R.id.productname);
        description = (TextView) itemView.findViewById(R.id.description);
        productprice = (TextView) itemView.findViewById(R.id.price);
        // Locate the ImageView in listview_item.xml
        icon = (ImageView) itemView.findViewById(R.id.icon);



            // Capture position and set results to the TextViews
            productname.setText(resultp.get("productname"));
            description.setText(resultp.get("description"));
            productprice.setText(resultp.get("productprice"));
            Picasso.get().load(resultp.get("icon")).into(icon);

        }


        // Capture ListView item click
        itemView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Get the position
                resultp = data.get(position);


            }
        });
        return itemView;
    }

}
