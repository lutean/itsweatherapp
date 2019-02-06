package io.prepod.itsweatherapp.db;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class CityWeather {

    @PrimaryKey
    private int id;
    private String cityName;
    private String temp;
    private String description;
    private long date;

    @Ignore
    public CityWeather(String cityName, String temp, String description, long date) {
        this.cityName = cityName;
        this.temp = temp;
        this.description = description;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
