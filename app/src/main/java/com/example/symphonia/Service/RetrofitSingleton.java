package com.example.symphonia.Service;

import com.example.symphonia.Constants;

import retrofit2.Retrofit;

public class RetrofitSingleton {
    private static RetrofitSingleton instance = null;
    private RetrofitApi retrofitApi;

    public static RetrofitSingleton getInstance() {
        if (instance == null) {
            instance = new RetrofitSingleton();
        }

        return instance;
    }

    private RetrofitSingleton() {
        buildRetrofit();
    }

    private void buildRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .build();

        this.retrofitApi = retrofit.create(RetrofitApi.class);
    }

    public RetrofitApi getRetrofitApi() {
        return this.retrofitApi;
    }
}
