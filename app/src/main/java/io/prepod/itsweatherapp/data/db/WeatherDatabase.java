package io.prepod.itsweatherapp.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import io.prepod.itsweatherapp.data.dao.WeatherDao;
import io.prepod.itsweatherapp.data.entities.CityWeather;

@Database(entities = {CityWeather.class}, version = 1)
public abstract class WeatherDatabase extends RoomDatabase {
    public abstract WeatherDao weatherDao();
}
