package com.example.weathermap.Controller;

import android.content.Intent;
import android.support.v4.app.INotificationSideChannel;
import android.view.View;
import android.widget.Toast;

import com.example.weathermap.Models.Convert;
import com.example.weathermap.Models.WeatherApi;
import com.example.weathermap.Models.getfromapi.CityWeather;
import com.example.weathermap.Models.getlocationkey.CityData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowFromMapListener implements View.OnClickListener {
    private int lat,lon;
    private MapActivity activity;
    private String locationKey;

    public ShowFromMapListener(int lat, int lon,MapActivity mapActivity) {
        this.lat = lat;
        this.lon = lon;
        this.activity = mapActivity;
    }

    @Override
    public void onClick(View v) {
        getKey();
    }

    private void getKey(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WeatherApi.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherApi getApi = retrofit.create(WeatherApi.class);

        Call<CityData> call = getApi.getLocationKey("" + lat +"," + lon);

        call.enqueue(new Callback<CityData>() {
            @Override
            public void onResponse(Call<CityData> call, Response<CityData> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(activity, "Code: " + response.code(), Toast.LENGTH_LONG).show();
                }
                try{
                    CityData cityData = response.body();
                    if(cityData == null){
                        Toast.makeText(activity,"no w0rk",Toast.LENGTH_LONG).show();
                    }else {
                        getData(cityData);
                    }
                }catch (NullPointerException e){
                    Toast.makeText(activity, "connect err", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CityData> call, Throwable t) {
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }
    private void getData(final CityData data){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WeatherApi.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherApi getApi = retrofit.create(WeatherApi.class);

        Call<CityWeather> call = getApi.getData(data.getKey());

        call.enqueue(new Callback<CityWeather>() {
            @Override
            public void onResponse(Call<CityWeather> call, Response<CityWeather> response) {
                if (!response.isSuccessful())
                    Toast.makeText(activity, "Code: " + response.code(), Toast.LENGTH_LONG).show();
                try{
                    CityWeather cityWeather = response.body();
                    if(cityWeather == null){
                        Toast.makeText(activity,"no w0rk",Toast.LENGTH_LONG).show();
                    }else {
                        Intent intent = new Intent(activity,DataShow.class);
                        intent.putExtra("CITY_NAME",data.getTimeZone().getName());
                        intent.putExtra("CITY_DATA",cityWeather);
                        activity.startActivity(intent);
                    }
                }catch (NullPointerException e){
                    Toast.makeText(activity, "connect err", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CityWeather> call, Throwable t) {
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
