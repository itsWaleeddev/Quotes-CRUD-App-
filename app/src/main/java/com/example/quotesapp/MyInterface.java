package com.example.quotesapp;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MyInterface {
    @GET("Read.php")
    Call<ApiResponse> getData();

    @FormUrlEncoded
    @POST("Delete.php")
    Call<DeleteResponse> deletePoetry(@Field("Id") String poetry_id);

    @FormUrlEncoded
    @POST("Add.php")
    Call<DeleteResponse> addPoetry(@Field("Quote") String poetry_data, @Field("Author_Name") String poet_name);

    @FormUrlEncoded
    @POST("Update.php")
    Call<DeleteResponse> updatePoetry(@Field("Quote") String poetry_data, @Field("Id") int id);

}
