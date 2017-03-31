package com.peng.migong.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by Penglingxiao on 2017/3/31.
 */

public class ScreenUtil {


    /**
     * 获取屏幕参数
     *
     * @param context
     * @return
     */
    public static DisplayMetrics getScreenSize(Context context) {
        DisplayMetrics dislayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(dislayMetrics);
        return dislayMetrics;
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = getScreenSize(context);
        return displayMetrics.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics displayMetrics = getScreenSize(context);
        return displayMetrics.heightPixels;
    }

    public static float getDeviceDensity(Context context) {
        DisplayMetrics displayMetrics = getScreenSize(context);
        return displayMetrics.density;
    }
}
