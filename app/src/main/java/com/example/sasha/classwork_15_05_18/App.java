package com.example.sasha.classwork_15_05_18;

import android.app.Application;

import com.example.sasha.classwork_15_05_18.api.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application
{
    private static Api forecastApi;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        forecastApi = retrofit.create(Api.class);
    }

    public static Api getApi() {
        return forecastApi;
    }
}
