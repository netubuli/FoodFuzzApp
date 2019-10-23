package com.otemainc.foodfuzzapp;

import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class CheckOutActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private TableLayout checkoutItems;
    Db mydb;
    private FusedLocationProviderClient client;
    TextView explain, spinner_label,total,delivery;
    EditText code,deliveryLoc;
    Button pay, confirm;
    CheckBox currentLoc;
    private Spinner spinner;
    double totalCost = 0.00;
    double finalCost = 0.00;
    double longitude;
    double latitude;
    private static final String PATH_TO_SERVER = "https://foodfuzz.co.ke/foodfuzzbackend/market/zones/zones.php";
    //An ArrayList for Spinner Items
    private ArrayList<String> zones;
    //JSON Array
    private JSONArray result;

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
        deliveryLoc = findViewById(R.id.txtloc);
        currentLoc = findViewById(R.id.btnCurrentLoc);
        spinner =  findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        spinner_label = findViewById(R.id.Spinner_label);
        delivery = findViewById(R.id.deliveryFee);
        total = findViewById(R.id.totalCost);
        //Initializing the ArrayList
        zones = new ArrayList<String>();
        currentLoc.setOnClickListener(this);
        pay.setOnClickListener(this);
        confirm.setOnClickListener(this);
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
                totalCost += data.getDouble(2);
                checkoutItems.addView(tableRow);
            } while (data.moveToNext());
             total.setText(Double.toString(totalCost));
            data.close();
        }
        client = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(CheckOutActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            requestPermission();
            return;
        }
        client.getLastLocation().addOnSuccessListener(CheckOutActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    longitude = location.getLongitude();
                    latitude = location.getLatitude();
                }
            }
        });
        getData();
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION},1);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnNext:
                pay.setVisibility(View.GONE);
                spinner_label.setVisibility(View.GONE);
                spinner_label.setVisibility(View.GONE);
                currentLoc.setVisibility((View.GONE));
                deliveryLoc.setVisibility(View.GONE);
                explain.setVisibility(View.VISIBLE);
                code.setVisibility(View.VISIBLE);
                confirm.setVisibility(View.VISIBLE);
                String client = mydb.getUser();
                Cursor data = mydb.getAllCartItems();
                String orderId = RandomGenerator.generateRandomString(10);
                final ProgressDialog progressDialog = new ProgressDialog(CheckOutActivity.this, R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Placing your order please wait");
                progressDialog.show();
                if(currentLoc.isChecked()){
                    if (data.getCount() > 0) {
                        data.moveToFirst();
                        do {
                            save(orderId,client,data.getString(1),data.getString(3),data.getString(2),data.getString(4),longitude,latitude,"",progressDialog);

                        } while (data.moveToNext());
                        mydb.clearCart();
                        data.close();
                    }

                }else{
                    if (data.getCount() > 0) {
                        data.moveToFirst();
                        do {
                            save(orderId,client,data.getString(1),data.getString(3),data.getString(2),data.getString(4),0.00,0.00,deliveryLoc.getText().toString(),progressDialog);

                        } while (data.moveToNext());
                        mydb.clearCart();
                        data.close();
                    }
                }

                break;
            case R.id.btnconfirm:
                break;
            case R.id.btnCurrentLoc:
                if(currentLoc.isChecked()){
                   deliveryLoc.setVisibility(v.GONE);
                   spinner.setVisibility(v.GONE);
                   spinner_label.setVisibility(v.GONE);
                   delivery.setText("100.00");
                   finalCost = totalCost + Double.valueOf(delivery.getText().toString());
                   total.setText(Double.toString(finalCost));
            }else{
                    deliveryLoc.setVisibility(v.VISIBLE);
                    spinner.setVisibility(v.VISIBLE);
                    spinner_label.setVisibility(v.VISIBLE);
                    delivery.setText("0.00");
                    total.setText(Double.toString(totalCost));
                }
        }
    }
    private void save(final String orderId, final String client, final String name, final String Seller, final String amount, final String quantity, final double longi, final double lat, final String location, final ProgressDialog progressDialog) {
        String URL_ORDER = "https://foodfuzz.co.ke/foodfuzzbackend/market/orders/checkout.php";
        StringRequest orderStringRequest = new StringRequest(Request.Method.POST, URL_ORDER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject orderObject = new JSONObject(response);
                            String orderSuccess = orderObject.getString("success");
                            if(orderSuccess.equals("1")){
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
                params.put("orderId",orderId);
                params.put("name", name);
                params.put("email", client);
                params.put("tel", Seller);
                params.put("password", amount);
                params.put("quantity",quantity);
                params.put("longitude",String.valueOf(longi));
                params.put("latitude",String.valueOf(lat));
                params.put("location",location);
                return params;
            }
        };
        RequestQueue orderRequestQueue = Volley.newRequestQueue(this);
        orderRequestQueue.add(orderStringRequest);

    }

    private void getData(){
        //Creating a string request
        StringRequest stringRequest = new StringRequest(PATH_TO_SERVER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);

                            //Storing the Array of JSON String to our JSON Array
                            result = j.getJSONArray("zone");

                            //Calling method getStudents to get the students from the JSON Array
                            getZones(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private void getZones(JSONArray j){
        //Traversing through all the items in the json array
        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                zones.add(json.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        spinner.setAdapter(new ArrayAdapter<String>(CheckOutActivity.this, android.R.layout.simple_spinner_dropdown_item, zones));
    }

    //Method to get student name of a particular position
    private String getName(int position){
        String name="";
        try {
            //Getting object of given index
            JSONObject json = result.getJSONObject(position);

            //Fetching name from that object
            name = json.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return name;
    }

    //Doing the same with this method as we did with getName()
    private String getCost(int position){
        String cost="";
        try {
            JSONObject json = result.getJSONObject(position);
            cost = json.getString("cost");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cost;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        delivery.setText(getCost(position));
       finalCost = totalCost + Double.valueOf(delivery.getText().toString());
        total.setText(Double.toString(finalCost));
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        delivery.setText("0.00");
        total.setText(Double.toString(totalCost));

    }
}
