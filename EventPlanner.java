package com.example.eventplannerdemoapplicationapp;

public class EventPlanner {

    private String eventName;
    private String eventTime;
    private String eventDate;
    private String uid;

    public EventPlanner()
    {
        this.eventName = "NA";
        this.eventTime = "NA";
        this.eventDate = "NA";
    }

    public EventPlanner(String ename, String etime, String edate, String euid)
    {
        this.eventName = ename;
        this.eventTime = etime;
        this.eventDate = edate;
        this.uid = euid;
    }

    public String getEventName() { return this.eventName; }

    public String getEventTime() { return this.eventTime; }

    public String getEventDate() { return this.eventDate; }

    public String getEUID() { return this.uid; }

    public String toString() { return this.eventName + "\n" + this.eventTime + "\n" + this.eventDate; }
}
