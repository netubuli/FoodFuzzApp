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
import com.otemainc.foodfuzzapp.utility.adapter.DrinkGridviewAdapter;
import com.otemainc.foodfuzzapp.utility.adapter.FoodGridviewAdapter;
import com.otemainc.foodfuzzapp.utility.items.Drink;
import com.otemainc.foodfuzzapp.utility.items.Food;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DrinksRetriever {
    private static final String URL_DRINK = "http://192.168.100.250:8082/foodfuzzbackend/market/drink/drink.php";
    private final Context d;
    private DrinkGridviewAdapter drinkGridviewAdapter;

    public DrinksRetriever(Context d) {
        this.d = d;
    }
    public void retrieveDrink(final GridView gridView, final ProgressBar myprogressbar){
        final ArrayList <Drink> drinks = new ArrayList<>();
        myprogressbar.setIndeterminate(true);
        myprogressbar.setVisibility(View.VISIBLE);
        StringRequest drinkStringRequest = new StringRequest(Request.Method.GET, URL_DRINK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject;
                        Drink drink;
                        try{
                            JSONObject drinkObject = new JSONObject(response);
                            JSONArray drinkArray = drinkObject.getJSONArray("drink");

                            for(int i=0; i<drinkArray.length();i++){
                                jsonObject = drinkArray.getJSONObject(i);
                                int id = jsonObject.getInt("id");
                                String name = jsonObject.getString("name");
                                String cost = jsonObject.getString("cost");
                                String image = jsonObject.getString("image");
                                drink = new Drink(id,"https://foodfuzz.co.ke/foodfuzzbackend/market/uploads/images/"+ image, name, "KSH "+cost);
                                drinks.add(drink);
                            }
                            drinkGridviewAdapter = new DrinkGridviewAdapter(d,drinks);
                            gridView.setAdapter(drinkGridviewAdapter);
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
