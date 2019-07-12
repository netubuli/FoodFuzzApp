package com.otemainc.foodfuzzapp.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.otemainc.foodfuzzapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Food extends Fragment {
    private static final String TAG = "Food";
    ListView food;
    public Food() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_food, container, false);
        food = view.findViewById(R.id.FoodContainer);
        food.setDividerHeight(2);

        return view;
    }

}
