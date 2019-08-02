package com.otemainc.foodfuzzapp.utility.adapter;

import android.content.Context;

import com.otemainc.foodfuzzapp.utility.Db;
import com.otemainc.foodfuzzapp.utility.items.Cart;

import java.util.ArrayList;

public class CartListViewAdapter {
    Context c;
    ArrayList<Cart> items;
    Db db;

    public CartListViewAdapter(Context c, ArrayList<Cart> items) {
        this.c = c;
        this.items = items;
    }
}
