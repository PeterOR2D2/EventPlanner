package com.example.eventplannerdemoapplicationapp;

import android.content.Context;
import android.graphics.Color;
import android.widget.ArrayAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import android.widget.TextView;

import com.google.android.gms.actions.ItemListIntents;


import java.util.ArrayList;
import java.util.List;

public class EventPlannerAdapter extends ArrayAdapter<EventPlanner>{
    private Context eContext;
    private List<EventPlanner> eventPlanlist = new ArrayList<EventPlanner>();

    public EventPlannerAdapter(Context context, ArrayList<EventPlanner> eplanlist)
    {
        super(context, 0, eplanlist);
        this.eContext = context;
        this.eventPlanlist = eplanlist;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View Itemlist = convertView;

        if (Itemlist == null) {
            Itemlist = LayoutInflater.from(this.eContext).inflate(R.layout.eventplanner_view, parent, false);
        }

        EventPlanner currEventPlanner = this.eventPlanlist.get(position);

        TextView eventName = (TextView) Itemlist.findViewById(R.id.textView_EventName);
        eventName.setTextColor(Color.parseColor("#FFFF00"));
        eventName.setText(currEventPlanner.getEventName());

        TextView eventDescription = (TextView) Itemlist.findViewById(R.id.textView_EventTime);
        eventDescription.setTextColor(Color.parseColor("#FFFF00"));
        eventDescription.setText(currEventPlanner.getEventTime());

        return Itemlist;
    }
}
