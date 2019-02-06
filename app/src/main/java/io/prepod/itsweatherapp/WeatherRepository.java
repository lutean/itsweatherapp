package io.prepod.itsweatherapp;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.prepod.itsweatherapp.containers.WeatherByName;
import io.prepod.itsweatherapp.db.CityWeather;
import io.prepod.itsweatherapp.db.WeatherDao;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class WeatherRepository {

    private final int FRESH_TIME = 60 * 1000 * 1000;

    private WeatherCache weatherCache;
    private WebApi webApi;
    private WeatherDao weatherDao;
    private Executor executor;

    @Inject
    public WeatherRepository(WebApi webApi, WeatherDao weatherDao, Executor executor) {
    /*    ItsWeatherApp.getAppComponent().inject(this);
        if (webApi != null){
            Log.i("My!", "onCreate: ");
        }*/
        this.webApi = webApi;
        this.weatherDao = weatherDao;
    }

    public LiveData<WeatherByName> getWeatherByName(String cityName) {
        LiveData<WeatherByName> cached = weatherCache.get(cityName);
        if (cached != null) return cached;

        final MutableLiveData<WeatherByName> data = new MutableLiveData<>();
        weatherCache.put(cityName, data);

        webApi.getWeatherByName(cityName).enqueue(new Callback<WeatherByName>() {
            @Override
            public void onResponse(Call<WeatherByName> call, Response<WeatherByName> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<WeatherByName> call, Throwable t) {

            }
        });
        return data;
    }

    private void updateWeather(String cityName) {
        executor.execute(() -> {

            if (!isFreshWeather(weatherDao.loadWeather(cityName))) {

                try {
                    Response<WeatherByName> response = webApi.getWeatherByName(cityName).execute();
                    weatherDao.saveWeather(transformWeatherData(response.body()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private boolean isFreshWeather(CityWeather cityWeather) {
        long date = cityWeather.getDate();
        return System.currentTimeMillis() - date <= FRESH_TIME;
    }

    private CityWeather transformWeatherData(WeatherByName weatherByName) {
        if (weatherByName == null) return null;
        return new CityWeather(weatherByName.getName(),
                String.valueOf(weatherByName.getMain().getTemp()),
                weatherByName.getWeather().get(0).getDescription(),
                weatherByName.getDt());
    }

}
