package io.prepod.itsweatherapp.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.prepod.itsweatherapp.data.entities.CityWeather;

@Dao
public interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long saveWeather(CityWeather cityWeather);

    @Query("SELECT * FROM CityWeather WHERE cityName = :cityName")
    LiveData<CityWeather> loadWeatherLiveData(String cityName);

    @Query("SELECT * FROM CityWeather WHERE cityName = :cityName")
    CityWeather loadWeather(String cityName);
}
