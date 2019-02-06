package io.prepod.itsweatherapp.di;

import dagger.Module;
import dagger.Provides;
import dagger.Reusable;
import io.prepod.itsweatherapp.WeatherRepository;

@Module
public class RepositoryModule {

    @Provides
    @Reusable
    public WeatherRepository provideWeatherRepository(){
        return new WeatherRepository();
    }
}
