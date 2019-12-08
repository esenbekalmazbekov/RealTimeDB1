package com.example.weathermap.Models;

import com.example.weathermap.Models.getfromapi.CityWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WeatherApi {
    String DOMAIN = "https://dataservice.accuweather.com/";

    @GET( "forecasts/v1/daily/5day/{cityKey}?apikey=in5iNkbzCVLoyJBsWEsHAxDCBXOc2B2R")
    Call<CityWeather> getData(@Path("cityKey") String cityKey);
}