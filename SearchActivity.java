

package com.example.eventplannerdemoapplicationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import android.os.Bundle;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private ChildEventListener childEventListener;

    private ArrayList<EventPlanner> eventplannerList;
    private ArrayList<EventPlanner> Resultsearch;

    private EventPlannerAdapter Adapterlist;
    private TextView editeDate;
    private TextView displayDate;
    private DatePickerDialog datePick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        this.database = FirebaseDatabase.getInstance();
        this.myRef = database.getReference().child("EventsPlan");

        this.eventplannerList = new ArrayList<EventPlanner>();
        this.Resultsearch = new ArrayList<EventPlanner>();

        this.childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                eventplannerList.add(dataSnapshot.getValue(EventPlanner.class));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        this.myRef.addChildEventListener(this.childEventListener);

        this.Adapterlist = new EventPlannerAdapter(this,this.Resultsearch);
        ListView result = findViewById(R.id.resultListView);
        result.setAdapter(this.Adapterlist);

        editeDate = (TextView) findViewById(R.id.editTextDate);
        editeDate.setTextColor(Color.parseColor("#FFFF00"));
        editeDate.setGravity(Gravity.LEFT);
        editeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cdar = Calendar.getInstance();
                int emonth = cdar.get(Calendar.MONTH);
                int eday = cdar.get(Calendar.DAY_OF_MONTH);
                int eyear = cdar.get(Calendar.YEAR);
                datePick = new DatePickerDialog(SearchActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editeDate.setText((month+1) + "/" + dayOfMonth + "/" + year);
                    }
                }, eyear, emonth, eday);
                datePick.show();
            }
        });
    }

    public void searchDate(View view)
    {
        this.Adapterlist.clear();
        boolean found = false;
        displayDate = (TextView) findViewById(R.id.displayTextDate);
        String search_result = editeDate.getText().toString();
        for(EventPlanner ePlanner: this.eventplannerList)
        {
            if(ePlanner.getEventDate().equals(search_result))
            {
                this.Adapterlist.add(ePlanner);
                found = true;
                displayDate.setText(ePlanner.getEventDate());
                displayDate.setTextColor(Color.parseColor("#ff0000"));
            }
        }
        if(!(found))
        {
            Toast.makeText(this, search_result + " has not been found.", Toast.LENGTH_LONG).show();
        }
        editeDate.setText(R.string.getTextDate);
    }

    public void HomeEvent(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
