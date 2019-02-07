package io.prepod.itsweatherapp.views;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import dagger.MapKey;
import io.prepod.itsweatherapp.ItsWeatherApp;
import io.prepod.itsweatherapp.WeatherRepository;
import io.prepod.itsweatherapp.containers.WeatherByName;

public class WeatherViewModel extends ViewModel {

    @Documented
    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @MapKey
    public @interface ViewModelKey {
        Class<? extends ViewModel> value();
    }

    private LiveData<WeatherByName> weatherByName;

    private WeatherRepository weatherRepository;

/*    public WeatherViewModel(){
        ItsWeatherApp.getAppComponent().inject(this);
    }*/

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
