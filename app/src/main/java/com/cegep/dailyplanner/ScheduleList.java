package com.cegep.dailyplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ScheduleList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_list);

        findViewById(R.id.fab_new).setOnClickListener(v -> startActivity(new Intent(ScheduleList.this, AddNewScheduleActivity.class)));

        findViewById(R.id.addTextView).setOnClickListener(v -> startActivity(new Intent(ScheduleList.this, AddNewScheduleActivity.class)));

        RecyclerView scheduleListView = findViewById(R.id.scheduleList);
    }
}