package com.peng.migong.util;

import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Penglingxiao on 2017/4/6.
 */

public class StatusUtil {

    public static void setWindowStatusBarColor(Activity activity, int resId) {
        try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(activity.getResources().getColor(resId));
            }

        } catch (Exception e) {
        }
    }

}
