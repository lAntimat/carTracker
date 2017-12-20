package com.tracker.lantimat.cartracker.mapActivity.API;

import com.google.gson.Gson;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by GabdrakhmanovII on 08.12.2017.
 */

public interface SOService {

    @FormUrlEncoded
    @POST("auth")
    Call<Gson> login(@Field("login") String login, @Field("password") String pass);

    @GET("objects")
    Observable<ArrayList<CarsR>> getObjects();

    @GET("objects/{Id}/packets")
    Observable<ArrayList<TrackR>> getTrack(@Path("Id") String customerId,
                                          @Query("begin") long begin,
                                          @Query("end") long end);
}
