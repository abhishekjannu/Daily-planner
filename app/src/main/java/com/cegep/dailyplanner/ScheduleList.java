package com.cegep.dailyplanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ScheduleList extends AppCompatActivity {

    private FirebaseFirestore firestoreDB;

    private ScheduleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_list);

        firestoreDB = FirebaseFirestore.getInstance();

        findViewById(R.id.fab_new).setOnClickListener(v -> startActivityForResult(new Intent(ScheduleList.this, AddNewScheduleActivity.class), 1000));
        findViewById(R.id.addTextView).setOnClickListener(v -> startActivityForResult(new Intent(ScheduleList.this, AddNewScheduleActivity.class), 1000));

        RecyclerView scheduleListView = findViewById(R.id.scheduleList);
        scheduleListView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ScheduleAdapter();
        scheduleListView.setAdapter(adapter);
        // Add adapter to the recycler view
        loadAllSchedules();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loadAllSchedules();
    }

    private void loadAllSchedules() {
        String email = UserStorage.getUserEmail(this);
        if (email == null || email.isEmpty()) {
            Toast.makeText(this, "Please login again", Toast.LENGTH_SHORT).show();
            return;
        }

        firestoreDB.collection("users/" + email + "/schedules").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<SchedulePojo> list = new ArrayList<>();
                    if (task.getResult() != null)
                        for (DocumentSnapshot document : task.getResult()) {
                            SchedulePojo taskItem = document.toObject(SchedulePojo.class);
                            list.add(taskItem);
                        }

                    if (list.isEmpty()) {
                        Toast.makeText(ScheduleList.this, "No schedules", Toast.LENGTH_LONG).show();
                    } else {
                        adapter.setSchedules(list);
                    }
                } else {
                    Toast.makeText(ScheduleList.this, "Unable to retrieve schedules", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}