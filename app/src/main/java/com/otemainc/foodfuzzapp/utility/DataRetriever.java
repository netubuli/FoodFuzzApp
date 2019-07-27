package com.otemainc.foodfuzzapp.utility;

import android.content.Context;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.otemainc.foodfuzzapp.auth.SignInActivity;
import com.otemainc.foodfuzzapp.utility.adapter.FoodGridviewAdapter;
import com.otemainc.foodfuzzapp.utility.items.Food;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataRetriever {
    private static final String URL_FOOD = "http://192.168.100.250:8082/foodfuzzbackend/market/food/food.php";
    private final Context f;
    private FoodGridviewAdapter foodGridviewAdapter;

    public DataRetriever(Context f) {
        this.f = f;
    }
    public void retrieveFood(final GridView gridView, final ProgressBar myprogressbar){
        final ArrayList <Food> foods = new ArrayList<>();
        myprogressbar.setIndeterminate(true);
        myprogressbar.setVisibility(View.VISIBLE);
        StringRequest foodStringRequest = new StringRequest(Request.Method.GET, URL_FOOD,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject;
                Food food;
                try{
                JSONObject foodObject = new JSONObject(response);
               JSONArray foodArray = foodObject.getJSONArray("food");

                    for(int i=0; i<foodArray.length();i++){
                        jsonObject = foodArray.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String name = jsonObject.getString("name");
                        String cost = jsonObject.getString("cost");
                        String image = jsonObject.getString("image");
                        food = new Food(id,URL_FOOD+"../uploads/images/"+ image, name, "KSH "+cost);
                        foods.add(food);
                    }
                    foodGridviewAdapter = new FoodGridviewAdapter(f,foods);
                    gridView.setAdapter(foodGridviewAdapter);
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
        RequestQueue foodRequestQue = Volley.newRequestQueue(f);
        foodRequestQue.add(foodStringRequest);
    }
}
