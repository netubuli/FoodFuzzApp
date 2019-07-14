package com.otemainc.foodfuzzapp.utility.adapter;

import android.media.Image;

public class RestaurantAdapter {
    private Image image;
    private String title;
    private String cost;

    public RestaurantAdapter(Image image, String title, String cost) {
        this.image = image;
        this.title = title;
        this.cost = cost;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
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
