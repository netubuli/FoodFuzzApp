package com.otemainc.foodfuzzapp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import com.otemainc.foodfuzzapp.CheckOutActivity;
import com.otemainc.foodfuzzapp.R;
import com.otemainc.foodfuzzapp.auth.SignInActivity;
import com.otemainc.foodfuzzapp.splash.SplashActivity;
import com.otemainc.foodfuzzapp.utility.CartRetriever;
import com.otemainc.foodfuzzapp.utility.SharedPreferenceUtil;


public class Cart extends Fragment {
    private GridView cart;
    private ProgressBar progress;
    private Button checkout;

    public Cart() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        cart = view.findViewById(R.id.cartConainer);
        progress = view.findViewById(R.id.cartProgressBar);
        new CartRetriever(getActivity()).retrieveCart(cart,progress);
        checkout = view.findViewById(R.id.button);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SharedPreferenceUtil.getInstance().getString("is_logged_in").equalsIgnoreCase("")) {
                    //User is not yet logged in
                    // show the login activity
                    Intent login = new Intent(Cart.this.getActivity(), SignInActivity.class);
                    startActivity(login);
                }
                else{
                    Intent chekoutView = new Intent(Cart.this.getActivity(), CheckOutActivity.class);
                    startActivity(chekoutView);
                }

            }
        });


        return view;
    }

}
