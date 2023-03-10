

package com.example.eventplannerdemoapplicationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addEvent(View view)
    {
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }

    public void searchEvent(View view)
    {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    public void removeEvent(View view)
    {
        Intent intent = new Intent(this, RemoveActivity.class);
        startActivity(intent);
    }
}
