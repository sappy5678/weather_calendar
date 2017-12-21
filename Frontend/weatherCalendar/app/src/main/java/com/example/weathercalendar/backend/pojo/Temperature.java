package com.example.weathercalendar.backend.pojo;

/**
 * Created by sappy5678 on 12/15/17.
 */

public class Temperature {
    private String endtime;

    private String id;

    private String starttime;

    private String location;

    private String value;

    public String getEndtime ()
    {
        return endtime;
    }

    public void setEndtime (String endtime)
    {
        this.endtime = endtime;
    }


    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getStarttime ()
    {
        return starttime;
    }

    public void setStarttime (String starttime)
    {
        this.starttime = starttime;
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
        return "Temperature [id = "+id+", starttime = "+starttime+", endtime = "+endtime+", location = "+location+", value = "+value+"]";
    }
}
