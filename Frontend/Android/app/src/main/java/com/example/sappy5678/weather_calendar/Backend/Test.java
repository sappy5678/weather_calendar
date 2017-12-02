package com.example.sappy5678.weather_calendar.Backend;

import android.util.Log;

import java.io.IOException;

import retrofit2.http.GET;
import retrofit2.http.Url;
import retrofit2.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

/**
 * Created by sappy5678 on 12/2/17.
 */

public class Test {
    public static void main(String[] args) throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WeatherApi.BaseUrl)
                // 添加String支持
                // .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        WeatherApi service = retrofit.create(WeatherApi.class);
        Call<String> call = service.getData();
        String body = call.execute().body();

        // 异步请求
        // call.enqueue(new Callback<String>() {
        //         @Override
        //         public void onResponse(Call<String> call, Response<String> response) {
        //             // 处理返回数据
        //             if (response.isSuccessful()) {
        //                 Log.d("Error", "onResponse: " + response.body());
        //             }
        //         }
        //         @Override
        //         public void onFailure(Call<String> call, Throwable t) {
        //             Log.d("Error", "onFailure: 请求数据失败");
        //         }
        //     });
    }
}
