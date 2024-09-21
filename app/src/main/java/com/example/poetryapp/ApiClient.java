package com.example.poetryapp;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {


    public static Retrofit retrofit = null;

    public static Retrofit getClient() {
        String url = "http://192.168.11.49/ApiDesigns/";
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
        Gson gson = new GsonBuilder().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(url).client(okHttpClient).
                addConverterFactory(GsonConverterFactory.create(gson)).build();
        return retrofit;
    }


}
