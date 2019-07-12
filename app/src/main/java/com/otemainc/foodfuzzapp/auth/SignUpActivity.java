package com.otemainc.foodfuzzapp.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import com.otemainc.foodfuzzapp.utility.SharedPreferenceUtil;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    TextView signIn;
    EditText nameText,emailText, phoneText, passwordText, cPasswordText;
    Button signUp;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signUp = findViewById(R.id.btn_signup);
        signIn = findViewById(R.id.link_login);
        nameText = findViewById(R.id.input_name);
        emailText = findViewById(R.id.input_email);
        phoneText = findViewById(R.id.input_telephone);
        passwordText = findViewById(R.id.input_password);
        cPasswordText = findViewById(R.id.input_cpassword);
        signIn.setOnClickListener(this);
        signUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.link_login:
                Intent login = new Intent(SignUpActivity.this,SignInActivity.class);
                startActivity(login);
                finish();
                break;
            case R.id.btn_signup:
                final String name = nameText.getText().toString().trim();
                final String email = emailText.getText().toString().trim();
                final String phone = phoneText.getText().toString().trim();
                final String pass = passwordText.getText().toString().trim();
                final String cPass = cPasswordText.getText().toString().trim();
                //create and show the progressDialog
                final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this,
                        R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Creating Account...");
                progressDialog.show();
                if(validate(name,email,phone,pass,cPass)){
                    signUp.setEnabled(false);
                    if (!isValidPassword(pass)) {
                        passwordText.setError("Password should contain at least one number, one lowercase letter, one uppercase letter, one special character and no space");
                        progressDialog.dismiss();
                        signUp.setEnabled(true);
                    }else{
                        passwordText.setError(null);
                        save(name,email,phone,pass, progressDialog);
                    }
                }else{
                    signUp.setEnabled(true);
                }
                break;
        }
    }

    private void save(final String name, final String email, final String phone, final String pass, final ProgressDialog progressDialog) {
        String URL_REGIST = "http://192.168.100.250:8082/foodfuzzbackend/auth/register.php";
        StringRequest registerStringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject registerObject = new JSONObject(response);
                            String registerSuccess = registerObject.getString("success");
                            if(registerSuccess.equals("1")){
                                progressDialog.dismiss();
                                Toast.makeText(SignUpActivity.this,"Registration Successfull", Toast.LENGTH_SHORT).show();
                                new android.os.Handler().postDelayed(
                                        new Runnable() {
                                            public void run() {
                                                SharedPreferenceUtil.getInstance().saveString("is_logged_in","Yes");
                                                Intent main = new Intent(SignUpActivity.this, HomeActivity.class);
                                                main.putExtra("uName", name);
                                                main.putExtra("uEmail", email);
                                                startActivity(main);
                                                setResult(RESULT_OK, null);
                                                finish();
                                            }
                                        }, 3000);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(SignUpActivity.this,"Registration Failed " + e.toString(), Toast.LENGTH_SHORT).show();
                            signUp.setEnabled(true);

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(SignUpActivity.this,"Registration Error " + error.toString(), Toast.LENGTH_SHORT).show();
                        signUp.setEnabled(true);
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
        RequestQueue registerrequestQueue = Volley.newRequestQueue(this);
        registerrequestQueue.add(registerStringRequest);

    }

    private boolean validate(@NotNull String name, String email, String phone, String pass, String cpass) {
        boolean valid = true;
        if (name.isEmpty() || name.length() < 4) {
            nameText.setError("Name should be at least 4 characters");
            valid = false;
        } else {
            nameText.setError(null);
        }
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }
        if(phone.isEmpty() || phone.length() <10 ||!android.util.Patterns.PHONE.matcher(phone).matches()){
            phoneText.setError("Invalid Phone number");
            valid = false;
        }else{
            phoneText.setError(null);
        }
        if (pass.isEmpty() || pass.length() < 6) {
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
    //Check if password is valid
    public boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();

    }
}
