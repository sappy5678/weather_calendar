package com.example.weathercalendar.calendar;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;


import com.example.weathercalendar.calendar.pojo.Events;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by sappy5678 on 12/17/17.
 */

// 單帳號管理
public class AccountCalendar {

    private ArrayList<String> accountNameList;
    private ArrayList<Integer> calendarIdList;
    private ContentResolver cr;
    private String targetAccount;

    /**
     * 建立
     * @param cr 在 activity 中使用 getContentResolver() 傳入，獲得手機上的公開資料庫
     * @param targetAccount 要查詢的帳號
     */

    public AccountCalendar(ContentResolver cr, String targetAccount)
    {
        this.cr = cr;
        this.targetAccount = targetAccount;
        updateCalendars();

    }

    /**
     * 查詢並更新行事曆
     */
    @SuppressLint("MissingPermission")
    public ArrayList<String> updateCalendars()
    {
        // 建立List來暫存查詢的結果
        ArrayList<String> accountNameList = new ArrayList<>();
        ArrayList<Integer> calendarIdList = new ArrayList<>();

        // // 設定要返回的資料
        // String[] EVENT_PROJECTION = new String[]{
        //         CalendarContract.Calendars._ID,                             // 0 日歷ID
        //         CalendarContract.Calendars.ACCOUNT_NAME,                // 1 日歷所屬的帳戶名稱
        //         CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,       // 2 日歷名稱
        //         CalendarContract.Calendars.OWNER_ACCOUNT,                  // 3 日歷擁有者
        //         CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL,       // 4 對此日歷所擁有的權限
        // };
        // 定義查詢條件，找出屬於上面Google帳戶及可以完全控制的日歷
        // String selection = "((" + CalendarContract.Calendars.ACCOUNT_NAME + " = ?) AND ("
        //         + CalendarContract.Calendars.ACCOUNT_TYPE + " = ?) AND ("
        //         + CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL + " = ?))";
        // 定義查詢條件，找出屬於上面Google帳戶及可以完全控制的日歷
        // String[] selectionArgs = new String[]{targetAccount,
        //         "com.google",
        //         Integer.toString(CalendarContract.Calendars.CAL_ACCESS_OWNER)};

        // 設定要返回的資料
        String[] EVENT_PROJECTION = new String[]{
                CalendarContract.Calendars._ID,                             // 0 日歷ID
                CalendarContract.Calendars.ACCOUNT_NAME,                // 1 日歷所屬的帳戶名稱
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,       // 2 日歷名稱
                CalendarContract.Calendars.OWNER_ACCOUNT,                  // 3 日歷擁有者
                // CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL,       // 4 對此日歷所擁有的權限
        };

        int PROJECTION_ID_INDEX = 0;
        int PROJECTION_ACCOUNT_NAME_INDEX = 1;
        int PROJECTION_DISPLAY_NAME_INDEX = 2;
        int PROJECTION_OWNER_ACCOUNT_INDEX = 3;
        int PROJECTION_CALENDAR_ACCESS_LEVEL = 4;


        String selection = "((" + CalendarContract.Calendars.ACCOUNT_NAME + " = ?) AND ("
                + CalendarContract.Calendars.ACCOUNT_TYPE + " = ?) )";

        String[] selectionArgs = new String[]{targetAccount,
                "com.google"};


        // 查詢日歷
        Cursor cur;
        // ContentResolver cr = getContentResolver();
        Uri uri = CalendarContract.Calendars.CONTENT_URI;


        // 開始查詢
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
                // accessLevel = cur.getInt(PROJECTION_CALENDAR_ACCESS_LEVEL);
                ContentValues a;
                Log.i("query_calendar", String.format("calendarId=%s", calendarId));
                Log.i("query_calendar", String.format("accountName=%s", accountName));
                Log.i("query_calendar", String.format("displayName=%s", displayName));
                Log.i("query_calendar", String.format("ownerAccount=%s", ownerAccount));
                // Log.i("query_calendar", String.format("accessLevel=%s", accessLevel));
                // 暫存資料讓使用者選擇
                accountNameList.add(displayName);
                calendarIdList.add((int) calendarId);
            }
            cur.close();
        }
        this.accountNameList = accountNameList;
        this.calendarIdList = calendarIdList;

        return accountNameList;
    }


    @SuppressLint("MissingPermission")
    public ArrayList<Events> queryEvents(String targetCalendar,Calendar beginTime,Calendar endTime)
    {
        // 設定要返回的資料
        String[] INSTANCE_PROJECTION = new String[]{
                CalendarContract.Instances.EVENT_ID,      // 0 活動ID
                CalendarContract.Instances.BEGIN,         // 1 活動開始日期時間
                CalendarContract.Instances.END,           // 2 活動結束日期時間
                CalendarContract.Instances.TITLE,         // 3 活動標題
                CalendarContract.Instances.DESCRIPTION,   // 4 活動內容
                CalendarContract.Instances.ORGANIZER,     // 5 主辦人（單位）的email

        };
        // 根據上面的設定，定義各資料的索引，提高代碼的可讀性
        int PROJECTION_ID_INDEX = 0;
        int PROJECTION_BEGIN_INDEX = 1;
        int PROJECTION_END_INDEX = 2;
        int PROJECTION_TITLE_INDEX = 3;
        int PROJECTION_DESCRIPTION_INDEX = 4;
        int PROJECTION_ORGANIZER_INDEX = 5;
        // 取得在EditText的日歷ID
        // String targetCalendar = ((EditText) findViewById(R.id.calendar_id)).getText().toString();
        // 指定一個時間段，查詢以下時間內的所有活動
        // 月份是從0開始，0-11
        // Calendar beginTime = Calendar.getInstance();
        // beginTime.set(2017, 0, 1, 8, 0);
        long startMillis = beginTime.getTimeInMillis();
        // Calendar endTime = Calendar.getInstance();
        // endTime.set(2018, 4, 1, 8, 0);
        long endMillis = endTime.getTimeInMillis();
        // 查詢活動
        Cursor cur = null;
        Uri.Builder builder = CalendarContract.Instances.CONTENT_URI.buildUpon();
        // 定義查詢條件，找出上面日歷中指定時間段的所有活動
        String selection = CalendarContract.Events.CALENDAR_DISPLAY_NAME + " = ?";
        String[] selectionArgs = new String[]{targetCalendar};
        ContentUris.appendId(builder, startMillis);
        ContentUris.appendId(builder, endMillis);

        // 建立List來暫存查詢的結果
        ArrayList<Events> eventList = new ArrayList<>();
        cur = cr.query(builder.build(),
                INSTANCE_PROJECTION,
                selection,
                selectionArgs,
                null);
        if (cur != null) {
            while (cur.moveToNext()) {
                Events event = new Events();
                long eventID = 0;
                long beginVal = 0;
                long endVal = 0;
                String title = null;
                String description = null;
                String organizer = null;
                // 取得所需的資料
                eventID = cur.getLong(PROJECTION_ID_INDEX);
                beginVal = cur.getLong(PROJECTION_BEGIN_INDEX);
                endVal = cur.getLong(PROJECTION_END_INDEX);
                title = cur.getString(PROJECTION_TITLE_INDEX);
                description = cur.getString(PROJECTION_DESCRIPTION_INDEX);
                organizer = cur.getString(PROJECTION_ORGANIZER_INDEX);
                // Log.i("query_event", String.format("eventID=%s", eventID));
                // Log.i("query_event", String.format("beginVal=%s", beginVal));
                // Log.i("query_event", String.format("title=%s", title));
                // 暫存資料讓使用者選擇
                event.setId((int) eventID);
                event.setBegin((long) beginVal);
                event.setEnd((long) endVal);
                event.setTitle(title);
                event.setDescription(description);
                event.setOrganizer(organizer);
                eventList.add(event);

            }
            cur.close();

        }
        return eventList;
    }

    // getter and setter
    public ArrayList<String> claendars() {
        return accountNameList;
    }

    public ArrayList<String> getAccountNameList() {
        return accountNameList;
    }

    public void setAccountNameList(ArrayList<String> accountNameList) {
        this.accountNameList = accountNameList;
    }

    public ArrayList<Integer> getCalendarIdList() {
        return calendarIdList;
    }

    public void setCalendarIdList(ArrayList<Integer> calendarIdList) {
        this.calendarIdList = calendarIdList;
    }

    public ContentResolver getCr() {
        return cr;
    }

    public void setCr(ContentResolver cr) {
        this.cr = cr;
    }

    public String getTargetAccount() {
        return targetAccount;
    }

    public void setTargetAccount(String targetAccount) {
        this.targetAccount = targetAccount;
    }
}
