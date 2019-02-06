package io.prepod.itsweatherapp.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.prepod.itsweatherapp.DispatchThread;

@Module
public class ThreadModule {

    @Singleton
    @Provides
    DispatchThread provideDispatchThread(){
        return new DispatchThread();
    }

}
