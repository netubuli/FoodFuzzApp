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

import org.jetbrains.annotations.NotNull;


/**
 * A simple {@link Fragment} subclass.
 */
public class Food extends Fragment {
    private static final String TAG = "Food";
   private GridView food;
   private ProgressBar myprogressbar;
    public Food() {

    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_food, container, false);
        food = view.findViewById(R.id.FoodContainer);
       myprogressbar = view.findViewById(R.id.foodProgressBar);
        new DataRetriever(getActivity()).retrieveFood(food,myprogressbar);

        return view;
    }

}
