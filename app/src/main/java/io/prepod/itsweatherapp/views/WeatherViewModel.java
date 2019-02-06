package io.prepod.itsweatherapp.views;

import androidx.lifecycle.ViewModel;
import io.prepod.itsweatherapp.containers.City;

public class WeatherViewModel extends ViewModel {

    private String cityName;
    private City city;


    public void init(String cityName){
        this.cityName = cityName;
    }

    public City getCity() {
        return city;
    }
}
