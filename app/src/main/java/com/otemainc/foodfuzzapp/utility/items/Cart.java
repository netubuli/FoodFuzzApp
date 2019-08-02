package com.otemainc.foodfuzzapp.utility.items;

public class Cart {
    private int id;
    private String title;
    private String cost;
    private String seller;
    private int quantity;

    public Cart(int id,  String title, String cost, String seller, int quantity) {
        this.id = id;
        this.title = title;
        this.cost = cost;
        this.seller = seller;
        this.quantity = quantity;
    }
    public int getId(){
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCost() {
        return cost;
    }

    public String getSeller() {
        return seller;
    }

    public int getQuantity() {
        return quantity;
    }
}
