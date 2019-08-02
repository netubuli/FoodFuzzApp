package com.otemainc.foodfuzzapp.utility;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.otemainc.foodfuzzapp.utility.adapter.CartListViewAdapter;
import com.otemainc.foodfuzzapp.utility.items.Cart;

import java.util.ArrayList;

public class CartRetriever {
    private final Context c;
    private CartListViewAdapter cartListViewAdapter;
    Db mydb;

    public CartRetriever(Context c) {
        this.c = c;
    }

    public void retrieveCart(GridView listView, ProgressBar progress) {
        final ArrayList<Cart> items = new ArrayList<>();
        progress.setIndeterminate(true);
        progress.setVisibility(View.VISIBLE);
        mydb = new Db(c);
        Cart cart;
        Cursor res =mydb.getAllCartItems();
        if(res.getCount()>0){
            StringBuffer buffer = new StringBuffer();
            while(res.moveToNext()){
                int id = Integer.parseInt(res.getString(0));
                String name = res.getString(1);
                String cost = res.getString(2);
                String seller =res.getString(3);
                int quantity =Integer.parseInt(res.getString(4));
                cart = new Cart(id, name, cost, seller, quantity);
                items.add(cart);
                System.out.println();
            }
            cartListViewAdapter = new CartListViewAdapter(c,items);
            listView.setAdapter(cartListViewAdapter);
            progress.setVisibility(View.GONE);
        }
    }
}
