package com.otemainc.foodfuzzapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import com.otemainc.foodfuzzapp.utility.Db;
import com.otemainc.foodfuzzapp.utility.items.Cart;

public class CheckOutActivity extends AppCompatActivity {
   private TableLayout checkoutItems;
    Db mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        checkoutItems = findViewById(R.id.tableLayout);
        mydb = new Db(this);
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

                checkoutItems.addView(tableRow);

            } while (data.moveToNext());
            data.close();
        }
    }
}
