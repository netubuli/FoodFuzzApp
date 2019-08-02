package com.otemainc.foodfuzzapp.utility.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.otemainc.foodfuzzapp.R;
import com.otemainc.foodfuzzapp.utility.Db;
import com.otemainc.foodfuzzapp.utility.items.Drink;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DrinkGridviewAdapter extends BaseAdapter {
    Context d;
    ArrayList<Drink> drinks;
    Db db;

    public DrinkGridviewAdapter(Context d, ArrayList<Drink> drinks){
        this.d = d;
        this.drinks = drinks;
    }
    @Override
    public int getCount() {
        return drinks.size();
    }
    @Override
    public Object getItem(int position) {
        return drinks.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view==null){
            view = LayoutInflater.from(d).inflate(R.layout.drink_layout,null,false);
        }
        TextView name = view.findViewById(R.id.name);
        TextView cost = view.findViewById(R.id.cost);
        ImageView image = view.findViewById(R.id.image);
        final Drink drink = (Drink) this.getItem(position);
        name.setText(drink.getTitle());
        cost.setText(drink.getCost());
        //check if there is an image returned
        if(drink.getImage()!= null && drink.getImage().length()>0){
            Picasso.get().load(drink.getImage()).placeholder(R.drawable.foodfuzzlogo).into(image);
        }else{
            Picasso.get().load(R.drawable.foodfuzzlogo).into(image);

        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save to cart
                db = new Db(d);
                boolean isAdded = db.addToCart(drink.getId(), drink.getTitle(), drink.getCost(), drink.getSeller(), 1);
                if (isAdded == true) {
                   Toast.makeText(d, drink.getSeller() + "'s " + drink.getTitle() + " Successfully added to cart", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(d, "Error Unable to update cart", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
