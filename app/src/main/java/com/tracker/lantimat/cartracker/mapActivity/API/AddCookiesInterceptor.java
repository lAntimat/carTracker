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
        HashSet<String> preferences = SharedPreferenceHelper.getSharedPreferenceStringSet(MyApplication.getContext(), Constants.AUTH_COOKIE, new HashSet<String>());

        for (String cookie : preferences) {
            builder.addHeader("Cookie", cookie);
            Log.v("OkHttp", "Adding Header: " + cookie); // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
        }
        return chain.proceed(builder.build());
    }
}
