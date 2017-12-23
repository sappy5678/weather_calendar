package com.example.weathercalendar.backend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by sappy5678 on 12/19/17.
 */

public class WeatherApiCreater {

    public WeatherApi create()
    {
        // 建立 gson parser 去 parser calendar
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WeatherApi.BaseUrl)
                // 添加String支持
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(WeatherApi.class);
    }
}
