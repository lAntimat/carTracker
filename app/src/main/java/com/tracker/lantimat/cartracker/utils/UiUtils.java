package com.tracker.lantimat.cartracker.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Size;

/**
 * Created by GabdrakhmanovII on 04.08.2017.
 */

public class UiUtils {

    public static Context context = null;

    public static void setContext(Context context) {
        UiUtils.context = context;
    }

    public static boolean isGingerbread() {
        // Can use static final constants like HONEYCOMB, declared in later versions
        // of the OS since they are inlined at compile time. This is guaranteed behavior.
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    public static boolean isHoneycomb() {
        // Can use static final constants like HONEYCOMB, declared in later versions
        // of the OS since they are inlined at compile time. This is guaranteed behavior.
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    public static int dpToPx(int dp) {
        return dpToPx(UiUtils.context, dp);
    }

    public static int dpToPx(Context context, int dp) {
        if (context != null) {
            float density = context.getResources().getDisplayMetrics().density;
            return Math.round((float)dp * density);
        }

        return 0;
    }

    public static int pxToDp(int px) {
        return pxToDp(UiUtils.context, px);
    }

    public static int pxToDp(Context context, int px) {
        if (context != null) {
            float density = context.getResources().getDisplayMetrics().density;
            return Math.round((float)px / density);
        }

        return 0;
    }



    /**
     *
     * @param width
     * @param height
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static Size makeSize(int width, int height) {
        return new Size(width, height);
    }




    //-- --//
    public static Bitmap createScaledBitmap(int resId, int width, int height) {
        try {
            Bitmap bitmap = BitmapFactory.decodeResource(
                    context.getResources(), resId);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
            bitmap.recycle();  // 비트맵의 메모리 해제를 직접적으로 요청
            return scaledBitmap;
        }
        catch (Exception e) {
//			LogUtils.e(LOG_TAG, "error=%s", e.getMessage());
        }
        return null;
    }
}