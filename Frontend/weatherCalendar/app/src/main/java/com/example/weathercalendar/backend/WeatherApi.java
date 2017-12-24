package com.example.weathercalendar.backend;

import com.example.weathercalendar.backend.pojo.Rain;
import com.example.weathercalendar.calendar.pojo.EventData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by sappy5678 on 12/14/17.
 */

public interface WeatherApi {
    String BaseUrl = "http://140.138.224.111:5000/";
    @GET("api/v1/rain/{date}/{location}")
    Call<String> getRainString(@Path("date") int date,@Path("location") String location);

    @GET("api/v1/rain/{date}/{location}")
    Call<List<Rain>> getRainList(@Path("date") int date,@Path("location") String location);

    @GET("api/v1/rain/{date}/{location}")
    Call<String> getRainString(@Path("date") String date,@Path("location") String location);

    @GET("api/v1/rain/{date}/{location}")
    Call<List<Rain>> getRainList(@Path("date") String date,@Path("location") String location);


    @GET("api/v1/temperature/{date}/{location}")
    Call<String> getTemperatureString(@Path("date") int date,@Path("location") String location);

    @GET("api/v1/temperature/{date}/{location}")
    Call<List<Rain>> getTemperatureList(@Path("date") int date,@Path("location") String location);

    @GET("api/v1/temperature/{date}/{location}")
    Call<String> getTemperatureString(@Path("date") String date,@Path("location") String location);

    @GET("api/v1/temperature/{date}/{location}")
    Call<List<Rain>> getTemperatureList(@Path("date") String date,@Path("location") String location);


    @GET("api/v1/register/{token}")
    Call<String> sendToken(@Path("token") String token);

    @POST("api/v1/calendars/{token}")
    Call<String> sendCalendars(@Path("token") String token, @Body ArrayList<EventData> events);
}

