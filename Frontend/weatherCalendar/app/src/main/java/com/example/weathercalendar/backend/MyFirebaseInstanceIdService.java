package com.example.weathercalendar.backend;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sappy5678 on 12/22/17.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e("[Token]", "Refreshed token: " + refreshedToken);
        sendToken();
        // new WelcomeActivity().sendCalendars();

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        // sendRegistrationToServer(refreshedToken);
    }

    public void sendToken() {
        String s = FirebaseInstanceId.getInstance().getToken();

        // 確保一定能拿到資料
        while (s == null) {
            s = FirebaseInstanceId.getInstance().getToken();
        }

        // 傳送資料
        WeatherApi weatherApi = new WeatherApiCreater().create();
        Call<String> call = weatherApi.sendToken(s);
        // 异步请求
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                // 处理返回数据
                if (response.isSuccessful()) {
                    Log.i("[WeatherApi]", "get Response");
                    // Toast.makeText(WelcomeActivity.this,"Success Login",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Error", "onFailure: 请求数据失败");
                // Toast.makeText(WelcomeActivity.this,"Fail Login",Toast.LENGTH_LONG).show();;
            }


        });
    }
}

