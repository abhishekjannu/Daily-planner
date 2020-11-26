package com.cegep.dailyplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {
    FirebaseFirestore firestoreDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firestoreDB = FirebaseFirestore.getInstance();

        EditText editTexEmail = findViewById(R.id.email);
        EditText editTexPassword = findViewById(R.id.password);

        findViewById(R.id.signin).setOnClickListener(v -> {
            String email = String.valueOf(editTexEmail.getText());
            String password = String.valueOf(editTexPassword.getText());
            if (email.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
                return;
            }

            login(email, password);
        });

        findViewById(R.id.textViewRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });
    }

    private void login(String email, String password) {
        firestoreDB.collection("users")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            boolean found = false;
                            if (task.getResult() != null)
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    if (password.equalsIgnoreCase(document.getString("password"))) {
                                        UserStorage.insertUserName(LoginActivity.this, email);
                                        found = true;
                                        break;
                                    }
                                }
                            if (!found)
                                Toast.makeText(LoginActivity.this, "Username and password did not match!!", Toast.LENGTH_SHORT).show();
                            else {
                                startActivity(new Intent(LoginActivity.this, ScheduleList.class));
                                finish();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Could not find the user name!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}