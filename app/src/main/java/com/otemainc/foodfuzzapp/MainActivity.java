package com.otemainc.foodfuzzapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.otemainc.foodfuzzapp.home.HomeActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ViewFlipper flipper, textFlipper;
    Button goToMarket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flipper = findViewById(R.id.Flipper);
        textFlipper = findViewById(R.id.titles);
        goToMarket = findViewById(R.id.gotToMarket);
        goToMarket.setOnClickListener(this);
        int images [] = {R.drawable.slide, R.drawable.slide1, R.drawable.slide2, R.drawable.slide3, R.drawable.slide4};
        int titles [] = {R.string.slide, R.string.slide1, R.string.slide2, R.string.slide3, R.string.slide4};
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
        flipper.setFlipInterval(5000);
        flipper.setAutoStart(true);
        flipper.setInAnimation(this,android.R.anim.slide_in_left);
        flipper.setOutAnimation(this,android.R.anim.slide_out_right);

    }
    public void flipperTitles(int title){
        TextView desc = new TextView(this);
        desc.setTextColor(getResources().getColor(R.color.colorAccent));
        desc.setTextSize(18);
        desc.setText(title);
        textFlipper.addView(desc);
        textFlipper.setFlipInterval(5000);
        textFlipper.setAutoStart(true);
        textFlipper.setInAnimation(this,android.R.anim.slide_in_left);
        textFlipper.setOutAnimation(this,android.R.anim.slide_out_right);

    }

    @Override
    public void onClick(View view) {
        Intent market = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(market);
        finish();
    }
}
