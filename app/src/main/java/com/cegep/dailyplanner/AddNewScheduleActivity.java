package com.cegep.dailyplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddNewScheduleActivity extends AppCompatActivity {

    private FirebaseFirestore firestoreDB;

    private SimpleDateFormat simpleDateFormat;
    private SimpleDateFormat simpleTimeFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_schedule);

        firestoreDB = FirebaseFirestore.getInstance();

        simpleDateFormat = new SimpleDateFormat("dd MMM YY", Locale.ENGLISH);
        simpleTimeFormat = new SimpleDateFormat("hh : mm a", Locale.ENGLISH);

        AppCompatEditText editTextDate = findViewById(R.id.date);
        AppCompatEditText editTextTime = findViewById(R.id.time);
        AppCompatEditText editTextDescription = findViewById(R.id.description);

        findViewById(R.id.ic_calender).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog pickerDialog = new DatePickerDialog(AddNewScheduleActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, month, dayOfMonth);

                        editTextDate.setText(simpleDateFormat.format(newDate.getTime()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                pickerDialog.show();
            }
        });

        findViewById(R.id.ic_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();

                TimePickerDialog timePickerDialog = new TimePickerDialog(AddNewScheduleActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                Calendar newTime = Calendar.getInstance();
                                newTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                newTime.set(Calendar.MINUTE, minute);
                                editTextTime.setText(simpleTimeFormat.format(newTime.getTime()));
                            }
                        }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        findViewById(R.id.createButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = String.valueOf(editTextDate.getText());
                String time = String.valueOf(editTextTime.getText());
                String description = String.valueOf(editTextDescription.getText());

                if (date.isEmpty()) {
                    Toast.makeText(AddNewScheduleActivity.this, "Please select date", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (time.isEmpty()) {
                    Toast.makeText(AddNewScheduleActivity.this, "Please select time", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (description.isEmpty()) {
                    Toast.makeText(AddNewScheduleActivity.this, "Please enter description", Toast.LENGTH_SHORT).show();
                    return;
                }

                addSchedule(date, time, description);
            }
        });
    }

    private void addSchedule(String date, String time, String description) {
        String email = UserStorage.getUserEmail(this);

        SchedulePojo schedule = new SchedulePojo();
        schedule.setDate(date);
        schedule.setTime(time);
        schedule.setDescription(description);

        firestoreDB.collection("users/" + email + "/schedules").add(schedule);

        Toast.makeText(AddNewScheduleActivity.this, "Schedule added successfully!", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }
}