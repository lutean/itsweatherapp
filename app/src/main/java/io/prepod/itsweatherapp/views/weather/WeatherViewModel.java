package io.prepod.itsweatherapp.views.weather;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import io.prepod.itsweatherapp.data.WeatherRepository;
import io.prepod.itsweatherapp.data.entities.CityWeather;

public class WeatherViewModel extends ViewModel {

    private LiveData<CityWeather> weatherByName;

    private WeatherRepository weatherRepository;

    @Inject
    public WeatherViewModel(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    void getWeatherByName(String cityName){
        if (this.weatherByName != null) return;
        weatherByName = weatherRepository.getWeatherByName(cityName);
    }

    LiveData<CityWeather> getCity() {
        return weatherByName;
    }
}
