package com.tracker.lantimat.cartracker.mapActivity.API;

/**
 * Created by coderzlab on 22/2/17.
 */
import android.util.Log;

import com.tracker.lantimat.cartracker.Constants;
import com.tracker.lantimat.cartracker.utils.MyApplication;
import com.tracker.lantimat.cartracker.utils.SharedPreferenceHelper;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
/**
 * This interceptor put all the Cookies in Preferences in the Request.
 * Your implementation on how to get the Preferences may vary.
 */
public class AddCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        String preferences =  SharedPreferenceHelper.getSharedPreferenceString(MyApplication.getContext(), Constants.AUTH_COOKIE, "");

        //for (String cookie : preferences) {
            builder.addHeader("Cookie", preferences);
            Log.v("OkHttp", "Adding Header: " + preferences); // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
        //}
        return chain.proceed(builder.build());
    }
}
