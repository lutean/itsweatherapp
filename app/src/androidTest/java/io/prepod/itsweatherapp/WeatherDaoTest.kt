package io.prepod.itsweatherapp

import io.prepod.itsweatherapp.data.entities.CityWeather
import org.junit.Test
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.CoreMatchers.`is`

class WeatherDaoTest: DbTest(){
    @Test
    fun insertAndGet(){
        val weather = CityWeather(200,
                "Barcelona",
                25.3,
                "cloudy",
                1549897756)

        db.weatherDao().saveWeather(weather)

        val loaded = db.weatherDao().loadWeather("Barcelona")
        assertThat(loaded.temp, `is` (25.3))
    }
}