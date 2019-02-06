package io.prepod.itsweatherapp;

import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.prepod.itsweatherapp.containers.WeatherByName;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

@Singleton
public class WeatherRepository {

    @Inject
    Retrofit retrofit;
    @Inject
    WebApi webApi;

    private WeatherCache weatherCache;

    public WeatherRepository() {
        ItsWeatherApp.getAppComponent().inject(this);
        if (webApi != null){
            Log.i("My!", "onCreate: ");
        }
    }

    public LiveData<WeatherByName> getWeatherByName(String cityName){
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

}
