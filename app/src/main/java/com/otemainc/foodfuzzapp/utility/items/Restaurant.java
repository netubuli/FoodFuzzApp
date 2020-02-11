package com.otemainc.foodfuzzapp.utility.items;

public class Restaurant {
    private String image, name;
    int id;

    public Restaurant(int id, String image, String name) {
        this.id = id;
        this.image = image;
        this.name = name;
    }
    public int getId(){
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }
}

