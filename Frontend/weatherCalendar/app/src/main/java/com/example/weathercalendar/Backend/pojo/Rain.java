package com.example.weathercalendar.Backend.pojo;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by sappy5678 on 12/14/17.
 */

public class Rain {
    private Calendar endtime;

    private String id;

    private Calendar starttime;

    private String location;

    private String value;


    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }



    public String getLocation ()
    {
        return location;
    }


    public void setLocation (String location)
    {
        this.location = location;
    }

    public String getValue ()
    {
        return value;
    }

    public void setValue (String value)
    {
        this.value = value;
    }

    public Calendar getEndtime() {
        return endtime;
    }

    public void setEndtime(Calendar endtime) {
        this.endtime = endtime;
    }

    public Calendar getStarttime() {
        return starttime;
    }

    public void setStarttime(Calendar starttime) {
        this.starttime = starttime;
    }

    @Override
    public String toString()
    {
        return "Rain [id = "+id+", starttime = "+starttime+", endtime = "+endtime+", location = "+location+", value = "+value+"]";
    }
}
