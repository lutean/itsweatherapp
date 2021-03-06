package io.prepod.itsweatherapp.data;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import io.prepod.itsweatherapp.DataHelper;
import io.prepod.itsweatherapp.DataWithStatus;
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

    public LiveData<DataWithStatus<CityWeather>> getWeatherByName(String cityName) {
        return new DataHelper<WeatherByName, CityWeather>(dispatchThread) {

            @Override
            protected Response<WeatherByName> makeRequest() throws IOException {
                return webApi.getWeatherByName(cityName).execute();
            }

            @Override
            protected void storeData(CityWeather data) {
                weatherDao.saveWeather(data);
            }

            @Override
            protected boolean isFreshData(CityWeather cityWeather) {
                return isFreshWeather(cityWeather);
            }

            @Override
            protected CityWeather transformData(WeatherByName data) {
                return transformWeatherData(data);
            }

            @Override
            protected LiveData<CityWeather> loadFromStore() {
                return weatherDao.loadWeatherLiveData(cityName);
            }
        }.asLiveData();
    }

    private boolean isFreshWeather(CityWeather cityWeather) {
        if (cityWeather == null) return false;
        long date = cityWeather.getDate();
        return System.currentTimeMillis() - date <= FRESH_TIME;
    }

    private CityWeather transformWeatherData(WeatherByName weatherByName) {
        if (weatherByName == null) return null;
        return new CityWeather(weatherByName.getId(),
                weatherByName.getName().toLowerCase(),
                weatherByName.getMain().getTemp(),
                weatherByName.getWeather().get(0).getDescription(),
                weatherByName.getDt());
    }

}