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
                auth(email,password);
                if(validate( email,password)){
                    signIn.setEnabled(false);

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