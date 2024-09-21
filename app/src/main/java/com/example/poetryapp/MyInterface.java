package com.example.poetryapp;

import com.example.poetryapp.models.PoetryInfo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MyInterface {
    @GET("readPoetry.php")
    Call<ApiResponse> getData();

    @FormUrlEncoded
    @POST("deletePoetry.php")
    Call<DeleteResponse> deletePoetry(@Field("id") String poetry_id);

    @FormUrlEncoded
    @POST("addPoetry.php")
    Call<DeleteResponse> addPoetry(@Field("Poetry") String poetry_data, @Field("Poet_Name") String poet_name);

    @FormUrlEncoded
    @POST("updatePoetry.php")
    Call<DeleteResponse> updatePoetry(@Field("Poetry") String poetry_data, @Field("id") int id);

}
