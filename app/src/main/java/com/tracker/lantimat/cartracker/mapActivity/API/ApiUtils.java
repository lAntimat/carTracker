package com.tracker.lantimat.cartracker.mapActivity.API;

/**
 * Created by GabdrakhmanovII on 08.12.2017.
 */

public class ApiUtils {

    public static final String BASE_URL = "https://sandbox.rightech.io/api/v1/";

    public static SOService getSOService() {
        return RetrofitClient.getClient(BASE_URL).create(SOService.class);
    }
}
