package com.example.kyung.googlemapfunction.util;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by Kyung on 2017-11-23.
 */

public class DeviceUtil {

    private static DisplayMetrics getActivityDisplayMetrics(Activity activity){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }
    public static int getScreenHeight(Activity activity){
        return getActivityDisplayMetrics(activity).heightPixels;
    }
    public static int getScreenWidth(Activity activity){
        return getActivityDisplayMetrics(activity).widthPixels;
    }
}
