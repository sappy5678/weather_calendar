package com.example.weathercalendar.Backend;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by sappy5678 on 12/19/17.
 */

public class WeatherApiCreater {

    public WeatherApi creat()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WeatherApi.BaseUrl)
                // 添加String支持
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(WeatherApi.class);
    }
}
