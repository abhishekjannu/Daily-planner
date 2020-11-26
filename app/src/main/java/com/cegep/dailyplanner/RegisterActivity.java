package com.cegep.dailyplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText editTexEmail = findViewById(R.id.email);
        EditText editTexPhoneNumber = findViewById(R.id.phoneNumber);
        EditText editTexFullName = findViewById(R.id.fullName);
        EditText editTexPassword = findViewById(R.id.password);
        Button createAccountButton = findViewById(R.id.createAccountButton);

        createAccountButton.setOnClickListener(v -> {
            String email = String.valueOf(editTexEmail.getText());
            String phoneNumber = String.valueOf(editTexPhoneNumber.getText());
            String fullName = String.valueOf(editTexFullName.getText());
            String password = String.valueOf(editTexPassword.getText());

            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter email!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (phoneNumber.isEmpty()) {
                Toast.makeText(this, "Please enter phone number!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (fullName.isEmpty()) {
                Toast.makeText(this, "Please enter full name!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.isEmpty()) {
                Toast.makeText(this, "Please enter password!", Toast.LENGTH_SHORT).show();
                return;
            }

            // TODO : Check user and signup
            startActivity(new Intent(this, ScheduleList.class));
        });
    }
}