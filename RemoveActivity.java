

package com.example.eventplannerdemoapplicationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RemoveActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private ChildEventListener childEventListener;

    private ArrayList<EventPlanner> eventplannerList;
    private ArrayList<EventPlanner> Resultsearch;

    private EventPlannerAdapter Adapterlist;

    private TextView EditeDateRemove;
    private TextView displayDateRemove;
    private DatePickerDialog DatePick;
    private ListView result;

    private String yestd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove);

        this.database = FirebaseDatabase.getInstance();
        this.myRef = database.getReference("EventsPlan");

        this.eventplannerList = new ArrayList<EventPlanner>();
        this.Resultsearch = new ArrayList<EventPlanner>();


        this.childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                eventplannerList.add(dataSnapshot.getValue(EventPlanner.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        this.myRef.addChildEventListener(this.childEventListener);

        this.Adapterlist = new EventPlannerAdapter(this,this.Resultsearch);
        result = findViewById(R.id.resListView);
        result.setAdapter(this.Adapterlist);

        result.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final EventPlanner selectItem = (EventPlanner) parent.getItemAtPosition(position);
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot dsp: dataSnapshot.getChildren())
                        {
                            String e = dsp.child("eventDate").getValue().toString();
                            String exid = dsp.child("euid").getValue().toString();
                            String ename = dsp.child("eventName").getValue().toString();
                            if((e.equals(selectItem.getEventDate())) &&
                                    (ename.equals(selectItem.getEventName())))
                            {
                                myRef.child(exid).removeValue();
                                break;
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                eventplannerList.remove(selectItem);
                refresh(selectItem.getEventDate());
            }
        });


        EditeDateRemove = (TextView) findViewById(R.id.editTextDate);
        EditeDateRemove.setTextColor(Color.parseColor("#FFFF00"));
        EditeDateRemove.setGravity(Gravity.LEFT);
        EditeDateRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cdar = Calendar.getInstance();
                int emonth = cdar.get(Calendar.MONTH);
                int eday = cdar.get(Calendar.DAY_OF_MONTH);
                int eyear = cdar.get(Calendar.YEAR);
                DatePick = new DatePickerDialog(RemoveActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        EditeDateRemove.setText((month+1) + "/" + dayOfMonth + "/" + year);
                    }
                }, eyear, emonth, eday);
                DatePick.show();
            }
        });
    }

    public void refresh(String update_info)
    {
        this.Adapterlist.clear();
        for(EventPlanner ePlanner: this.eventplannerList)
        {
            if(ePlanner.getEventDate().equals(update_info))
            {
                this.Adapterlist.add(ePlanner);
            }
        }
        if(this.eventplannerList.size() == 0)
        {
            displayDateRemove.setText(R.string.resetTextDate);
        }
    }

    public void removeEventDate(View view)
    {
        this.Adapterlist.clear();
        boolean found = false;
        displayDateRemove = (TextView) findViewById(R.id.displayTextDateforRemove);
        String search_result = EditeDateRemove.getText().toString();
        for(EventPlanner ePlanner: this.eventplannerList)
        {
            if(ePlanner.getEventDate().equals(search_result))
            {
                this.Adapterlist.add(ePlanner);
                found = true;
                displayDateRemove.setText(ePlanner.getEventDate());
                displayDateRemove.setTextColor(Color.parseColor("#ff0000"));
            }
        }
        if(!(found))
        {
            Toast.makeText(this, "There are no events for "+ search_result + ".", Toast.LENGTH_LONG).show();
        }
        EditeDateRemove.setText(R.string.getTextDate);
    }

    public void HomeEvent(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
