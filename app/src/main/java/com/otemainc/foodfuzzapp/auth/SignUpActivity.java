package com.otemainc.foodfuzzapp.auth;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.otemainc.foodfuzzapp.R;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    TextView signIn;
    EditText name,email, phone, password, cPassword;
    Button signUp;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signUp = findViewById(R.id.btn_signup);
        signIn = findViewById(R.id.link_login);
        name = findViewById(R.id.input_name);
        email = findViewById(R.id.input_email);
        phone = findViewById(R.id.input_telephone);
        password = findViewById(R.id.input_password);
        cPassword = findViewById(R.id.input_cpassword);
        signIn.setOnClickListener(this);
        signUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }
}
