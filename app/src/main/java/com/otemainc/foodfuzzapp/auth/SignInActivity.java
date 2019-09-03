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
import com.otemainc.foodfuzzapp.utility.Db;
import com.otemainc.foodfuzzapp.utility.SharedPreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    TextView signUp,skip, forgetPassword;
    EditText emailText, passwordText;
    Button signIn;
    Db mydb;
    private static String URL_LOGIN = "https://foodfuzz.co.ke/foodfuzzbackend/auth/login.php";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        signUp = findViewById(R.id.link_signup);
        signIn = findViewById(R.id.btn_login);
        skip = findViewById(R.id.skip);
        forgetPassword = findViewById(R.id.link_forgot_pass);
        emailText = findViewById(R.id.input_email);
        passwordText = findViewById(R.id.input_password);
        skip.setOnClickListener(this);
        signIn.setOnClickListener(this);
        signUp.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);
        mydb = new Db(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.skip:
                Toast.makeText(this, "Skip Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.link_signup:
                Intent register = new Intent(SignInActivity.this,SignUpActivity.class);
                startActivity(register);
                finish();
                break;
            case R.id.link_forgot_pass:
               Intent resetPwd = new Intent(SignInActivity.this,ForgetPasswordActivity.class);
               startActivity(resetPwd);
               finish();
                break;
            case R.id.btn_login:
                final String email = emailText.getText().toString().trim();
                final String password = passwordText.getText().toString().trim();
                final ProgressDialog progressDialog = new ProgressDialog(SignInActivity.this,
                        R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Authenticating...");
                progressDialog.show();
                if(validate( email,password)){
                    signIn.setEnabled(false);
                    //Call the auth method to login the user
                    auth(email,password, progressDialog);
                }
                else{
                    progressDialog.dismiss();
                    onLoginFailed();
                }
                break;
        }

    }

    private void onLoginFailed() {
        signIn.setEnabled(true);
    }

    //attempt to login the user
    private void auth(final String email, final String password, final ProgressDialog progressDialog) {
        StringRequest loginStringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject loginObject = new JSONObject(response);
                            String loginSuccess = loginObject.getString("success");
                            JSONArray loginArray = loginObject.getJSONArray("login");
                            //if login is successful
                            if(loginSuccess.equals("1")){
                                for(int i =0; i< loginArray.length();i++){
                                    JSONObject object = loginArray.getJSONObject(i);
                                    final  int id = object.getInt("id".trim());
                                    final String name = object.getString("name").trim();
                                    final String tel = object.getString("tel".trim());
                                    final String email = object.getString("email").trim();
                                    boolean isAdded=  mydb.addUser(id,name,tel,email);
                                  if(isAdded==true){
                                      Toast.makeText(SignInActivity.this, "Login Success.\n Welcome " +name, Toast.LENGTH_SHORT).show();
                                      progressDialog.dismiss();
                                      SharedPreferenceUtil.getInstance().saveString("is_logged_in", "Yes");
                                      Intent main = new Intent(SignInActivity.this, HomeActivity.class);
                                      main.putExtra("uName", name);
                                      main.putExtra("uEmail", email);
                                      startActivity(main);
                                      finish();
                                  }else{
                                      Toast.makeText(SignInActivity.this, "Login failed Unable to update local db", Toast.LENGTH_SHORT).show();
                                      progressDialog.dismiss();
                                      onLoginFailed();
                                  }
                                }
                            }
                            else {
                                Toast.makeText(SignInActivity.this,"Invalid Email/Password",Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                                onLoginFailed();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText( SignInActivity.this,"Login Error" + e.toString(),Toast.LENGTH_LONG).show();
                            onLoginFailed();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(SignInActivity.this,"Login Failed "+error.toString(), Toast.LENGTH_LONG).show();
                        onLoginFailed();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email",email);
                params.put("password",password);
                return params;
            }
        };
        RequestQueue loginRequestQue = Volley.newRequestQueue(this);
        loginRequestQue.add(loginStringRequest);
    }
//check if there are any blank inputs or the email is not valid or the password is less than four characters
    private boolean validate(String email, String password) {
        boolean valid = true;
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }
        if (password.isEmpty() || password.length() < 4) {
            passwordText.setError("Password should be at least 4 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }
        return  valid;
    }
}