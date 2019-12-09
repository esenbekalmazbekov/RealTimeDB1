package com.example.weathermap.Controller;

import android.content.Intent;
import android.view.View;

import com.example.weathermap.Models.getfromapi.CityWeather;

public class ShowWeatherForFiveDayListener implements View.OnClickListener {
    private MainActivity mainActivity;
    private String name;
    private CityWeather cityWeather;
    ShowWeatherForFiveDayListener(MainActivity mainActivity, String name, CityWeather cityWeather) {
        this.mainActivity = mainActivity;
        this.name = name;
        this.cityWeather = cityWeather;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(mainActivity,DataShow.class);
        intent.putExtra("CITY_NAME",name);
        intent.putExtra("CITY_DATA",cityWeather);
        mainActivity.startActivity(intent);
    }
}
