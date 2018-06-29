package com.tracker.lantimat.cartracker.forDriver;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.appizona.yehiahd.fastsave.FastSave;
import com.google.gson.Gson;
import com.tracker.lantimat.cartracker.LoginActivity;
import com.tracker.lantimat.cartracker.mapActivity.API.ApiUtils;
import com.tracker.lantimat.cartracker.mapActivity.API.SOService;
import com.tracker.lantimat.cartracker.mapActivity.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthHelper {

    public static String TAG = "AuthHelper";
    public static String IS_AUTH = "IS_AUTH";
    public static String LOGIN = "LOGIN";
    public static String PASS = "PASS";

    private static Context context;
    private static boolean isAuth = false;

    public AuthHelper() {
    }

    public static void init(Context _context) {
        context = _context;
    }

    public interface OnLoginCallback {
        void onSuccess();
        void onFailure();
    }

    public static boolean isAuth () {
            //if(FastSave.getInstance().getBoolean(IS_AUTH)) return true;
            //else return false;
        return isAuth;
    }

    public static void login(String login, String password, OnLoginCallback onLoginCallback) {
        loginToRighTech(login, password, onLoginCallback);
    }

    public static void login(OnLoginCallback onLoginCallback) {
        String login = FastSave.getInstance().getString(LOGIN);
        String pass = FastSave.getInstance().getString(PASS);

        if(login == null || pass == null) {
            context.startActivity(new Intent(context, LoginActivity.class));
        } else {
            loginToRighTech(login, pass, onLoginCallback);
        }
    }

    private static void loginToRighTech(String login, String password, OnLoginCallback onLoginCallback) {
        SOService mService;
        mService = ApiUtils.getSOService();
        Call<Gson> call = mService.login("driver001", "Driver89600747198");
        call.enqueue(new Callback<Gson>() {
            @Override
            public void onResponse(Call<Gson> call, Response<Gson> response) {
                Log.d(TAG, "AuthSuccess " + response.toString());

                FastSave.getInstance().saveBoolean(IS_AUTH, true);
                FastSave.getInstance().saveString(LOGIN, login);
                FastSave.getInstance().saveString(PASS, password);
                isAuth = true;
                onLoginCallback.onSuccess();

            }

            @Override
            public void onFailure(Call<Gson> call, Throwable t) {
                Log.d(TAG, "AuthFail ");
                FastSave.getInstance().saveBoolean(IS_AUTH, false);
                isAuth = false;
                onLoginCallback.onFailure();

            }
        });
    }

    public static User getUser() {
        return new User(1, 1, "123456 4587", "Фарид", "Сафин", "https://24smi.org/public/media/235x307/celebrity/2017/02/21/SscyoHMHET6M_marat-safin.jpg");
    }

    public static void signOut() {
        isAuth = false;
        FastSave.getInstance().deleteValue(LOGIN);
        FastSave.getInstance().deleteValue(PASS);
    }

}
