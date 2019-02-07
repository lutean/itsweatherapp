package io.prepod.itsweatherapp.di;

import androidx.lifecycle.ViewModelProvider;
import dagger.Module;
import dagger.Provides;
import io.prepod.itsweatherapp.WeatherRepository;

@Module
public class ViewModelModule {

    @Provides
    ViewModelProvider.Factory provideViewModelFactory(WeatherRepository repository) {
        return new ViewModelFactory(repository);
    }

}
