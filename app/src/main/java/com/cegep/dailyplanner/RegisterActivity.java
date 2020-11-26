package com.cegep.dailyplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseFirestore firestoreDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firestoreDB = FirebaseFirestore.getInstance();

        EditText editTexEmail = findViewById(R.id.email);
        EditText editTexPhoneNumber = findViewById(R.id.phoneNumber);
        EditText editTexFullName = findViewById(R.id.fullName);
        EditText editTexPassword = findViewById(R.id.password);

        findViewById(R.id.textViewLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

        findViewById(R.id.createAccountButton).setOnClickListener(v -> {
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
            checkUserAndRegister(email, password, fullName, phoneNumber);
        });
    }

    private void checkUserAndRegister(String email, String password, String fullName, String phoneNumber) {
        firestoreDB.collection("users")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.isSuccessful() && (task.getResult() == null || task.getResult().size() == 0)) {
                                registerUser(email, password, fullName, phoneNumber);
                            } else
                                Toast.makeText(RegisterActivity.this, "Email already exists!!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Not able to check for email! Please try again!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void registerUser(String email, String password, String fullName, String phoneNumber) {
        Map<String, String> userProfile = new HashMap<>();
        userProfile.put("fullName", fullName);
        userProfile.put("email", email);
        userProfile.put("password", password);
        userProfile.put("phone", phoneNumber);

        firestoreDB.collection("users").document(email).set(userProfile).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                UserStorage.insertUserName(RegisterActivity.this, email);
                startActivity(new Intent(RegisterActivity.this, ScheduleList.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, "Can not register! Please try again!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}