package com.otemainc.foodfuzzapp.home;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.otemainc.foodfuzzapp.MainActivity;
import com.otemainc.foodfuzzapp.auth.ProfileActivity;
import com.otemainc.foodfuzzapp.R;
import com.otemainc.foodfuzzapp.auth.SignInActivity;
import com.otemainc.foodfuzzapp.utility.Db;
import com.otemainc.foodfuzzapp.utility.SharedPreferenceUtil;
import com.otemainc.foodfuzzapp.utility.adapter.tabPagerAdapter;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private TextView name,email;
    private ImageView image;
    TabLayout tab;
    ViewPager pager;
    Db mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();
        String extraName = intent.getStringExtra("uName");
        String extraEmail = intent.getStringExtra("uEmail");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        name = headerView.findViewById(R.id.uName);
        email = headerView.findViewById(R.id.uEmail);
        image = headerView.findViewById(R.id.uImage);
        name.setText(extraName);
        email.setText(extraEmail);
        setSupportActionBar(toolbar);
         ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        tab = findViewById(R.id.tabs);
        pager = findViewById(R.id.container);
        tabPagerAdapter pagerAdapter = new tabPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        tab.setupWithViewPager(pager);
        mydb = new Db(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if(id== R.id.action_logout){
            //Check if the user has been authenticated
            if (SharedPreferenceUtil.getInstance().getString("is_logged_in").equalsIgnoreCase("")) {
                //User is not yet logged in
                // show the login activity
                Toast.makeText(this,"You need to be logged in to complete this process", Toast.LENGTH_LONG).show();
                Intent login = new Intent(HomeActivity.this, SignInActivity.class);
                startActivity(login);
                finish();
            }else {
                SharedPreferenceUtil.getInstance().saveString("is_logged_in", "");
                mydb.deleteUser();
                Intent Main = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(Main);
                finish();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_reset_pwd) {
            //Check if the user has been authenticated
            if (SharedPreferenceUtil.getInstance().getString("is_logged_in").equalsIgnoreCase("")) {
                //User is not yet logged in
                // show the login activity
                Toast.makeText(this,"You need to be logged in to complete this process", Toast.LENGTH_LONG).show();
                Intent login = new Intent(HomeActivity.this, SignInActivity.class);
                startActivity(login);
                finish();
            }else {
                Intent goToReset = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(goToReset);
                finish();
            }
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_food) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
