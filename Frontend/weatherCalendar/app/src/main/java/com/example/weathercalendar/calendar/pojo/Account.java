package com.example.weathercalendar.calendar.pojo;

import java.util.ArrayList;

/**
 * Created by sappy5678 on 12/18/17.
 */

public class Account {

    private Integer id;
    private String displayName;
    private ArrayList<Calendars> events;

    public Account(Integer id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

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

    public ArrayList<Calendars> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Calendars> events) {
        this.events = events;
    }
}
