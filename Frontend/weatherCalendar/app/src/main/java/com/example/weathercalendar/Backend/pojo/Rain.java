package com.example.weathercalendar.Backend.pojo;

import java.sql.Timestamp;

/**
 * Created by sappy5678 on 12/14/17.
 */

public class Rain {
    private String endtime;

    private String id;

    private String starttime;

    private String location;

    private String value;

    public Timestamp getEndtime ()
    {
        return Timestamp.valueOf(endtime);
    }

    public void setEndtime (String endtime)
    {
        this.endtime = endtime;
    }

    public void setEndtime (Timestamp endtime)
    {
        this.endtime = endtime.toString();
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public Timestamp getStarttime ()
    {
        return Timestamp.valueOf(starttime);
    }

    public void setStarttime (String starttime)
    {
        this.starttime = starttime;
    }

    public void setStarttime (Timestamp starttime)
    {
        this.starttime = starttime.toString();
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

    @Override
    public String toString()
    {
        return "Rain [id = "+id+", starttime = "+starttime+", endtime = "+endtime+", location = "+location+", value = "+value+"]";
    }
}
