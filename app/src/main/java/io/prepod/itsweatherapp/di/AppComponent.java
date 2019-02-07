package io.prepod.itsweatherapp.di;

import javax.inject.Singleton;

import dagger.Component;
import io.prepod.itsweatherapp.views.MainActivity;
import io.prepod.itsweatherapp.data.WeatherRepository;
import io.prepod.itsweatherapp.views.weather.WeatherFragment;
import io.prepod.itsweatherapp.views.weather.WeatherViewModel;

@Singleton
@Component(modules = {WebModule.class,
        RepositoryModule.class,
        AppModule.class,
        ThreadModule.class,
        ViewModelModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);
    void inject(WeatherRepository weatherRepository);
    void inject(WeatherViewModel weatherViewModel);
    void inject(WeatherFragment weatherFragment);

}