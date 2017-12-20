package com.example.weathercalendar.Backend.pojo;

import android.util.Log;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by sappy5678 on 12/14/17.
 */

public class Rain {
    private String endtime;

    private String id;

    private String starttime;

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

    public Calendar getEndtime()  {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss", Locale.ENGLISH);
        try {
            cal.setTime(sdf.parse(this.endtime));// all done
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.i("Time",cal.toString());
        return cal;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public void setEndtime(Calendar endtime) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        String s = sdf.format(endtime);// all done

        this.endtime = s;
    }

    public Calendar getStarttime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        try {
            cal.setTime(sdf.parse(this.starttime));// all done
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public void setStarttime(Calendar starttime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        String s = sdf.format(starttime);// all done

        this.starttime = s;
    }

    @Override
    public String toString()
    {
        return "Rain [id = "+id+", starttime = "+starttime+", endtime = "+endtime+", location = "+location+", value = "+value+"]";
    }
}
