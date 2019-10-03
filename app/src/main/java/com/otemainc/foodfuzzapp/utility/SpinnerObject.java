package com.otemainc.foodfuzzapp.utility;

import com.google.gson.annotations.SerializedName;
public class SpinnerObject {
    @SerializedName("name")
    private String name;
            private String cost;
            private int id;
    public SpinnerObject(){}
    public SpinnerObject(int id,String name, String cost) {
        this.id = id;
        this.name = name;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCost() {
        return cost;
    }
    public void setCost(String cost) {
        this.cost = cost;
    }
}
