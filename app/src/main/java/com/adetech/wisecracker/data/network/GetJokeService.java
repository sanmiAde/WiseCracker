package com.adetech.wisecracker.data.network;

import com.adetech.wisecracker.data.database.Joke;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface GetJokeService
{
    @Headers("Accept: application/json")
    @GET("/")
    Call<List<Joke>> getJokes();
}
