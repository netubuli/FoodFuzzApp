package com.otemainc.foodfuzzapp.utility.items;

public class Drink {
    private int id;
    private String image;
    private String title;
    private String cost;
    private String seller;

    public Drink(int id, String image, String title, String cost,String seller) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.cost = cost;
        this.seller=seller;
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

    public String getSeller(){
        return seller;
    }

}
