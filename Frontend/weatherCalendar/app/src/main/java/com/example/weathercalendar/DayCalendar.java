package com.example.weathercalendar;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.weathercalendar.backend.WeatherApi;
import com.example.weathercalendar.backend.WeatherApiCreater;
import com.example.weathercalendar.backend.pojo.Rain;
import com.example.weathercalendar.calendar.AccountCalendar;
import com.example.weathercalendar.calendar.CustomDecoration;
import com.example.weathercalendar.calendar.pojo.Events;
import com.example.weathercalendar.gravatar.MD5Util;
import com.framgia.library.calendardayview.CalendarDayView;

import com.framgia.library.calendardayview.EventView;
import com.framgia.library.calendardayview.PopupView;
import com.framgia.library.calendardayview.data.IEvent;
import com.framgia.library.calendardayview.data.IPopup;
import com.framgia.library.calendardayview.decoration.CdvDecorationDefault;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 2017/12/8.
 */

public class DayCalendar extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    CalendarDayView dayView;

    ArrayList<IEvent> events;
    ArrayList<IPopup> popups;
    AccountCalendar ac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_calendar_main);
        Intent intent =this.getIntent();
        Calendar calendar= (Calendar) intent.getExtras().getSerializable("user");
        // int month=test.getTime().getMonth();
        // int day=test.getTime().getDate();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);

        dayView = (CalendarDayView) findViewById(R.id.dayView);
        dayView.setLimitTime(0, 24);
        dayView.setDecorator(new CustomDecoration(getApplicationContext()));
        ((CdvDecorationDefault) (dayView.getDecoration())).setOnEventClickListener(
                new EventView.OnEventClickListener() {
                    @Override
                    public void onEventClick(EventView view, IEvent data) {
                        Log.e("TAG", "onEventClick:" + data.getName());
                    }

                    @Override
                    public void onEventViewClick(View view, EventView eventView, IEvent data) {
                        Log.e("TAG", "onEventViewClick:" + data.getName());
                        if (data instanceof Event) {
                            // change event (ex: set event color)
                            dayView.setEvents(events);
                        }
                    }
                });

        ((CdvDecorationDefault) (dayView.getDecoration())).setOnPopupClickListener(
                new PopupView.OnEventPopupClickListener() {
                    @Override
                    public void onPopupClick(PopupView view, IPopup data) {
                        Log.e("TAG", "onPopupClick:" + data.getTitle());
                    }

                    @Override
                    public void onQuoteClick(PopupView view, IPopup data) {
                        Log.e("TAG", "onQuoteClick:" + data.getTitle());
                    }

                    @Override
                    public void onLoadData(PopupView view, ImageView start, ImageView end,
                                           IPopup data) {
                        start.setImageResource(R.drawable.little5);
                        end.setImageResource(R.drawable.little5);
                        // view.setBackgroundColor(Color.argb(255,255,0,0));

                    }
                });


        events = new ArrayList<>();

        WeatherApi weatherApi= new WeatherApiCreater().creat();
        // TODO 天氣查詢要符合時間
        // TODO 能夠新增 刪除 修改 事件
        Call<List<Rain>> call = weatherApi.getRainList(20171221,"臺北市");
        // 异步请求
        call.enqueue(new Callback<List<Rain>>() {
            @Override
            public void onResponse(Call<List<Rain>> call, Response<List<Rain>> response) {
                // 处理返回数据
                if (response.isSuccessful()) {
                    Log.i("[WeatherApi]", "get Response");
                    // Toast.makeText(DayCalendar.this,"Success",Toast.LENGTH_LONG).show();

                    if(response.body().size() != 0)
                        drawWeather(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Rain>> call, Throwable t) {
                Log.e("Error", "onFailure: 请求数据失败");
                Toast.makeText(DayCalendar.this,"Fail",Toast.LENGTH_LONG).show();;
            }


        });

        // {
        //     int eventColor = ContextCompat.getColor(this, R.color.eventColor);
        //     Calendar timeStart = Calendar.getInstance();
        //     timeStart.set(Calendar.HOUR_OF_DAY, 11);
        //     timeStart.set(Calendar.MINUTE, 0);
        //     Calendar timeEnd = (Calendar) timeStart.clone();
        //     timeEnd.set(Calendar.HOUR_OF_DAY, 15);
        //     timeEnd.set(Calendar.MINUTE, 30);
        //     Event event = new Event(1, timeStart, timeEnd, "Event", "Hockaido", eventColor);
        //     event.setLocation("AAA");
        //     events.add(event);
        // }
        //
        // {
        //     int eventColor = ContextCompat.getColor(this, R.color.eventColor1);
        //     Calendar timeStart = Calendar.getInstance();
        //     timeStart.set(Calendar.HOUR_OF_DAY, 18);
        //     timeStart.set(Calendar.MINUTE, 0);
        //     Calendar timeEnd = (Calendar) timeStart.clone();
        //     timeEnd.set(Calendar.HOUR_OF_DAY, 20);
        //     timeEnd.set(Calendar.MINUTE, 30);
        //     Event event = new Event(1, timeStart, timeEnd, "Another event", "Hockaido", eventColor);
        //
        //     events.add(event);
        // }

        ac = new AccountCalendar(getContentResolver(),getResources().getString(R.string.targetAccount));
        ac.updateCalendars();
        ArrayList<Events> eventList = new ArrayList<>();



        assert calendar != null;
        Calendar beginTime = (Calendar) calendar.clone();
        // Calendar beginTime = Calendar.getInstance();
        beginTime.set(Calendar.HOUR_OF_DAY, 0);
        beginTime.set(Calendar.MINUTE,0);
        beginTime.set(Calendar.SECOND,0);
        beginTime.get(Calendar.SECOND);

        Calendar endTime = (Calendar) calendar.clone();
        // Calendar endTime = Calendar.getInstance();
        endTime.set(Calendar.HOUR_OF_DAY, 23);
        endTime.set(Calendar.MINUTE,59);
        endTime.set(Calendar.SECOND,59);
        endTime.get(Calendar.SECOND);
        for(int i = 0; i < ac.getAccountNameList().size(); ++i)
        {
            Log.i("Search Name",ac.getAccountNameList().get(i));
            eventList.addAll(ac.queryEvents(ac.getAccountNameList().get(i),beginTime,endTime));
        }
        popups = new ArrayList<>();





        for(Events eventItem:eventList)
        {
            Popup popup = new Popup();
            popup.setStartTime(eventItem.getBegin());
            popup.setEndTime(eventItem.getEnd());
            // String email = "sappy5678@gmail.com";
            String email = eventItem.getOrganizer();
            String hash = MD5Util.md5Hex(email);
            popup.setImageStart("https://secure.gravatar.com/avatar/"+hash+"?size=200");
            popup.setTitle(eventItem.getTitle());
            popup.setDescription(eventItem.getDescription());
            popups.add(popup);
        }
        // {
        //     Calendar timeStart = Calendar.getInstance();
        //     timeStart.set(Calendar.HOUR_OF_DAY, 12);
        //     timeStart.set(Calendar.MINUTE, 0);
        //     Calendar timeEnd = (Calendar) timeStart.clone();
        //     timeEnd.set(Calendar.HOUR_OF_DAY, 14);
        //     timeEnd.set(Calendar.MINUTE, 0);
        //
        //     Popup popup = new Popup();
        //     popup.setStartTime(timeStart);
        //     popup.setEndTime(timeEnd);
        //     popup.setImageStart("https://i.imgur.com/WRI3U4V.png");
        //     popup.setTitle("event 1 with title");
        //     popup.setDescription("Yuong alsdf");
        //     popups.add(popup);
        // }
        //
        // {
        //     Calendar timeStart = Calendar.getInstance();
        //     timeStart.set(Calendar.HOUR_OF_DAY, 20);
        //     timeStart.set(Calendar.MINUTE, 0);
        //     Calendar timeEnd = (Calendar) timeStart.clone();
        //     timeEnd.set(Calendar.HOUR_OF_DAY, 22);
        //     timeEnd.set(Calendar.MINUTE, 0);
        //
        //     Popup popup = new Popup();
        //     popup.setStartTime(timeStart);
        //     popup.setEndTime(timeEnd);
        //     popup.setImageStart("http://sample.com/image.png");
        //     popup.setTitle("event 2 with title");
        //     popup.setDescription("Yuong alsdf");
        //
        //
        //     popups.add(popup);
        // }

        // dayView.setEvents(events);
        dayView.setPopups(popups);
    }

    public void drawWeather(List<Rain> rainList)
    {
        for(Rain rain:rainList)
        {
            if(Integer.valueOf(rain.getValue()) <= 30)
            {
                continue;
            }
            int eventColor = Color.argb(100,0,0,255);
            // int eventColor = ContextCompat.getColor(this, Color.argb());
            // Calendar timeStart = Calendar.getInstance();
            // timeStart.set(Calendar.HOUR_OF_DAY, 11);
            // timeStart.set(Calendar.MINUTE, 0);
            // Calendar timeEnd = (Calendar) timeStart.clone();
            // timeEnd.set(Calendar.HOUR_OF_DAY, 15);
            // timeEnd.set(Calendar.MINUTE, 30);



            Calendar timeStart = rain.getStarttime();
            Calendar timeEnd = rain.getEndtime();
            Calendar beginTime = Calendar.getInstance();
            // Calendar beginTime = Calendar.getInstance();
            beginTime.set(Calendar.HOUR_OF_DAY, 0);
            beginTime.set(Calendar.MINUTE,0);
            beginTime.set(Calendar.SECOND,0);
            beginTime.getTime();

            Calendar endTime = Calendar.getInstance();
            // Calendar endTime = Calendar.getInstance();
            endTime.set(Calendar.HOUR_OF_DAY, 23);
            endTime.set(Calendar.MINUTE,59);
            endTime.set(Calendar.SECOND,59);
            endTime.getTime();

            // 設定在時間範圍內
            Log.i("Time  Compare",String.valueOf(timeEnd.after(endTime)));
            Log.i("End Time",endTime.getTime().toString());
            Log.i("End Time",timeEnd.getTime().toString());
            if(timeStart.before(beginTime))
            {
                timeStart = (Calendar) beginTime.clone();
            }
            if(timeEnd.after(endTime))
            {
                timeEnd = (Calendar) endTime.clone();
            }

            Event event = new Event(1, timeStart, timeEnd, "Event", "Hockaido", eventColor);
            event.setLocation("AAA");

            events.add(event);
        }
        dayView.setEvents(events);
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
    /**
     * 当有多个权限需要申请的时候
     * 这里以打电话和SD卡读写权限为例
     */
    private void requestPermissions(){

        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_CALENDAR);
        }

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_CALENDAR);
        }

        if (!permissionList.isEmpty()){  //申请的集合不为空时，表示有需要申请的权限
            ActivityCompat.requestPermissions(this,permissionList.toArray(new String[permissionList.size()]),1);
        }else { //所有的权限都已经授权过了

        }
    }
    /**
     * 权限申请返回结果
     * @param requestCode 请求码
     * @param permissions 权限数组
     * @param grantResults  申请结果数组，里面都是int类型的数
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0){ //安全写法，如果小于0，肯定会出错了
                    for (int i = 0; i < grantResults.length; i++) {

                        int grantResult = grantResults[i];
                        if (grantResult == PackageManager.PERMISSION_DENIED){ //这个是权限拒绝
                            String s = permissions[i];
                            Toast.makeText(this,s+"权限被拒绝了",Toast.LENGTH_SHORT).show();
                        }else{ //授权成功了
                            //do Something
                        }
                    }
                }
                break;
            default:
                break;
        }
    }
}

