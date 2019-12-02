package com.otemainc.foodfuzzapp.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.otemainc.foodfuzzapp.MainActivity;
import com.otemainc.foodfuzzapp.R;
import com.otemainc.foodfuzzapp.utility.AppConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgetPasswordActivity extends AppCompatActivity {
    private Button reset, cancel;
   private AutoCompleteTextView email;
    private ProgressDialog pDialog;
    private static final String TAG = SignUpActivity.class.getSimpleName();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        email = findViewById(R.id.resetEmail);
        cancel = findViewById(R.id.cancelReset);
        // Progress dialog
        pDialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        pDialog.setCancelable(false);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoMain();
            }
        });
        reset = findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emailText = email.getText().toString().trim();
                if(validate( emailText)) {
                    resetPassword(emailText);
                }
            }
        });

    }

    private void gotoMain() {
        Intent Gotomain = new Intent(ForgetPasswordActivity.this, MainActivity.class);
        startActivity(Gotomain);
        finish();
    }

    private boolean validate(String emailText) {
        boolean valid = true;
        if (emailText.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            email.setError("enter a valid email address");
            valid = false;
        } else {
            email.setError(null);
        }
        return valid;
    }

    private void resetPassword(final String emailText) {
        // Tag used to cancel the request
        String tag_string_req = "req_reset";
        pDialog.setMessage("Submitting request ...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_PWDRESET, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Reset Response: " + response);
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    // Check for error node in json
                    if (!error) {
                        // user password successfully updated
                        Toast.makeText(getApplicationContext(),jObj.getString("message")+" : Check your email for more details on how to reset your password",Toast.LENGTH_LONG).show();
                        // Launch main activity
                        gotoMain();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),"Reset Error "+errorMsg, Toast.LENGTH_LONG).show();
                        reset.setEnabled(true);
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    reset.setEnabled(true);
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " +error + ">>" + error.networkResponse.statusCode
                        + ">>" + error.networkResponse.data
                        + ">>" + error.getCause()
                        + ">>" + error.getMessage());
                Toast.makeText(getApplicationContext(), "Error Resetting password please try again "+error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
                reset.setEnabled(true);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("email", emailText);
                return params;
            }

        };

        // Adding request to request queue
        RequestQueue resetRequestQue = Volley.newRequestQueue(this);
        resetRequestQue.add(strReq);
    }
    private void showDialog() {
        if (!pDialog.isShowing()) {
            pDialog.setIndeterminate(true);
            pDialog.show();
        }
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
