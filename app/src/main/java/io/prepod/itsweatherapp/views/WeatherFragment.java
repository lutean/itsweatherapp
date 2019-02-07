package io.prepod.itsweatherapp.views;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import io.prepod.itsweatherapp.ItsWeatherApp;
import io.prepod.itsweatherapp.R;
import io.prepod.itsweatherapp.di.ViewModelFactory;
import io.prepod.itsweatherapp.di.ViewModelModule;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;


public class WeatherFragment extends Fragment {

    private static final String CITY_PARAM = "cityName";

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
            String cityName = getArguments().getString(CITY_PARAM);
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(WeatherViewModel.class);
//            viewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
            viewModel.init(cityName);
            viewModel.getCity().observe(this, city -> {
                Log.i("My!", "onActivityCreated: " + city);
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false);
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

}
