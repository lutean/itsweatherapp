package io.prepod.itsweatherapp;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {WebModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);

}
