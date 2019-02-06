package io.prepod.itsweatherapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.prepod.itsweatherapp.containers.Weather;
import io.prepod.itsweatherapp.containers.WeatherByName;

class WeatherCache {

    public LiveData<WeatherByName> get(String cityName){
        return null;
    }

    public void put(String cityName, MutableLiveData<WeatherByName> data) {
    }
}
