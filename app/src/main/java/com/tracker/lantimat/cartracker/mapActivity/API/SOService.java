package com.tracker.lantimat.cartracker.mapActivity.API;

import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by GabdrakhmanovII on 08.12.2017.
 */

public interface SOService {

    @FormUrlEncoded
    @POST("auth")
    Call<Gson> login(@Field("login") String login, @Field("password") String pass);

    @GET("objects")
    Call<ArrayList<CarsR>> getObjects();
}
