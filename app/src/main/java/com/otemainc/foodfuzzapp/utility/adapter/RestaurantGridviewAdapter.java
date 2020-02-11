package com.otemainc.foodfuzzapp.utility.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.otemainc.foodfuzzapp.R;
import com.otemainc.foodfuzzapp.utility.items.Restaurant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RestaurantGridviewAdapter extends BaseAdapter {
    Context d;
    ArrayList<Restaurant> restaurants;

    public RestaurantGridviewAdapter(Context d, ArrayList<Restaurant> restaurants){
        this.d = d;
        this.restaurants = restaurants;
    }
    @Override
    public int getCount() {
        return restaurants.size();
    }
    @Override
    public Object getItem(int position) {
        return restaurants.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view==null){
            view = LayoutInflater.from(d).inflate(R.layout.restaurant_layout,null,false);
        }
        TextView name = view.findViewById(R.id.name);
        ImageView image = view.findViewById(R.id.image);
        final Restaurant restaurant = (Restaurant) this.getItem(position);
        name.setText(restaurant.getName());
        //check if there is an image returned
        if(restaurant.getImage()!= null && restaurant.getImage().length()>0){
            Picasso.get().load(restaurant.getImage()).placeholder(R.drawable.foodfuzzlogo).into(image);
        }else{
            Picasso.get().load(R.drawable.foodfuzzlogo).into(image);

        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(d, "Placing orders directly from a particular restaurant coming soon", Toast.LENGTH_LONG).show();

            }
        });

        return view;
    }
}
