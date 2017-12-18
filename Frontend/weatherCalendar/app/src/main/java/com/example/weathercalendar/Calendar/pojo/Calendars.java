package com.example.weathercalendar.Calendar.pojo;

import java.util.ArrayList;

/**
 * Created by sappy5678 on 12/18/17.
 */

public class Calendars {

    private Integer id;
    private String displayName;
    private ArrayList<Events> events;


    // constructor
    public Calendars(Integer id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }


    // getter and setter


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public ArrayList<Events> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Events> events) {
        this.events = events;
    }
}
