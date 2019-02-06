package io.prepod.itsweatherapp;

import android.app.Application;

public class ItsWeatherApp extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .webModule(new WebModule())
                .build();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
