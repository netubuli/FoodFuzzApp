package com.otemainc.foodfuzzapp.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.otemainc.foodfuzzapp.R;
import com.otemainc.foodfuzzapp.home.HomeActivity;
import com.otemainc.foodfuzzapp.utility.AppConfig;
import com.otemainc.foodfuzzapp.utility.Db;
import com.otemainc.foodfuzzapp.utility.SharedPreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    private TextView back;
    private EditText nameText,emailText, phoneText, passwordText, cPasswordText;
     private Button submit;
    private static final String TAG = SignUpActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    Db mydb;
    private String clientid, clientName, clientEmail, clientPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        submit = findViewById(R.id.btn_save);
        back = findViewById(R.id.link_back);
        nameText = findViewById(R.id.input_RFname);
        emailText = findViewById(R.id.input_Remail);
        phoneText = findViewById(R.id.input_RFtelephone);
        passwordText = findViewById(R.id.input_RFpassword);
        cPasswordText = findViewById(R.id.input_RFcpassword);
        // Progress dialog
        pDialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        pDialog.setCancelable(false);
        mydb = new Db(this);
        Cursor clientdata = mydb.getUser();
        if(clientdata.getCount()>0){
            clientdata.moveToFirst();
            do{
                clientid = clientdata.getString(0);
                clientName = clientdata.getString(1);
                clientPhone = clientdata.getString(2);
                clientEmail = clientdata.getString(3);

            }while (clientdata.moveToNext());
            clientdata.close();
            nameText.setText(clientName);
            emailText.setText(clientEmail);
            phoneText.setText(clientPhone);
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Home = new Intent(ProfileActivity.this, HomeActivity.class);
                startActivity(Home);
                finish();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String fullname = nameText.getText().toString().trim();
                final String email = emailText.getText().toString().trim();
                final String phone = phoneText.getText().toString().trim();
                final String password = passwordText.getText().toString().trim();
                final String cpass = cPasswordText.getText().toString().trim();
                if(validPassword(password, cpass)){
                    update( clientid, fullname, email, phone,password);
                    
                }
            }
        });
    }

    private boolean validPassword(String pass, String cpass) {
        boolean valid = true;
        if (pass.isEmpty() || pass.length() < 4) {
            passwordText.setError("Password should be at least 6 characters long");
            valid = false;
        }else {
            passwordText.setError(null);
        }
        if(cpass.isEmpty()){
            cPasswordText.setError("Retype Password");
            valid = false;
        }else if(!cpass.equals(pass)){
            cPasswordText.setError("Passwords do not match");
            valid = false;
        }else{
            cPasswordText.setError(null);
        }
        return valid;
    }

    private void update( final String clientId, final String fullname, final String email, final String phone, final String password) {
        // Tag used to cancel the request
        pDialog.setMessage("Registering ...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_UPDATEDATA, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Reset Response: " + response);
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        Toast.makeText(getApplicationContext(), "Updated!", Toast.LENGTH_LONG).show();
                        //clear shared frefs
                        SharedPreferenceUtil.getInstance().saveString("is_logged_in", "");
                        //delete user
                        mydb.deleteUser();
                        // Launch login activity
                        Intent intent = new Intent(ProfileActivity.this, SignInActivity.class);
                        startActivity(intent);
                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Reset Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), " An error has occurred during data update "+error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<>();
                params.put("Id", clientId);
                params.put("name", fullname);
                params.put("email", email);
                params.put("phone", phone);
                params.put("password", password);
                return params;
            }
        };
        // Adding request to request queue
        RequestQueue resetRequestQue = Volley.newRequestQueue(this);
        resetRequestQue.add(strReq);

    }
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
