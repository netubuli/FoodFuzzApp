package com.otemainc.foodfuzzapp.utility.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.otemainc.foodfuzzapp.R;
import com.otemainc.foodfuzzapp.utility.Db;
import com.otemainc.foodfuzzapp.utility.items.Cart;

import java.util.ArrayList;

public class CartListViewAdapter extends BaseAdapter {
    Context c;
    ArrayList<Cart> items;
    Db db;

    public CartListViewAdapter(Context c, ArrayList<Cart> items) {
        this.c = c;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if(view==null){
            view = LayoutInflater.from(c).inflate(R.layout.cart_layout,null,false);
        }
        TextView name = view.findViewById(R.id.cname);
        TextView cost = view.findViewById(R.id.tcost);
        TextView seller = view.findViewById(R.id.cseller);
        TextView quantity = view.findViewById(R.id.cquantity);
        TextView updateCart = view.findViewById(R.id.updateCart);

        final Cart cart = (Cart) this.getItem(position);
        name.setText(cart.getTitle());
        cost.setText(cart.getCost());
        seller.setText(cart.getSeller());
        quantity.setText(Integer.toString(cart.getQuantity()));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new Db(c);
                int deleteItem = db.deleteItem(String.valueOf(cart.getId()));
                if(deleteItem>0){

                    Toast.makeText(c,  cart.getTitle() + " Successfully deleted from cart", Toast.LENGTH_SHORT).show();

                }

            }
        });
        return view;
    }
}
