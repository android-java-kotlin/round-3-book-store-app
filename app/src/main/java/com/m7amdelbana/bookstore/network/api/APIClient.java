package com.m7amdelbana.bookstore.network.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.m7amdelbana.bookstore.network.api.APIConstants.BASE_URL;

public class APIClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit
                    .Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build();
        }
        return retrofit;
    }
}
