package com.movietest.moviesearchapp.rest;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {

    public static class SingletonHolder {
        private static final ApiManager instance = new ApiManager();
    }

    private static final String BASE_URL = "http://www.omdbapi.com/";

    private Retrofit retrofit;
    private OMDbApi api;

    private ApiManager() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiManager getInstance() {
        return SingletonHolder.instance;
    }

    public OMDbApi getSearchResponse() {
        if (api == null) {
            api = retrofit.create(OMDbApi.class);
        }
        return api;
    }

}
