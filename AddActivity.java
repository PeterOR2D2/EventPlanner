

package com.example.eventplannerdemoapplicationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.EditText;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.app.TimePickerDialog;
import android.widget.TimePicker;
import android.graphics.Color;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private TextView editeventDate;
    private TextView editeventTime;
    private EditText editEventName;
    private EditText editEventDescription;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        this.database = FirebaseDatabase.getInstance();
        this.myRef = database.getReference().child("EventsPlan");

        editEventName = findViewById(R.id.editEventTextName);
        editEventName.setTextColor(Color.parseColor("#FFFF00"));

        editeventTime = (TextView) findViewById(R.id.editEventTime);
        editeventTime.setTextColor(Color.parseColor("#FFFF00"));
        editeventTime.setTextSize(30);
        editeventTime.setGravity(Gravity.CENTER);
        editeventTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar ecurrtime = Calendar.getInstance();
                int ehour = ecurrtime.get(Calendar.HOUR_OF_DAY);
                int eminute = ecurrtime.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(AddActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        editeventTime.setText(String.format("%02d:%02d", hourOfDay, minute));
                    }
                }, ehour, eminute, true);
                timePickerDialog.show();
            }
        });

        editeventDate = (TextView) findViewById(R.id.editEventDate);
        editeventDate.setTextColor(Color.parseColor("#FFFF00"));
        editeventDate.setGravity(Gravity.CENTER);
        editeventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cdar = Calendar.getInstance();
                int emonth = cdar.get(Calendar.MONTH);
                int eday = cdar.get(Calendar.DAY_OF_MONTH);
                int eyear = cdar.get(Calendar.YEAR);
                datePickerDialog = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editeventDate.setText((month+1) + "/" + dayOfMonth + "/" + year);
                    }
                }, eyear, emonth, eday);
                datePickerDialog.show();
            }
        });
    }

    public void addEvent(View view)
    {

        String EventName = editEventName.getText().toString();
        String EventTime = this.editeventTime.getText().toString();
        String EventDate = this.editeventDate.getText().toString();
        if((EventName.length() > 0) && (EventDate.length() > 0))
        {
            String uid_key = myRef.push().getKey();
            EventPlanner eplan = new EventPlanner(EventName, EventTime, EventDate, uid_key);
            myRef.child(uid_key).setValue(eplan);
            Toast.makeText(this, eplan.getEventName() + " has been created.", Toast.LENGTH_LONG).show();
        }
        editEventName.setText("");
        editeventTime.setText(R.string.getTextTime);
        editeventDate.setText(R.string.getTextDate);


    }

    public void HomeEvent(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
