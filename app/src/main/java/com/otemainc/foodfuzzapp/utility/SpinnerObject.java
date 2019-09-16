package com.otemainc.foodfuzzapp.utility;

import com.google.gson.annotations.SerializedName;
public class SpinnerObject {
    @SerializedName("name")
    private String name;
            private String cost;
    public SpinnerObject(){}
    public SpinnerObject(String name, String cost) {
        this.name = name;
        this.cost = cost;
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
