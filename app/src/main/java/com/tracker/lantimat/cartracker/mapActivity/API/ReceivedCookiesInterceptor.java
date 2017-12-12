package com.tracker.lantimat.cartracker.mapActivity.API;

/**
 * Created by coderzlab on 22/2/17.
 */
import android.app.Application;

import com.tracker.lantimat.cartracker.Constants;
import com.tracker.lantimat.cartracker.utils.MyApplication;
import com.tracker.lantimat.cartracker.utils.SharedPreferenceHelper;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Response;
/**
 * This Interceptor add all received Cookies to the app DefaultPreferences.
 * Your implementation on how to save the Cookies on the Preferences may vary.
 */
public class ReceivedCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            Set<String> cookies = new HashSet<>();
            String authCookie = "";
            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
                authCookie = header;
            }
            SharedPreferenceHelper.setSharedPreferenceString(MyApplication.getContext(), Constants.AUTH_COOKIE, authCookie);
        }
        return originalResponse;
    }
}
