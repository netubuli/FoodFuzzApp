package com.otemainc.foodfuzzapp.utility;

import android.content.Context;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.otemainc.foodfuzzapp.utility.adapter.DrinkGridviewAdapter;
import com.otemainc.foodfuzzapp.utility.adapter.RestaurantGridviewAdapter;
import com.otemainc.foodfuzzapp.utility.items.Drink;
import com.otemainc.foodfuzzapp.utility.items.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RestaurantRetriever {
    private final Context d;
    private RestaurantGridviewAdapter restaurantGridviewAdapter;

    public RestaurantRetriever(Context d) {
        this.d = d;
    }
    public void retrieveRestaurant(final GridView gridView, final ProgressBar myprogressbar){
        final ArrayList <Restaurant> restaurants = new ArrayList<>();
        myprogressbar.setIndeterminate(true);
        myprogressbar.setVisibility(View.VISIBLE);
        StringRequest drinkStringRequest = new StringRequest(Request.Method.GET, AppConfig.URL_RESTAURANT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject;
                        Restaurant restaurant;
                        try{
                            JSONObject restaurantObject = new JSONObject(response);
                            JSONArray restaurantArray = restaurantObject.getJSONArray("restaurant");

                            for(int i=0; i<restaurantArray.length();i++){
                                jsonObject = restaurantArray.getJSONObject(i);
                                int id = jsonObject.getInt("id");
                                String name = jsonObject.getString("name");
                                String image = jsonObject.getString("image");
                                restaurant = new Restaurant(id,AppConfig.URL_IMAGE + image, name);
                                restaurants.add(restaurant);
                            }
                            restaurantGridviewAdapter = new RestaurantGridviewAdapter(d,restaurants);
                            gridView.setAdapter(restaurantGridviewAdapter);
                            myprogressbar.setVisibility(View.GONE);

                        }catch (JSONException e){
                            myprogressbar.setVisibility(View.GONE);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myprogressbar.setVisibility(View.GONE);
                        error.printStackTrace();
                    }
                });
        RequestQueue foodRequestQue = Volley.newRequestQueue(d);
        foodRequestQue.add(drinkStringRequest);
    }
}
