package com.example.weathercalendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ///////////////////// Test Restful Api ////////////////////////
        // Retrofit retrofit = new Retrofit.Builder()
        //         .baseUrl(WeatherApi.BaseUrl)
        //         // 添加String支持
        //         .addConverterFactory(ScalarsConverterFactory.create())
        //         .addConverterFactory(GsonConverterFactory.create())
        //         .build();
        // WeatherApi service = retrofit.create(WeatherApi.class);
        // Call<List<Rain>> call = service.getRainList(20171215,"臺北市");
        // // 异步请求
        // call.enqueue(new Callback<List<Rain>>() {
        //     @Override
        //     public void onResponse(Call<List<Rain>> call, Response<List<Rain>> response) {
        //         // 处理返回数据
        //         if (response.isSuccessful()) {
        //             Log.e("Error", "onResponse: " + response.body().get(0).getLocation());
        //         }
        //     }
        //
        //     @Override
        //     public void onFailure(Call<List<Rain>> call, Throwable t) {
        //         Log.e("Error", "onFailure: 请求数据失败");
        //     }
        //
        //
        // });
        ////////////////////////////////////////////////////////

        ///////////////// Test Calendar Provider Api ///////////////

        // 行事曆
        // AccountCalendar account = new AccountCalendar(getContentResolver(),"sappy5678@gmail.com");
        // // account.updateCalendars();
        // Calendar beginTime = Calendar.getInstance();
        // beginTime.set(2017, 0, 1, 8, 0);
        // Calendar endTime = Calendar.getInstance();
        // endTime.set(2018, 4, 1, 8, 0);
        // account.queryEvents("ITAC",beginTime,endTime);


        ////////////////////////////////////////////////////////////
    }
}
