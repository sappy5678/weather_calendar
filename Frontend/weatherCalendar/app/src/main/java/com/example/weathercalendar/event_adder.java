package com.example.weathercalendar;

import android.*;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;

/**
 * Created by user on 2017/12/22.
 */

public class event_adder extends AppCompatActivity {
    //    @BindView(R.id.titleCon)
//    TextView title;
//    @BindView(R.id.descriptionCon)
//    TextView description;
//    @BindView(R.id.locationCon)
//    Spinner locationChosser;
//    @BindView(R.id.beginMessage)
//    TextView BeginMes;
//    @BindView(R.id.endMessage)
//    TextView EndMes;
//    @BindView(R.id.beginTime)
//    Button btnBeginning;
//    @BindView(R.id.endTime)
//    Button btnEnd;
//    @BindView(R.id.send)
//    Button btnSend;
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
    Button btncalendar_chooser;
    private DatePickerDialog dataPicker;
    private TimePickerDialog timePicker;
    public Calendar initCal = Calendar.getInstance();
    private Calendar beginCal = Calendar.getInstance();
    private Calendar endCal = Calendar.getInstance();
    private int calendar_id = 0;
    private String loc="";
    SimpleDateFormat spf = new SimpleDateFormat("HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_adder);
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
        btncalendar_chooser = (Button) findViewById(R.id.calendar_chooser);
        final ArrayAdapter<CharSequence> lunchList = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.TW_location,
                android.R.layout.simple_spinner_dropdown_item);
        locationChosser.setAdapter(lunchList);
        //initial
        loc=(String)lunchList.getItem(0);
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
        btncalendar_chooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query_calendar();
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeZone zone=initCal.getTimeZone();

                ContentResolver cr = getContentResolver();
                ContentValues values = new ContentValues();
                values.put(Events.DTSTART, beginCal.getTimeInMillis());
                values.put(Events.DTEND, endCal.getTimeInMillis());
                values.put(Events.TITLE, title.getText().toString());
                values.put(Events.DESCRIPTION, description.getText().toString());
                values.put(Events.CALENDAR_ID, calendar_id);
                values.put(Events.EVENT_TIMEZONE, zone.getID());
                values.put(Events.EVENT_LOCATION, loc);
                @SuppressLint("MissingPermission") Uri uri = cr.insert(Events.CONTENT_URI, values);
                Toast toast = Toast.makeText(event_adder.this,"新增事件中", Toast.LENGTH_LONG);
                toast.show();
                // Intent intent = new Intent(event_adder.this, DayCalendar.class);
                // //        Calendar test=dateToCalendar(date.getDate());
                // Bundle bundle=new Bundle();
                // bundle.putSerializable("user", (Serializable)beginCal);
                // intent.putExtras(bundle);
                // startActivity(intent);
                // finish();

                finish();
            }
        });

//        dataPicker = new DatePickerDialog(getApplicationContext(),new DatePickerDialog.OnDateSetListener()
//        {
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
//            {
////                beginCal.set(Calendar.YEAR, year);
////                beginCal.set(Calendar.MONTH, monthOfYear);
////                beginCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
////                String myFormat = "yyyy/MM/dd";
////                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.TAIWAN);
////                etBirthday.setText(sdf.format(m_Calendar.getTime()));
//            })
//        };
    }
    protected void timePicker(final TextView Mes,final TextView TimeMes,final Calendar cal)
    {
        dataPicker=new DatePickerDialog(event_adder.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                cal.set(year,month,dayOfMonth);
                Mes.setText(String.valueOf(year) + "/" + String.valueOf(month + 1) + "/" + String.valueOf(dayOfMonth));

                timePicker.show();
            }
        },
                initCal.get(Calendar.YEAR),
                initCal.get(Calendar.MONTH),
                initCal.get(Calendar.DAY_OF_MONTH));
        dataPicker.show();
        timePicker=new TimePickerDialog(event_adder.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                cal.set(Calendar.HOUR_OF_DAY,hourOfDay);
                cal.set(Calendar.MINUTE,minute);
                cal.getTime();
                //TimeMes.setText(String.valueOf(hourOfDay)+":"+String.valueOf(minute));
                TimeMes.setText(spf.format(cal.getTime()));
            }
        },
                initCal.get(Calendar.HOUR_OF_DAY),
                initCal.get(Calendar.MINUTE),
                true);

    }
    public void query_calendar() {
        // 設定要返回的資料
        String[] EVENT_PROJECTION = new String[]{
                CalendarContract.Calendars._ID,                             // 0 日歷ID
                CalendarContract.Calendars.ACCOUNT_NAME,                // 1 日歷所屬的帳戶名稱
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,       // 2 日歷名稱
                CalendarContract.Calendars.OWNER_ACCOUNT,                  // 3 日歷擁有者
                CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL,       // 4 對此日歷所擁有的權限
        };
        // 根據上面的設定，定義各資料的索引，提高代碼的可讀性
        int PROJECTION_ID_INDEX = 0;
        int PROJECTION_ACCOUNT_NAME_INDEX = 1;
        int PROJECTION_DISPLAY_NAME_INDEX = 2;
        int PROJECTION_OWNER_ACCOUNT_INDEX = 3;
        int PROJECTION_CALENDAR_ACCESS_LEVEL = 4;
        // 取得在EditText的帳戶名稱
        SharedPreferences sp = getSharedPreferences("USER", MODE_PRIVATE);

        // String targetAccount = getResources().getString(R.string.targetAccount);
        String targetAccount = sp.getString("USER", "");

        // 查詢日歷
        Cursor cur;
        ContentResolver cr = getContentResolver();
        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        // 定義查詢條件，找出屬於上面Google帳戶及可以完全控制的日歷
        String selection = "((" + CalendarContract.Calendars.ACCOUNT_NAME + " = ?) AND ("
                + CalendarContract.Calendars.ACCOUNT_TYPE + " = ?) AND ("
                + CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL + " = ?))";
        String[] selectionArgs = new String[]{targetAccount,
                "com.google",
                Integer.toString(CalendarContract.Calendars.CAL_ACCESS_OWNER)};
        // 因為targetSDK=25，所以要在Apps運行時檢查權限
        int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.READ_CALENDAR);
        // 建立List來暫存查詢的結果
        final List<String> accountNameList = new ArrayList<>();
        final List<Integer> calendarIdList = new ArrayList<>();
        // 如果使用者給了權限便開始查詢日歷
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            cur = cr.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);
            if (cur != null) {
                while (cur.moveToNext()) {
                    long calendarId = 0;
                    String accountName = null;
                    String displayName = null;
                    String ownerAccount = null;
                    int accessLevel = 0;
                    // 取得所需的資料
                    calendarId = cur.getLong(PROJECTION_ID_INDEX);
                    accountName = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX);
                    displayName = cur.getString(PROJECTION_DISPLAY_NAME_INDEX);
                    ownerAccount = cur.getString(PROJECTION_OWNER_ACCOUNT_INDEX);
                    accessLevel = cur.getInt(PROJECTION_CALENDAR_ACCESS_LEVEL);
                    Log.i("query_calendar", String.format("calendarId=%s", calendarId));
                    Log.i("query_calendar", String.format("accountName=%s", accountName));
                    Log.i("query_calendar", String.format("displayName=%s", displayName));
                    Log.i("query_calendar", String.format("ownerAccount=%s", ownerAccount));
                    Log.i("query_calendar", String.format("accessLevel=%s", accessLevel));
                    // 暫存資料讓使用者選擇
                    accountNameList.add(displayName);
                    calendarIdList.add((int) calendarId);
                }
                cur.close();
            }
            if (calendarIdList.size() != 0) {
                // 建立一個Dialog讓使用者選擇日歷
                AlertDialog.Builder adb = new AlertDialog.Builder(this);
                final CharSequence items[] = accountNameList.toArray(new CharSequence[accountNameList.size()]);
                adb.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TextView targetCalendarId = (TextView) findViewById(R.id.calendar_name);
                        targetCalendarId.setText(items[which].toString());
                        calendar_id=calendarIdList.get(which);
                        dialog.dismiss();
                    }
                });
                adb.setNegativeButton("CANCEL", null);
                adb.show();
            }
            else {
                Toast toast = Toast.makeText(this, "找不到日歷", Toast.LENGTH_LONG);
                toast.show();
            }
        }
        else {
            Toast toast = Toast.makeText(this, "沒有所需的權限", Toast.LENGTH_LONG);
            toast.show();
        }
    }

}
