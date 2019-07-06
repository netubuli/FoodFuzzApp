package com.otemainc.foodfuzzapp.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.otemainc.foodfuzzapp.R;
import com.otemainc.foodfuzzapp.auth.SignInActivity;
import com.otemainc.foodfuzzapp.home.HomeActivity;
import com.otemainc.foodfuzzapp.utility.SharedPreferenceUtil;

public class SplashActivity extends AppCompatActivity {
       @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
    }

    private void init() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Check if the user has been authenticated
                if (SharedPreferenceUtil.getInstance().getString("is_logged_in").equalsIgnoreCase("")) {

                    //User is not yet logged in
                    // show the login activity
                    Intent login = new Intent(SplashActivity.this, SignInActivity.class);
                    startActivity(login);
                }else {
                    //User has logged in
                    //Show the HomeActivity
                    Intent home = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(home);
                }
                finish();
            }
        },5000);

    }
}
