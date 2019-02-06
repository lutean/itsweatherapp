package io.prepod.itsweatherapp;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Retrofit;

import android.os.Bundle;
import android.util.Log;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    private AppComponent appComponent;
    @Inject
    Retrofit retrofit;
    @Inject
    WebApi webApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ItsWeatherApp.getAppComponent().inject(this);

        if (webApi != null){
            Log.i("My!", "onCreate: ");
        }

    }
}
