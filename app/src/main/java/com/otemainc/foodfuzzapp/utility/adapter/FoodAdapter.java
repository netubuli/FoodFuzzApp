package com.otemainc.foodfuzzapp.utility.adapter;

import android.media.Image;

public class FoodAdapter {
    private String image, title, cost;

    public FoodAdapter(String image, String title, String cost) {
        this.image = image;
        this.title = title;
        this.cost = cost;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
