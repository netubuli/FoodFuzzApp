package com.otemainc.foodfuzzapp.utility.items;

public class Food {
    private String image, title, cost;

    public Food(String image, String title, String cost) {
        this.image = image;
        this.title = title;
        this.cost = cost;
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
