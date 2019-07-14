package com.otemainc.foodfuzzapp.utility.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.otemainc.foodfuzzapp.utility.items.Food;

import java.util.ArrayList;

public class FoodGridviewAdapter extends BaseAdapter {
    Context f;
    ArrayList<Food> foods;

    public FoodGridviewAdapter(Context f,ArrayList<Food> foods){
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

        return view;
    }
}
