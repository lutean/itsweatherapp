package io.prepod.itsweatherapp.views.weather;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import io.prepod.itsweatherapp.DataWithStatus;
import io.prepod.itsweatherapp.ItsWeatherApp;
import io.prepod.itsweatherapp.R;
import io.prepod.itsweatherapp.data.entities.CityWeather;
import io.prepod.itsweatherapp.di.ViewModelFactory;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;


public class WeatherFragment extends Fragment{

    private static final String CITY_PARAM = "cityName";

    private TextView temperatureTxt;
    private TextView weatherDescriptionTxt;
    private TextView nameOfCityTxt;

    private String cityName;

    @Inject
    ViewModelFactory viewModelFactory;

    private WeatherViewModel viewModel;

    public WeatherFragment() {
    }

    public static WeatherFragment newInstance(String cityName) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putString(CITY_PARAM, cityName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            cityName = getArguments().getString(CITY_PARAM);
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(WeatherViewModel.class);
            viewModel.getWeatherByName(cityName);
            viewModel.getCity().observe(this, this::fillViews);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_weather, container, false);
        temperatureTxt = v.findViewById(R.id.text_weather_temperature);
        weatherDescriptionTxt = v.findViewById(R.id.text_weather_description);
        nameOfCityTxt = v.findViewById(R.id.text_weather_city);
        return v;
    }


    @Override
    public void onAttach(Context context) {
        ItsWeatherApp.getAppComponent().inject(this);
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void fillViews(DataWithStatus<CityWeather> weatherData) {
        if (weatherData == null) return;
        switch (weatherData.getStatus()){
            case SUCCESS:
                temperatureTxt.setText(String.valueOf(Math.round(weatherData.getData().getTemp())));
                weatherDescriptionTxt.setText(weatherData.getData().getDescription());
                nameOfCityTxt.setText(weatherData.getData().getCityName());
                break;
            case ERROR:
                showMessage(weatherData.getMessage());
                break;
        }

    }

    private void showMessage(String message){
        if (TextUtils.isEmpty(message)) message = getString(R.string.error_common);
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

}
