package io.prepod.itsweatherapp.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CityWeather {

    @PrimaryKey
    private int id;
    private String cityName;
    private double temp;
    private String description;
    private long date;

    public CityWeather(int id, String cityName, double temp, String description, long date) {
        this.id = id;
        this.cityName = cityName;
        this.temp = temp;
        this.description = description;
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
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

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
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
