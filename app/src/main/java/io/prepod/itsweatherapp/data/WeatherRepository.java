package io.prepod.itsweatherapp.data;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import io.prepod.itsweatherapp.DispatchThread;
import io.prepod.itsweatherapp.ItsWeatherApp;
import io.prepod.itsweatherapp.WebApi;
import io.prepod.itsweatherapp.containers.WeatherByName;
import io.prepod.itsweatherapp.data.dao.WeatherDao;
import io.prepod.itsweatherapp.data.entities.CityWeather;
import retrofit2.Response;

@Singleton
public class WeatherRepository {

    private final int FRESH_TIME = 60 * 1000;

    private WebApi webApi;
    private WeatherDao weatherDao;
    private DispatchThread dispatchThread;

    public WeatherRepository() {
        ItsWeatherApp.getAppComponent().inject(this);
    }

    @Inject
    public WeatherRepository(WebApi webApi, WeatherDao weatherDao, DispatchThread dispatchThread) {
        this();
        this.webApi = webApi;
        this.weatherDao = weatherDao;
        this.dispatchThread = dispatchThread;
    }

    public LiveData<CityWeather> getWeatherByName(String cityName) {
        updateWeather(cityName);
        return weatherDao.loadWeatherLiveData(cityName);
    }

    private void updateWeather(String cityName) {
        dispatchThread.postRunnable(() -> {

            if (!isFreshWeather(weatherDao.loadWeather(cityName))) {

                try {
                    Response<WeatherByName> response = webApi.getWeatherByName(cityName).execute();
                    //TODO error handle
                    weatherDao.saveWeather(transformWeatherData(response.body()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private boolean isFreshWeather(CityWeather cityWeather) {
        if (cityWeather == null) return false;
        long date = cityWeather.getDate();
        return System.currentTimeMillis() - date <= FRESH_TIME;
    }

    private CityWeather transformWeatherData(WeatherByName weatherByName) {
        if (weatherByName == null) return null;
        return new CityWeather(weatherByName.getName(),
                weatherByName.getMain().getTemp(),
                weatherByName.getWeather().get(0).getDescription(),
                weatherByName.getDt());
    }

}
