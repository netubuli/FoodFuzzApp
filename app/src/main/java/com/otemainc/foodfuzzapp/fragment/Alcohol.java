package com.otemainc.foodfuzzapp.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import com.otemainc.foodfuzzapp.R;
import com.otemainc.foodfuzzapp.utility.AlcoholRetriever;
import com.otemainc.foodfuzzapp.utility.DrinksRetriever;


/**
 * A simple {@link Fragment} subclass.
 */
public class Alcohol extends Fragment {
    private GridView alcohol;
    private ProgressBar myprogressbar;

    public Alcohol() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alcohol, container, false);
        alcohol = view.findViewById(R.id.alcoholContainer);
        myprogressbar = view.findViewById(R.id.alcoholProgressBar);
        new AlcoholRetriever(getActivity()).retrieveAlcohol(alcohol,myprogressbar);
        return view;
    }

}
