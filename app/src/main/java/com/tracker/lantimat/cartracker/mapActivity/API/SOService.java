package com.tracker.lantimat.cartracker.mapActivity.API;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by GabdrakhmanovII on 08.12.2017.
 */

public interface SOService {
    @GET("objects")
    Call<CarsR> getObjects();
}
