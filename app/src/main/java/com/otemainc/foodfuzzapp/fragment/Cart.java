package com.otemainc.foodfuzzapp.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import com.otemainc.foodfuzzapp.R;
import com.otemainc.foodfuzzapp.utility.CartRetriever;


public class Cart extends Fragment {
    private GridView cart;
    private ProgressBar progress;

    public Cart() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        cart = view.findViewById(R.id.cartConainer);
        progress = view.findViewById(R.id.cartProgressBar);
        new CartRetriever(getActivity()).retrieveCart(cart,progress);


        return view;
    }

}
