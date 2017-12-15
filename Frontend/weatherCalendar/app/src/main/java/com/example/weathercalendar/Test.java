package com.example.weathercalendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.weathercalendar.Backend.WeatherApi;
import com.example.weathercalendar.Backend.pojo.Rain;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ///////////////////// Test Section ////////////////////////
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WeatherApi.BaseUrl)
                // 添加String支持
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherApi service = retrofit.create(WeatherApi.class);
        Call<List<Rain>> call = service.getRainList(20171215,"臺北市");
        // 异步请求
        call.enqueue(new Callback<List<Rain>>() {
            @Override
            public void onResponse(Call<List<Rain>> call, Response<List<Rain>> response) {
                // 处理返回数据
                if (response.isSuccessful()) {
                    Log.e("Error", "onResponse: " + response.body().get(0).getLocation());
                }
            }

            @Override
            public void onFailure(Call<List<Rain>> call, Throwable t) {
                Log.e("Error", "onFailure: 请求数据失败");
            }


        });




        ////////////////////////////////////////////////////////
    }
}