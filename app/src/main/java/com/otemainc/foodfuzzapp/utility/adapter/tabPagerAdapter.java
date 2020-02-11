package com.otemainc.foodfuzzapp.utility.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.otemainc.foodfuzzapp.fragment.Alcohol;
import com.otemainc.foodfuzzapp.fragment.Cart;
import com.otemainc.foodfuzzapp.fragment.Drink;
import com.otemainc.foodfuzzapp.fragment.Food;
import com.otemainc.foodfuzzapp.fragment.Restaurant;

public class tabPagerAdapter extends FragmentStatePagerAdapter {
    String[] tabArray = new String[]{"Meals","Soft Drinks","Alcoholic Drinks","Restaurants","Cart"};
    Integer tabno = 5;

    public tabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabArray[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Food food = new Food();
                return  food;
            case 1:
                Drink drink = new Drink();
                return drink;
            case 2:
                Alcohol alcohol = new Alcohol();
                return alcohol;
            case 3:
                Restaurant restaurant = new Restaurant();
                return restaurant;

            case 4:
                Cart cart = new Cart();
                return cart;
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabno;
    }
}
