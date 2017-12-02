package com.example.sappy5678.weather_calendar.Backend;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by sappy5678 on 12/2/17.
 */

public interface WeatherApi {
    String BaseUrl = "http://opendata.cwb.gov.tw/";
    @GET("govdownload?dataid=F-D0047-091&authorizationkey=rdec-key-123-45678-011121314")
    Call<String> getData();

}
