package com.otemainc.foodfuzzapp.utility;

import android.content.Context;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.otemainc.foodfuzzapp.utility.adapter.FoodGridviewAdapter;
import com.otemainc.foodfuzzapp.utility.items.Food;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataRetriever {
    private static final String URL_FOOD = "http://192.168.100.250:8082/foodfuzzbackend/auth/login.php";
    private final Context f;
    private FoodGridviewAdapter foodGridviewAdapter;

    public DataRetriever(Context f) {
        this.f = f;
    }
    public void retrieveFood(final GridView gridView, final ProgressBar myprogressbar){
        final ArrayList <Food> foods = new ArrayList<>();
        myprogressbar.setIndeterminate(true);
        myprogressbar.setVisibility(View.VISIBLE);
        AndroidNetworking.get(URL_FOOD).setPriority(Priority.MEDIUM).build().getAsJSONArray(new JSONArrayRequestListener() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject;
                Food food;
                try{
                    for(int i=0; i<response.length();i++){
                        jsonObject = response.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String name = jsonObject.getString("name");
                        String cost = jsonObject.getString("cost");
                        String image = jsonObject.getString("image");
                        food = new Food(URL_FOOD+"../images/"+ image, name, cost);
                        foods.add(food);
                    }
                    foodGridviewAdapter = new FoodGridviewAdapter(f,foods);
                    myprogressbar.setVisibility(View.GONE);

                }catch (JSONException e){
                    myprogressbar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ANError anError) {
                myprogressbar.setVisibility(View.GONE);
                anError.printStackTrace();
            }
        });
    }
}
