package com.otemainc.foodfuzzapp.utility.items;

public class Zone {
    private int id;
    private String name;
    private String cost;
    public Zone(int id, String name, String cost) {
        this.id = id;
        this.name = name;
        this.cost = cost;
    }
    public int getId(){
        return id;
    }
    public String getName() {
        return name;
    }
    public String getCost() {
        return cost;
    }
}
