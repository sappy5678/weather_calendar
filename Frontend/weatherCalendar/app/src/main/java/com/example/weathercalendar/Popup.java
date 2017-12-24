package com.example.weathercalendar;

import com.framgia.library.calendardayview.data.IPopup;

import java.util.Calendar;

/**
 * Created by user on 2017/12/10.
 */

public class Popup implements IPopup {


    private  Integer eventID;
    private  Integer eventIndex;
    Calendar startTime;
    Calendar endTime;

    String imageStart;
    String imageEnd;

    String title;

    String description;

    String quote;

    public Popup() {

    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    @Override
    public String getQuote() {
        return quote;
    }

    public void setImageStart(String imageStart) {
        this.imageStart = imageStart;
    }

    @Override
    public String getImageStart() {
        return imageStart;
    }

    public void setImageEnd(String imageEnd) {
        this.imageEnd = imageEnd;
    }

    @Override
    public String getImageEnd() {
        return imageEnd;
    }

    @Override
    public Boolean isAutohide() {
        return false;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    @Override
    public Calendar getStartTime() {
        return startTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    @Override
    public Calendar getEndTime() {
        return endTime;
    }
    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }
    public Integer getEventIndex() {
        return eventIndex;
    }

    public void setEventIndex(Integer eventIndex) {
        this.eventIndex = eventIndex;
    }
}

