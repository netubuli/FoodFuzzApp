package com.otemainc.foodfuzzapp;

import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.otemainc.foodfuzzapp.utility.Db;
import com.otemainc.foodfuzzapp.utility.RandomGenerator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class CheckOutActivity extends AppCompatActivity implements View.OnClickListener {
    private TableLayout checkoutItems;
    Db mydb;
    private FusedLocationProviderClient client;
    TextView explain;
    EditText code;
    Button pay, confirm;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        checkoutItems = findViewById(R.id.tableLayout);
        pay = findViewById(R.id.btnNext);
        explain = findViewById(R.id.explanation);
        code = findViewById(R.id.mpesacode);
        confirm = findViewById(R.id.btnconfirm);
        confirm.setVisibility(View.GONE);
        explain.setVisibility(View.GONE);
        code.setVisibility(View.GONE);
        pay.setOnClickListener(this);
        confirm.setOnClickListener(this);
        mydb = new Db(this);
        Double totalCost = 0.0;
        Cursor data = mydb.getAllCartItems();
        String orderId = RandomGenerator.generateRandomString(10);
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
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnNext:
                pay.setVisibility(View.GONE);
                explain.setVisibility(View.VISIBLE);
                code.setVisibility(View.VISIBLE);
                confirm.setVisibility(View.VISIBLE);


                break;
            case R.id.btnconfirm:
                break;
        }
    }
    private void save(final String name, final String email, final String phone, final String pass, final ProgressDialog progressDialog) {
        String URL_ORDER = "https://foodfuzz.co.ke/foodfuzzbackend/market/orders/checkout.php";
        StringRequest registerStringRequest = new StringRequest(Request.Method.POST, URL_ORDER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject registerObject = new JSONObject(response);
                            String registerSuccess = registerObject.getString("success");
                            if(registerSuccess.equals("1")){
                                Toast.makeText(CheckOutActivity.this,"Order Placed Successfully " , Toast.LENGTH_SHORT).show();
                                pay.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(CheckOutActivity.this,"Saving Failed " + e.toString(), Toast.LENGTH_SHORT).show();
                            pay.setEnabled(true);

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(CheckOutActivity.this,"Saving Error " + error.toString(), Toast.LENGTH_SHORT).show();
                        pay.setEnabled(true);
                    }
                }){
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("tel", phone);
                params.put("password", pass);
                return params;
            }
        };
        RequestQueue registerRequestQueue = Volley.newRequestQueue(this);
        registerRequestQueue.add(registerStringRequest);

    }
}
