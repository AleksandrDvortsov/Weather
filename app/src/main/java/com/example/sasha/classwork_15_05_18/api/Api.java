package com.example.sasha.classwork_15_05_18.api;

import com.example.sasha.classwork_15_05_18.MainActivity;
import com.example.sasha.classwork_15_05_18.MyPoja;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api
{
    @GET("weather")
    Call<MyPoja> getData(@Query("q") String cityName,
                         @Query("units") String units,
                         @Query("appid") String appid);
}
