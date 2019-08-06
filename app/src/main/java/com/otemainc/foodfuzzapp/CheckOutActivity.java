package com.otemainc.foodfuzzapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.otemainc.foodfuzzapp.utility.Db;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class CheckOutActivity extends AppCompatActivity {
    private TableLayout checkoutItems;
    Db mydb;
    private FusedLocationProviderClient client;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        checkoutItems = findViewById(R.id.tableLayout);
        mydb = new Db(this);
        Double totalCost = 0.0;
        Cursor data = mydb.getAllCartItems();
        if (data.getCount() > 0) {
            data.moveToFirst();
            do {
                View tableRow = LayoutInflater.from(this).inflate(R.layout.table_item, null, false);
                TextView seller = tableRow.findViewById(R.id.name);
                TextView title = tableRow.findViewById(R.id.title);
                TextView cost = tableRow.findViewById(R.id.cost);
                TextView quantity = tableRow.findViewById(R.id.quantity);
                title.setText(data.getString(1));
                seller.setText(data.getString(3));
                quantity.setText(data.getString(4));
                cost.setText(data.getString(2));
                totalCost += data.getDouble(2);
                checkoutItems.addView(tableRow);
            } while (data.moveToNext());
            TextView total = findViewById(R.id.totalCost);
            total.setText(totalCost.toString());
            data.close();
        }
        requestPermission();
        client = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(CheckOutActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {

            return;
        }
        client.getLastLocation().addOnSuccessListener(CheckOutActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    Toast.makeText(CheckOutActivity.this, "We are here "+location, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION},1);
    }
}
