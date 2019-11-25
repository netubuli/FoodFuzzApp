package com.otemainc.foodfuzzapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity {
    ViewFlipper flipper, textFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flipper = findViewById(R.id.Flipper);
        textFlipper = findViewById(R.id.titles);
        int images [] = {R.drawable.slide, R.drawable.slide1, R.drawable.slide2, R.drawable.slide3};
        int titles [] = {R.string.cart, R.string.food, R.string.app_name, R.string.drinks};
        for(int image:images){
                flipperImages(image);
            }
        for(int title:titles) {
            flipperTitles(title);
        }

    }
    public void flipperImages(int image){
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);
        flipper.addView(imageView);
        flipper.setFlipInterval(4000);
        flipper.setAutoStart(true);
        flipper.setInAnimation(this,android.R.anim.slide_in_left);
        flipper.setOutAnimation(this,android.R.anim.slide_out_right);

    }
    public void flipperTitles(int title){
        TextView desc = new TextView(this);
        //desc.setTextColor(R.color.colorAccent);
        desc.setText(title);
        textFlipper.addView(desc);
        textFlipper.setFlipInterval(4000);
        textFlipper.setAutoStart(true);
        textFlipper.setInAnimation(this,android.R.anim.slide_in_left);
        textFlipper.setOutAnimation(this,android.R.anim.slide_out_right);

    }
}
