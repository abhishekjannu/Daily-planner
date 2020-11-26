package com.cegep.dailyplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText editTexEmail = findViewById(R.id.email);
        EditText editTexPassword = findViewById(R.id.password);

        Button loginButton = findViewById(R.id.signin);

        loginButton.setOnClickListener(v -> {
            String email = String.valueOf(editTexEmail.getText());
            String password = String.valueOf(editTexPassword.getText());


            //TODO : Do some firestore stuff

            startActivity(new Intent(this, ScheduleList.class));
        });
    }


}