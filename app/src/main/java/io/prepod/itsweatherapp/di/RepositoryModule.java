package io.prepod.itsweatherapp.di;

import android.app.Application;

import java.util.concurrent.Executor;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import io.prepod.itsweatherapp.DispatchThread;
import io.prepod.itsweatherapp.WeatherRepository;
import io.prepod.itsweatherapp.WebApi;
import io.prepod.itsweatherapp.db.WeatherDao;
import io.prepod.itsweatherapp.db.WeatherDatabase;

@Module
public class RepositoryModule {

    private WeatherDatabase weatherDatabase;

    @Provides
    @Singleton
    public WeatherRepository provideWeatherRepository(WebApi webApi, WeatherDao weatherDao, DispatchThread dispatchThread){
        return new WeatherRepository(webApi, weatherDao, dispatchThread);
    }

    @Provides
    @Singleton
    WeatherDatabase provideDatabase(Application application){
        this.weatherDatabase = Room.databaseBuilder(application,
                WeatherDatabase.class,
                "weather")
                .build();
        return weatherDatabase;
    }

    @Provides
    @Singleton
    public WeatherDao provideWeatherDao(WeatherDatabase weatherDatabase){
        return weatherDatabase.weatherDao();
    }


}
