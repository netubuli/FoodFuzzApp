package com.otemainc.foodfuzzapp.utility.items;

public class Food {
    private String image, title, descr, cost, seller;
    int id, sellerId;

    public Food( int id, String image, String title, String descr, String cost, String seller, int sellerId) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.descr =descr;
        this.cost = cost;
        this.seller = seller;
        this.sellerId = sellerId;
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

    public String getDescr() {
        return descr;
    }

    public String getCost() {
        return cost;
    }

    public String getSeller() {
        return seller;
    }

    public int getSellerId() {
        return sellerId;
    }
}

