package com.otemainc.foodfuzzapp.splash;

import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.otemainc.foodfuzzapp.R;

public class SplashActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_splash);
        init();
    }

    private void init() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Check if the user has been authenticated


            }
        },5000);

    }
}
