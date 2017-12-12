package com.tracker.lantimat.cartracker.mapActivity.API;

import android.content.Context;

import com.loopj.android.http.PersistentCookieStore;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.client.CookieStore;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by GabdrakhmanovII on 08.12.2017.
 */

public class RetrofitClient {

    private static OkHttpClient.Builder okHttpClient;
    private static Retrofit retrofit = null;

    public static void initClient() {
        okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60 * 5, TimeUnit.SECONDS)
                .readTimeout(60 * 5, TimeUnit.SECONDS)
                .writeTimeout(60 * 5, TimeUnit.SECONDS);
        okHttpClient.interceptors().add(new AddCookiesInterceptor());
        okHttpClient.interceptors().add(new ReceivedCookiesInterceptor());
    }

    public static Retrofit getClient(String baseUrl) {

        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
