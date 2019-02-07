package io.prepod.itsweatherapp.di;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import dagger.Reusable;
import io.prepod.itsweatherapp.Config;
import io.prepod.itsweatherapp.WebApi;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class WebModule {

    @Provides
    @Reusable
    HttpLoggingInterceptor provideInterceptor(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(message -> Log.i("OkHTTP", message));
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }

    @Provides
    @Reusable
    OkHttpClient provideHttpClient(HttpLoggingInterceptor logging){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    HttpUrl originalHttpUrl = original.url();

                    HttpUrl url = originalHttpUrl.newBuilder()
                            .addQueryParameter("appid", Config.API_KEY)
                            .build();

                    Request.Builder requestBuilder = original.newBuilder()
                            .url(url);

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                })
                .build();
        return client;
    }

    @Provides
    @Reusable
    public WebApi provideWebApi(Retrofit retrofit){
        return retrofit.create(WebApi.class);
    }

    @Provides
    @Reusable
    public Retrofit provideRetrofit(OkHttpClient client){
        return new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
