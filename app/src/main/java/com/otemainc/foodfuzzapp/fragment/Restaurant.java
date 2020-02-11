package com.otemainc.foodfuzzapp.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import com.otemainc.foodfuzzapp.R;
import com.otemainc.foodfuzzapp.utility.DrinksRetriever;
import com.otemainc.foodfuzzapp.utility.RestaurantRetriever;


/**
 * A simple {@link Fragment} subclass.
 */
public class Restaurant extends Fragment {
    private GridView restaurant;
    private ProgressBar myprogressbar;

    public Restaurant() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant, container, false);
        restaurant = view.findViewById(R.id.restaurantContainer);
        myprogressbar = view.findViewById(R.id.restaurantProgressBar);
        new RestaurantRetriever(getActivity()).retrieveRestaurant(restaurant,myprogressbar);
        return view;
    }

}
