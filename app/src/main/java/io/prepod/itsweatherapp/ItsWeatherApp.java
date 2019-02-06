package io.prepod.itsweatherapp;

import android.app.Application;

import io.prepod.itsweatherapp.di.AppComponent;
import io.prepod.itsweatherapp.di.AppModule;
import io.prepod.itsweatherapp.di.DaggerAppComponent;
import io.prepod.itsweatherapp.di.RepositoryModule;
import io.prepod.itsweatherapp.di.ThreadModule;
import io.prepod.itsweatherapp.di.WebModule;

public class ItsWeatherApp extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .repositoryModule(new RepositoryModule())
                .webModule(new WebModule())
                .threadModule(new ThreadModule())
                .build();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
