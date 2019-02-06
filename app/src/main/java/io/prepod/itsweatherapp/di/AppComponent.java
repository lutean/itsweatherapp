package io.prepod.itsweatherapp.di;

import javax.inject.Singleton;

import dagger.Component;
import io.prepod.itsweatherapp.DispatchThread;
import io.prepod.itsweatherapp.MainActivity;
import io.prepod.itsweatherapp.WeatherRepository;
import io.prepod.itsweatherapp.db.WeatherDao;
import io.prepod.itsweatherapp.views.WeatherViewModel;

@Singleton
@Component(modules = {WebModule.class, RepositoryModule.class, AppModule.class, ThreadModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);
    void inject(WeatherRepository weatherRepository);
    void inject(WeatherViewModel weatherViewModel);

}
