package com.otemainc.foodfuzzapp.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import com.otemainc.foodfuzzapp.R;
import com.otemainc.foodfuzzapp.utility.DataRetriever;
import com.otemainc.foodfuzzapp.utility.DrinksRetriever;


/**
 * A simple {@link Fragment} subclass.
 */
public class Drink extends Fragment {
    private GridView drink;
    private ProgressBar myprogressbar;

    public Drink() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_drink, container, false);
        drink = view.findViewById(R.id.drinkContainer);
        myprogressbar = view.findViewById(R.id.drinkProgressBar);
        new DrinksRetriever(getActivity()).retrieveDrink(drink,myprogressbar);
        return view;
    }

}
