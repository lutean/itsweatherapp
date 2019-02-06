package io.prepod.itsweatherapp;

import io.prepod.itsweatherapp.containers.WeatherByName;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WebApi {

    @GET("data/2.5/weather")
    Call<WeatherByName> getWeatherByName(@Query("q") String cityName);

}