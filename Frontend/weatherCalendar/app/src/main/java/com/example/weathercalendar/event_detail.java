package com.example.weathercalendar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.weathercalendar.calendar.pojo.EventData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.logging.SimpleFormatter;

/**
 * Created by user on 2017/12/23.
 */

public class event_detail extends AppCompatActivity {
    TextView title;
    TextView description;
    Spinner locationChosser;
    TextView BeginMes;
    TextView EndMes;
    TextView BeginTimeMes;
    TextView EndTimeMes;
    Button btnBeginning;
    Button btnEnd;
    Button btnSend;
    Button btnDelete;
    Button btncalendar_chooser;
//    private Integer id;
//    private String title;
//    private Calendar begin = Calendar.getInstance();
//    private Calendar end = Calendar.getInstance();
//    private String description;
//    private String organizer;
//    private String calendar;
//    private String location;

    private DatePickerDialog dataPicker;
    private TimePickerDialog timePicker;
    public Calendar initCal = Calendar.getInstance();
    private Calendar beginCal = Calendar.getInstance();
    private Calendar endCal = Calendar.getInstance();
    private String loc="";
    private int calendar_id = 0;
    SimpleDateFormat spf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_detail);
        title = (TextView) findViewById(R.id.titleCon);
        description = (TextView) findViewById(R.id.descriptionCon);
        locationChosser = (Spinner) findViewById(R.id.locationCon);
        BeginMes = (TextView) findViewById(R.id.beginMessage);
        EndMes = (TextView) findViewById(R.id.endMessage);
        BeginTimeMes = (TextView) findViewById(R.id.beginTimeMes);
        EndTimeMes = (TextView) findViewById(R.id.endTimeMes);
        btnBeginning = (Button) findViewById(R.id.beginTime);
        btnEnd = (Button) findViewById(R.id.endTime);
        btnSend = (Button) findViewById(R.id.send);
        btnDelete=(Button)findViewById(R.id.delete);
        btncalendar_chooser = (Button) findViewById(R.id.calendar_chooser);
        final EventData eventDetail=(EventData)getIntent().getSerializableExtra("pack");
        final ArrayAdapter<CharSequence> lunchList = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.TW_location,
                android.R.layout.simple_spinner_dropdown_item);
        locationChosser.setAdapter(lunchList);
        spf = new SimpleDateFormat("HH:mm:ss");
        //set
        title.setText(eventDetail.getTitle());
        description.setText(eventDetail.getDescription());
        beginCal.setTime(eventDetail.getBegin().getTime());
        BeginMes.setText(String.valueOf(beginCal.get(Calendar.YEAR)) + "/" + String.valueOf(beginCal.get(Calendar.MONTH) + 1) + "/" + String.valueOf(beginCal.get(Calendar.DAY_OF_MONTH)));
        //BeginTimeMes.setText(String.valueOf(beginCal.get(Calendar.HOUR_OF_DAY))+":"+String.valueOf(beginCal.get(Calendar.MINUTE)));
        BeginTimeMes.setText(spf.format(beginCal.getTime()));
        endCal.setTime(eventDetail.getEnd().getTime());
        EndMes.setText(String.valueOf(endCal.get(Calendar.YEAR)) + "/" + String.valueOf(endCal.get(Calendar.MONTH) + 1) + "/" + String.valueOf(endCal.get(Calendar.DAY_OF_MONTH)));
        EndTimeMes.setText(spf.format(endCal.getTime()));
        //EndTimeMes.setText(String.valueOf(endCal.get(Calendar.HOUR_OF_DAY))+":"+String.valueOf(endCal.get(Calendar.MINUTE)));
        if(eventDetail.getLocation()==null||eventDetail.getLocation().isEmpty())
        {
            locationChosser.setSelection(0);
            loc=lunchList.getItem(0).toString();
        }
        else
        {
            int i=1;
            for(i=1;i<lunchList.getCount();i++)
            {
                if(eventDetail.getLocation().equals(((String) lunchList.getItem(i))))
                    break;
            }
            if(i==lunchList.getCount())
            {
                locationChosser.setSelection(0);
                loc=lunchList.getItem(0).toString();
                Toast toast = Toast.makeText(event_detail.this, "找不到相符地點", Toast.LENGTH_LONG);
                toast.show();
            }
            else
            {
                locationChosser.setSelection(i);
                loc=lunchList.getItem(i).toString();
            }

        }
        //setlisen
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ContentResolver cr = getContentResolver();
                ContentValues values = new ContentValues();
                Uri updateUri = null;
                // The new title for the event
                values.put(CalendarContract.Events.DTSTART, beginCal.getTimeInMillis());
                values.put(CalendarContract.Events.DTEND, endCal.getTimeInMillis());
                values.put(CalendarContract.Events.TITLE, title.getText().toString());
                values.put(CalendarContract.Events.DESCRIPTION, description.getText().toString());
                values.put(CalendarContract.Events.EVENT_LOCATION,loc );
                updateUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventDetail.getId());
                int rows = getContentResolver().update(updateUri, values, null, null);
                Toast toast = Toast.makeText(event_detail.this, "事件變更中"+"狀態: "+String.valueOf(rows), Toast.LENGTH_LONG);
                toast.show();

                finish();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentResolver cr = getContentResolver();
                ContentValues values = new ContentValues();
                Uri deleteUri = null;
                deleteUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventDetail.getId());
                int rows = getContentResolver().delete(deleteUri, null, null);
                Toast toast = Toast.makeText(event_detail.this, "事件刪除中"+"狀態: "+String.valueOf(rows), Toast.LENGTH_LONG);
                toast.show();
                finish();

            }
        });
        locationChosser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loc = lunchList.getItem(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnBeginning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePicker(BeginMes, BeginTimeMes, beginCal);

            }
        });
        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePicker(EndMes, EndTimeMes, endCal);
            }
        });
//        btncalendar_chooser.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                query_calendar();
//            }
//        });
    }
    protected void timePicker(final TextView Mes,final TextView TimeMes,final Calendar cal)
    {
        dataPicker = new DatePickerDialog(event_detail.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                cal.set(year,month,dayOfMonth);
                Mes.setText(String.valueOf(year) + "/" + String.valueOf(month + 1) + "/" + String.valueOf(dayOfMonth));
                timePicker.show();
            }
        },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));

        dataPicker.show();
        timePicker=new TimePickerDialog(event_detail.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                cal.set(Calendar.HOUR_OF_DAY,hourOfDay);
                cal.set(Calendar.MINUTE,minute);
                cal.getTime();
                // TimeMes.setText(String.valueOf(hourOfDay)+":"+String.valueOf(minute));
                TimeMes.setText(spf.format(cal.getTime()));
            }
        },
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true);

    }
}
