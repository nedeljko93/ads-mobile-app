package com.example.adsmobileapp.rest;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestManager {
    public static RestManager mInstance;

    private Retrofit restAdapter;

    private RestManager() {
        mInstance = this;
    }

    public static RestManager getInstance() {
        if (mInstance == null) {
            return new RestManager();
        }
        return mInstance;
    }

    private void buildRestAdapter() {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(RestApiConstants.REST_CALL_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(RestApiConstants.REST_CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                .build();

        restAdapter = new Retrofit.Builder()
                .baseUrl(RestApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .client(client)
                .build();
    }

    public <T> T getService(Class<T> serviceClass) {
        buildRestAdapter();
        return restAdapter.create(serviceClass);
    }

}
