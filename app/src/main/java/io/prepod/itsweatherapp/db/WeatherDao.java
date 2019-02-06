package io.prepod.itsweatherapp.db;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

public interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveWeather(CityWeather cityWeather);

    @Query("SELECT * FROM cityweather WHERE cityName = :cityName")
    LiveData<CityWeather> loadWeatherLiveData(String cityName);

    @Query("SELECT * FROM cityweather WHERE cityName = :cityName")
    CityWeather loadWeather(String cityName);
}
