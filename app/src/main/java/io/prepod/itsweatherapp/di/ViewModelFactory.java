package io.prepod.itsweatherapp.di;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import io.prepod.itsweatherapp.WeatherRepository;
import io.prepod.itsweatherapp.views.WeatherViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private WeatherRepository weatherRepository;

    @Inject
    public ViewModelFactory(WeatherRepository repository) {
        weatherRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(WeatherViewModel.class))
            return (T) new WeatherViewModel(weatherRepository);
        else {
            throw new IllegalArgumentException("ViewModel Not Found");
        }
    }

}
