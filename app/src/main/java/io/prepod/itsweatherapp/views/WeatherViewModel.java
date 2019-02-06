package io.prepod.itsweatherapp.views;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import io.prepod.itsweatherapp.WeatherRepository;
import io.prepod.itsweatherapp.containers.WeatherByName;

public class WeatherViewModel extends ViewModel {

    private LiveData<WeatherByName> weatherByName;

    private WeatherRepository weatherRepository;

    @Inject
    public WeatherViewModel(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public void init(String cityName){
        if (this.weatherByName != null) return;
        weatherByName = weatherRepository.getWeatherByName(cityName);
    }

    public LiveData<WeatherByName> getCity() {
        return weatherByName;
    }
}
