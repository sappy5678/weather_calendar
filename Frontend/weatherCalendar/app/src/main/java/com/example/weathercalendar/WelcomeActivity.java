package com.example.weathercalendar;

import android.Manifest;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.weathercalendar.backend.WeatherApi;
import com.example.weathercalendar.backend.WeatherApiCreater;
import com.example.weathercalendar.calendar.AccountCalendar;
import com.example.weathercalendar.calendar.pojo.EventData;
import com.google.android.gms.common.AccountPicker;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.AccountPicker;

public class WelcomeActivity extends Activity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    String targetAccount;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_welcome);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        SharedPreferences sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
        targetAccount = sharedPreferences.getString("USER", "");
        requestPermissions();



        /* New Handler to start the Menu-Activity 
         * and close this Splash-Screen after some seconds.*/
        // new Handler().postDelayed(new Runnable(){
        //     @Override
        //     public void run() {
        //         /* Create an Intent that will start the Menu-Activity. */
        //         requestPermissions();
        //         Intent mainIntent = new Intent(WelcomeActivity.this,MainActivity.class);
        //         WelcomeActivity.this.startActivity(mainIntent);
        //         WelcomeActivity.this.finish();
        //     }
        // }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (targetAccount.equals(""))
            get_account_name();

    }

    public void request_permission(View view) {
        // 要求使用者給予權限
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_CALENDAR,
                        Manifest.permission.WRITE_CALENDAR,Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }


    /**
     * 当有多个权限需要申请的时候
     * 这里以打电话和SD卡读写权限为例
     */
    private void requestPermissions(){

        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_CALENDAR);
        }

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_CALENDAR);
        }

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (!permissionList.isEmpty()){  //申请的集合不为空时，表示有需要申请的权限
            ActivityCompat.requestPermissions(this,permissionList.toArray(new String[permissionList.size()]),1);
        }else { //所有的权限都已经授权过了
            sendCalendars();
            // get_account_name();
            jump();


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
                    int number = 0;
                    for (int i = 0; i < grantResults.length; i++) {

                        int grantResult = grantResults[i];
                        if (grantResult == PackageManager.PERMISSION_DENIED){ //这个是权限拒绝
                            String s = permissions[i];
                            Toast.makeText(this,"抱歉\n"+s+"是必要權限",Toast.LENGTH_LONG).show();

                                // Thread.sleep(2000);
                                finishAffinity();

                        }else{ //授权成功了
                            //do Something
                            number++;
                        }

                    }

                    // 所有權限都通過了
                    if (number == grantResults.length)
                    {
                        // Intent mainIntent = new Intent(WelcomeActivity.this,MainActivity.class);
                        // WelcomeActivity.this.startActivity(mainIntent);
                        // WelcomeActivity.this.finish();
                    }
                }
                break;
            default:
                break;
        }
    }


    public void sendCalendars() {
        Log.i("[Server]", "Send Calendar");
        Runnable r1 = new Runnable() {

            public void run() {
                int permission = ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);

                // if (permission != PackageManager.PERMISSION_GRANTED) {
                //     //未取得權限，向使用者要求允許權限
                //     return;
                //
                // }


                AccountCalendar ac = new AccountCalendar(getContentResolver(), targetAccount);
                ac.updateCalendars();
                ArrayList<EventData> eventList = new ArrayList<>();

                Calendar beginTime = Calendar.getInstance();
                Calendar endTime = Calendar.getInstance();
                endTime.add(Calendar.DATE, 5);
                @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date currentDatePlusOne = endTime.getTime();
                Log.i("[EndDate]", dateFormat.format(currentDatePlusOne));


                for (int i = 0; i < ac.getAccountNameList().size(); ++i) {
                    Log.i("Search Calendar Name", ac.getAccountNameList().get(i));
                    Log.i("[Begin Date]", dateFormat.format(beginTime.getTime()));
                    Log.i("[End Date]", dateFormat.format(endTime.getTime()));
                    eventList.addAll(ac.queryEvents(ac.getAccountNameList().get(i), beginTime, endTime));
                }


                WeatherApi api = new WeatherApiCreater().create();

                String token = FirebaseInstanceId.getInstance().getToken();
                Call<String> call = api.sendCalendars(token, eventList);
                try {
                    call.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

        };

        Thread t1 = new Thread(r1);

        t1.start();


    }

    static final int GET_ACCOUNT_NAME_REQUEST = 1;  // 自訂的要求代碼

    public void get_account_name() {
        try {
            Intent intent = AccountPicker.newChooseAccountIntent(null, null,
                    new String[]{GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE}, false, null, null, null, null);
            startActivityForResult(intent, GET_ACCOUNT_NAME_REQUEST);  //GET_ACCOUNT_NAME_REQUEST是一個自訂的int, 用作分辨所返回的結果
        } catch (ActivityNotFoundException e) {
            // TODO
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GET_ACCOUNT_NAME_REQUEST && resultCode == RESULT_OK) {
            String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME); // 使用者所選的帳戶名稱
            SharedPreferences pref = getSharedPreferences("USER", MODE_PRIVATE);
            pref.edit()
                    .putString("USER", accountName)
                    .commit();

            targetAccount = accountName;

            Toast.makeText(this, "Success Login", Toast.LENGTH_LONG).show();
            sendCalendars();
            jump();
            // EditText queryAccountResult = (EditText) findViewById(R.id.account);
            // queryAccountResult.setText(accountName);
        }
    }

    public void jump() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                    /* Create an Intent that will start the Menu-Activity. */

                Intent mainIntent = new Intent(WelcomeActivity.this, MainActivity.class);
                WelcomeActivity.this.startActivity(mainIntent);
                WelcomeActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }



}