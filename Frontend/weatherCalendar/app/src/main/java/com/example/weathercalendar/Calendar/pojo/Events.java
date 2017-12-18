package com.example.weathercalendar.Calendar.pojo;

import java.util.Calendar;

/**
 * Created by sappy5678 on 12/17/17.
 */

public class Events {
    private Integer id;
    private String title;
    private Calendar begin = Calendar.getInstance();
    private Calendar end = Calendar.getInstance();;
    private String description;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Calendar getBegin() {
        return begin;
    }

    public void setBegin(Calendar begin) {
        this.begin = begin;
    }
    public void setBegin(Long begin) {
        this.begin.setTimeInMillis(begin);
    }

    public Calendar getEnd() {
        return end;
    }

    public void setEnd(Calendar end) {
        this.end = end;
    }
    public String getDescription() {
        return description;
    }
    public void setEnd(Long begin) {
        this.end.setTimeInMillis(begin);
    }

    public void setDescription(String description) {
        this.description = description;
    }
}