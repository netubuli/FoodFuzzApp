package com.otemainc.foodfuzzapp.utility.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.otemainc.foodfuzzapp.R;
import com.otemainc.foodfuzzapp.utility.Db;
import com.otemainc.foodfuzzapp.utility.dialogs.AddToCartDialog;
import com.otemainc.foodfuzzapp.utility.items.Food;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FoodGridviewAdapter extends BaseAdapter {
    Context f;
    ArrayList<Food> foods;
    Db db;

    public FoodGridviewAdapter(Context f, ArrayList<Food> foods){
        this.f = f;
        this.foods = foods;
    }
    @Override
    public int getCount() {
        return foods.size();
    }
    @Override
    public Object getItem(int position) {
        return foods.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view==null){
            view = LayoutInflater.from(f).inflate(R.layout.food_layout,null,false);
        }
        TextView name = view.findViewById(R.id.name);
        TextView cost = view.findViewById(R.id.cost);
        ImageView image = view.findViewById(R.id.image);
        final Food food = (Food) this.getItem(position);
        name.setText(food.getTitle());
        cost.setText(food.getCost());
        //check if there is an image returned
        if(food.getImage()!= null && food.getImage().length()>0){
            Picasso.get().load(food.getImage()).placeholder(R.drawable.foodfuzzlogo).into(image);
        }else{
            Picasso.get().load(R.drawable.foodfuzzlogo).into(image);

        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //save to cart
                db = new Db(f);
                boolean isAdded = db.addToCart(food.getId(), food.getTitle(), food.getCost(), food.getSeller(), 1);
                if (isAdded == true) {
                    Toast.makeText(f, food.getSeller() + "'s " + food.getTitle() + " Successfully added to cart", Toast.LENGTH_SHORT).show();
                    AddToCartDialog addToCartDialog = new AddToCartDialog();
                    addToCartDialog.show(((AppCompatActivity) f).getSupportFragmentManager(),"Add To CART");
                }else{
                    Toast.makeText(f, "Error Unable to update cart", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
