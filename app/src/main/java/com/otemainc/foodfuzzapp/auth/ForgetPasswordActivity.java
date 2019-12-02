package com.otemainc.foodfuzzapp.auth;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.otemainc.foodfuzzapp.R;

public class ForgetPasswordActivity extends AppCompatActivity {
    private MaterialButton reset;
   private AutoCompleteTextView email;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        email = findViewById(R.id.resetEmail);
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

    private void resetPassword(String emailText) {

    }
}
