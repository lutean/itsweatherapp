package io.prepod.itsweatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import io.prepod.itsweatherapp.containers.Weather;
import io.prepod.itsweatherapp.views.WeatherFragment;
import retrofit2.Retrofit;

import android.os.Bundle;
import android.util.Log;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WeatherFragment fragment = WeatherFragment.newInstance("Kyiv");
        showScreen(fragment, fragment.getClass().getSimpleName(), false);

    }

    private void showScreen(Fragment fragment, String contentTag, boolean addToBackStack) {
        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(contentTag, 0);
        if (!fragmentPopped && manager.findFragmentByTag(contentTag) == null) {
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.frame_layout_main_container, fragment, contentTag);
            ft.addToBackStack(contentTag);
            ft.commit();
        }
    }
}
