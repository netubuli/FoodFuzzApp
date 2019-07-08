package com.otemainc.foodfuzzapp.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.otemainc.foodfuzzapp.R;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    TextView signUp,skip, forgetPassword;
    EditText emailText, passwordText;
    Button signIn;
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
                Toast.makeText(this, "Forgot Password Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_login:
                final String email = emailText.getText().toString().trim();
                final String password = passwordText.getText().toString().trim();
                if(validate( email,password)){
                    auth(email,password);
                }
                break;

        }

    }
    //attempt to login the user
    private void auth(String email, String password) {

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