package com.example.sasha.classwork_15_05_18;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.provider.Contacts.SettingsColumns.KEY;

public class MainActivity extends AppCompatActivity
{
    public static final String METRIC = "metric";
    public static final String KEY = "a0b864a6d7a59953f1fc87e4b7faa1a7";
    TextView textCity;
    TextView textTemp;
    TextView textTempPlus;
    TextView textTempMinus;
    TextView textType;
    ImageView idImageview;
//    String strCityName;
    String Url, icon;
    private MyPoja forecast;
    int xz;
    double xzTemp, xzTempMin, xzTempMax;
    Weather[] wth;
//    String str = "https://openweathermap.org/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Погода");

        textCity = findViewById(R.id.textCity);
        textTemp = findViewById(R.id.textTemp);
        textTempPlus = findViewById(R.id.textTempPlus);
        textTempMinus = findViewById(R.id.textTempMinus);
        textType = findViewById(R.id.textType);
        idImageview = findViewById(R.id.idImageview);

        new LoadId().execute("http://api.openweathermap.org/data/2.5/weather?q=Dnipro&appid=a0b864a6d7a59953f1fc87e4b7faa1a7");

        getDataApi();
//        new LoadId().execute("https://openweathermap.org/city/709930");
    }

    private  void getDataApi ()
    {
        App.getApi().getData("Dnipro", METRIC, KEY).enqueue(new Callback<MyPoja>()
        {
            @Override
            public void onResponse(Call<MyPoja> call, Response<MyPoja> response) {
                MyPoja forecast = response.body();
                Log.e("Retrofit data", forecast.toString());
            }

            @Override
            public void onFailure(Call<MyPoja> call, Throwable t) {
                Log.e("Error", t.getMessage());

            }
        });
    }

    private class LoadId extends AsyncTask<String, Void, String>
    {
        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;

        @Override
        protected String doInBackground(String... strings)
        {
            String stringUrl = strings[0];
            String result = "no result";
            String inputLine;
            //Create a URL object holding our url
            try {
                URL myUrl = new URL(stringUrl);
                HttpURLConnection connection =(HttpURLConnection)
                        myUrl.openConnection();
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);

                connection.connect();
                InputStreamReader streamReader = new
                        InputStreamReader(connection.getInputStream());
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();
                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }
                reader.close();
                streamReader.close();
                result = stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
        @Override
        protected void onPostExecute(String str)
        {
//            Log.e("!",str);
            Gson gson = new Gson();
            forecast = gson.fromJson(str, MyPoja.class);
            textCity.setText(forecast.getName());

//            xz = Integer.parseInt(forecast.getId());
//            wth = forecast.getWeather();
//            xzTemp = Double.parseDouble(forecast.getMain().getTemp());
//            xzTempMin = Double.parseDouble(forecast.getMain().getTemp_min());
//            xzTempMax = Double.parseDouble(forecast.getMain().getTemp_max());
//
//            textType.setText(wth[0].getDescription());
//
//
//            icon = wth[0].getIcon();
//            Url = "http://openweathermap.org/img/w/" + icon + ".png";
//            CityName();
        }
    }

    public void CityName()
    {
        int temp,tempMin,tempMax = 0;
        textCity.setText("XZ");
        textTemp.setText("XZ");
//        textType.setText(wth[0].getDescription());

        Picasso.get().load(Url).into(idImageview);

        temp = (int)(xzTemp - 274);
        tempMin = (int)(xzTempMin - 274);
        tempMax = (int)(xzTempMax - 274);
        if ( temp > 0 || tempMin > 0 || tempMax > 0 )
        {
            textTemp.setText("+" + temp+"\u00B0");
            textTempMinus.setText("+" + tempMin + "\u00B0" + "\n" + "min");
            textTempPlus.setText("+" + tempMax + "\u00B0" + "\n" + "max");
        }
        if ( temp < 0 || tempMin < 0 || tempMax < 0 )
        {
            textTemp.setText("-" + temp+"\u00B0");
            textTempMinus.setText("-" + tempMin + "\u00B0");
            textTempPlus.setText("-" + tempMax + "\u00B0");
        }
//        textCity.setText(forecast.getName());

//        if ( xz == 709930)
//            textCity.setText("Днепр");
//        if ( xz == 706483 )
//            textCity.setText("Харьков");
//        if ( xz == 702550 )
//            textCity.setText("Львов");
//        if ( xz == 292223 )
//            textCity.setText("Дубаи");
//        if ( xz == 518557 )
//            textCity.setText("Новик");


        if ( textType.getText().toString().equals("broken clouds") )
        {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "AAAAAAAAAAAAAAAAAAA!",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            textType.setText("777");
        }
//        Log.e(type, type);
//        if ( textType.getText().length() == 9 )
//        {
//            textType.setText("Cолнечно");
//            Toast toast = Toast.makeText(getApplicationContext(),
//                    "Пора покормить кота!",
//                    Toast.LENGTH_SHORT);
//            toast.setGravity(Gravity.CENTER, 0, 0);
//            toast.show();
//        }
//        if ( textType.getText().length() == 13 )
//        {
//            textType.setText("Облачно");
//        }
//        if ( textType.getText().length() == 27 )
//        {
//            textType.setText("Дождь");
//        }
//        if ( textType.getText().length() == 10 )
//        {
//            textType.setText("Легкий дождь");
//        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_m, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.cityDnepr:
                cityDnepr();
                break;
            case R.id.cityKharkiv:
                cityKharkiv();
                break;
            case R.id.cityLviv:
                cityLviv();
                break;
            case R.id.cityDubai:
                cityDubai();
                break;
            case R.id.cityNovik:
                cityNovik();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void cityDnepr()
    {
        new LoadId().execute("http://api.openweathermap.org/data/2.5/weather?q=Dnipro&appid=a0b864a6d7a59953f1fc87e4b7faa1a7");
    }
    public void cityKharkiv()
    {
        new LoadId().execute("http://api.openweathermap.org/data/2.5/weather?q=Kharkiv&appid=a0b864a6d7a59953f1fc87e4b7faa1a7");
    }
    public void cityLviv()
    {
        new LoadId().execute("http://api.openweathermap.org/data/2.5/weather?q=Lviv&appid=a0b864a6d7a59953f1fc87e4b7faa1a7");
    }
    public void cityDubai()
    {
        new LoadId().execute("http://api.openweathermap.org/data/2.5/weather?q=Dubai&appid=a0b864a6d7a59953f1fc87e4b7faa1a7");
    }
    public void cityNovik()
    {
        new LoadId().execute("http://api.openweathermap.org/data/2.5/weather?q=Novomoskovsk&appid=a0b864a6d7a59953f1fc87e4b7faa1a7");
    }
}
