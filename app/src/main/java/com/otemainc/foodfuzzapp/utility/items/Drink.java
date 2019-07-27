package com.otemainc.foodfuzzapp.utility.items;

import android.media.Image;

public class Drink {
    private int id;
    private String image;
    private String title;
    private String cost;

    public Drink(int id, String image, String title, String cost) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.cost = cost;
    }
    public int getId(){
        return id;
    }
    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getCost() {
        return cost;
    }

}
