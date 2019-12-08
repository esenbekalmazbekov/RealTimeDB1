package com.example.weathermap.Controller;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.weathermap.Models.Convert;
import com.example.weathermap.Models.getfromapi.CityWeather;
import com.example.weathermap.Models.getfromapi.DailyForecast;
import com.example.weathermap.R;

import java.util.ArrayList;

public class DataShow extends AppCompatActivity {
    private TextView nameofCity;
    private ArrayList<TextView> dates = new ArrayList<>();
    private ArrayList<TextView> tempsMax = new ArrayList<>();
    private ArrayList<TextView> tempsMin = new ArrayList<>();
    private ArrayList<TextView> typeDays = new ArrayList<>();
    private ArrayList<TextView> intensityDays = new ArrayList<>();
    private ArrayList<TextView> typeNights = new ArrayList<>();
    private ArrayList<TextView> intensityNights = new ArrayList<>();
    private CityWeather cityWeather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_show);
        connectIds();
        replaceDatas();
    }

    private void pasteDatas() throws NullPointerException{
        String context;
        int i = 0;
        for(DailyForecast dailyForecast : cityWeather.getDailyForecasts()){
            if(i > 1)
                dates.get(i).setText(dailyForecast.getDate());

            context = "Temp max: " + Convert.convert(dailyForecast.getTemperature().getMaximum().getValue()).toString() + " C";
            tempsMax.get(i).setText(context);

            context = "Temp min: " + Convert.convert(dailyForecast.getTemperature().getMinimum().getValue()).toString() + " C";
            tempsMin.get(i).setText(context);

            context = "precipitationType: " + dailyForecast.getDay().getPrecipitationType();
            typeDays.get(i).setText(context);

            context = "precipitationIntensity: " + dailyForecast.getDay().getPrecipitationIntensity();
            intensityDays.get(i).setText(context);

            context = "precipitationType: " + dailyForecast.getNight().getPrecipitationType();
            typeNights.get(i).setText(context);

            context = "precipitationIntensity: " + dailyForecast.getNight().getPrecipitationIntensity();
            intensityNights.get(i).setText(context);
            i++;
        }
    }
    private void replaceDatas(){
        try {
            Intent intent = getIntent();
            String name = intent.getStringExtra("CITY_NAME");
            nameofCity = findViewById(R.id.nameofCity);
            nameofCity.setText(name);
            cityWeather = intent.getParcelableExtra("CITY_DATA");
            pasteDatas();
        }catch (NullPointerException e){
            nameofCity.setText("Server problem!!!");
        }
    }
    private void connectIds(){
        //dates
        dates.add(0,(TextView) findViewById(R.id.day1));
        dates.add(1,(TextView) findViewById(R.id.day2));
        dates.add(2,(TextView) findViewById(R.id.day3));
        dates.add(3,(TextView) findViewById(R.id.day4));
        dates.add(4,(TextView) findViewById(R.id.day5));
        //TempMax
        tempsMax.add(0,(TextView)findViewById(R.id.TempMax1));
        tempsMax.add(1,(TextView)findViewById(R.id.TempMax2));
        tempsMax.add(2,(TextView)findViewById(R.id.TempMax3));
        tempsMax.add(3,(TextView)findViewById(R.id.TempMax4));
        tempsMax.add(4,(TextView)findViewById(R.id.TempMax5));
        //tempMin
        tempsMin.add(0,(TextView)findViewById(R.id.TempMin1));
        tempsMin.add(1,(TextView)findViewById(R.id.TempMin2));
        tempsMin.add(2,(TextView)findViewById(R.id.TempMin3));
        tempsMin.add(3,(TextView)findViewById(R.id.TempMin4));
        tempsMin.add(4,(TextView)findViewById(R.id.TempMin5));
        //typeDay
        typeDays.add(0,(TextView)findViewById(R.id.typeDay1));
        typeDays.add(1,(TextView)findViewById(R.id.typeDay2));
        typeDays.add(2,(TextView)findViewById(R.id.typeDay3));
        typeDays.add(3,(TextView)findViewById(R.id.typeDay4));
        typeDays.add(4,(TextView)findViewById(R.id.typeDay5));
        //intensityDay
        intensityDays.add(0,(TextView)findViewById(R.id.intensityDay1));
        intensityDays.add(1,(TextView)findViewById(R.id.intensityDay2));
        intensityDays.add(2,(TextView)findViewById(R.id.intensityDay3));
        intensityDays.add(3,(TextView)findViewById(R.id.intensityDay4));
        intensityDays.add(4,(TextView)findViewById(R.id.intensityDay5));
        //typeNights
        typeNights.add(0,(TextView)findViewById(R.id.typeNight1));
        typeNights.add(1,(TextView)findViewById(R.id.typeNight2));
        typeNights.add(2,(TextView)findViewById(R.id.typeNight3));
        typeNights.add(3,(TextView)findViewById(R.id.typeNight4));
        typeNights.add(4,(TextView)findViewById(R.id.typeNight5));
        //intensityNight
        intensityNights.add(0,(TextView)findViewById(R.id.intensityNight1));
        intensityNights.add(1,(TextView)findViewById(R.id.intensityNight2));
        intensityNights.add(2,(TextView)findViewById(R.id.intensityNight3));
        intensityNights.add(3,(TextView)findViewById(R.id.intensityNight4));
        intensityNights.add(4,(TextView)findViewById(R.id.intensityNight5));
    }
}
