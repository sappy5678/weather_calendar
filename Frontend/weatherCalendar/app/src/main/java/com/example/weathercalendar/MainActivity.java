package com.example.weathercalendar;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;

import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.weathercalendar.calendar.AccountCalendar;
import com.example.weathercalendar.calendar.decorators.HighlightWeekendsDecorator;
import com.example.weathercalendar.calendar.decorators.OneDayDecorator;
import com.example.weathercalendar.calendar.pojo.EventData;
import com.example.weathercalendar.calendar.decorators.EventDecorator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        OnDateSelectedListener, OnMonthChangedListener {
    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();
    //my variable
    @BindView(R.id.calendarView)
    MaterialCalendarView widget;
    //my variable end
    @Override
    protected  void onResume() {
        super.onResume();
        new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        widget.setOnDateChangedListener(this);
        widget.setOnDateChangedListener(this);
        widget.setOnMonthChangedListener(this);
        //秀出整張表的日期 不限制當月日期
        widget.setShowOtherDates(MaterialCalendarView.SHOW_ALL);
        //高亮週末與當天粗體
        widget.addDecorators(
//                new MySelectorDecorator(this),
                new HighlightWeekendsDecorator(),
                oneDayDecorator
        );
        //背景thread跑事件紅點
        new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent jump=new Intent(getApplicationContext(),event_adder.class);
                startActivity(jump);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }
    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @Nullable CalendarDay date, boolean selected) {
//        textView.setText(getSelectedDatesString());
        Intent intent = new Intent(this, DayCalendar.class);
//        Calendar test=dateToCalendar(date.getDate());
        Bundle bundle=new Bundle();
        bundle.putSerializable("user", (Serializable)dateToCalendar(date.getDate()));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        //noinspection ConstantConditions
//        getSupportActionBar().setTitle(FORMATTER.format(date.getDate()));
        new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());
    }
    //print english month , date(day) , month
    private String getSelectedDatesString() {
        CalendarDay date = widget.getSelectedDate();
        if (date == null) {
            return "No Selection";
        }

        return FORMATTER.format(date.getDate());
    }
    //Convert Date to Calendar
    private Calendar dateToCalendar(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected ArrayList<CalendarDay> checkMonthEvent(ArrayList<CalendarDay> EventCalendarDay)
    {
        AccountCalendar ac;
        CalendarDay date=widget.getCurrentDate();
        //開始時間與結束時間設置成該月一號與下一月的一號
        Date beginDate=new Date(date.getDate().toString());
        Date endDate=new Date(date.getDate().toString());
        beginDate.setDate(1);
        endDate.setMonth(endDate.getMonth()%12+1);
        endDate.setDate(1);
        ac = new AccountCalendar(getContentResolver(),getResources().getString(R.string.targetAccount));
        ac.updateCalendars();
        ArrayList<EventData> eventList = new ArrayList<>();
        Calendar beginTime = Calendar.getInstance();
        // Calendar beginTime = Calendar.getInstance();
        beginTime.set(Calendar.YEAR,beginDate.getYear()+1900);
        beginTime.set(Calendar.MONTH,beginDate.getMonth());
        beginTime.set(Calendar.DATE,beginDate.getDate());
        beginTime.set(Calendar.HOUR, 0);
        beginTime.set(Calendar.MINUTE,0);
        beginTime.set(Calendar.SECOND,0);
        beginTime.get(Calendar.SECOND);

        Calendar endTime = Calendar.getInstance();
        // Calendar endTime = Calendar.getInstance();
        endTime.set(Calendar.YEAR,endDate.getYear()+1900);
        endTime.set(Calendar.MONTH,endDate.getMonth());
        endTime.set(Calendar.DATE,endDate.getDate());
        endTime.set(Calendar.HOUR, 23);
        endTime.set(Calendar.MINUTE,59);
        endTime.set(Calendar.SECOND,59);
        endTime.get(Calendar.SECOND);
        for(int i = 0; i < ac.getAccountNameList().size(); ++i)
        {
            Log.i("Search Name",ac.getAccountNameList().get(i));
            eventList.addAll(ac.queryEvents(ac.getAccountNameList().get(i),beginTime,endTime));
        }
        Iterator<EventData> iterator = eventList.iterator();
        while(iterator.hasNext()){
            EventData temp=iterator.next();
//            if(temp.getBegin().getTime().getMonth()!=date.getMonth()){
//                iterator.remove();
//            }
            //活動開始天加上紅點
            CalendarDay day = CalendarDay.from(temp.getBegin());
            EventCalendarDay.add(day);
            //用來新增如果跨天的話（兩天）結束那天也加紅點
            if(!(temp.getBegin().getTime().getYear()==temp.getEnd().getTime().getYear()
                    &&temp.getBegin().getTime().getMonth()==temp.getEnd().getTime().getMonth()
                    &&temp.getBegin().getTime().getDate()==temp.getEnd().getTime().getDate()))
            {
                day = CalendarDay.from(temp.getEnd());
                EventCalendarDay.add(day);
            }

        }
        return EventCalendarDay;
    }
    //使用背景tread跑，加裝飾
    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            ArrayList<CalendarDay> dates= new ArrayList<>();
            checkMonthEvent(dates);
            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            if (isFinishing()) {
                return;
            }

            widget.addDecorator(new EventDecorator(Color.argb(125,247,71,71), calendarDays));
        }
    }
    public int getIntFromColor(int Red, int Green, int Blue){
        Red = (Red << 16) & 0x00FF0000; //Shift red 16-bits and mask out other stuff
        Green = (Green << 8) & 0x0000FF00; //Shift Green 8-bits and mask out other stuff
        Blue = Blue & 0x000000FF; //Mask out anything not blue.

        return 0xFF000000 | Red | Green | Blue; //0xFF000000 for 100% Alpha. Bitwise OR everything together.
    }


}
