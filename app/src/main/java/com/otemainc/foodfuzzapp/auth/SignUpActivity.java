package com.otemainc.foodfuzzapp.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.otemainc.foodfuzzapp.R;
import org.jetbrains.annotations.NotNull;
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
                        save(name,email,phone,pass);
                    }
                }else{
                    signUp.setEnabled(true);
                }
                break;
        }
    }

    private void save(String name, String email, String phone, String pass) {
        
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
