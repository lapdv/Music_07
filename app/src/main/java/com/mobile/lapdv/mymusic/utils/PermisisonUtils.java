package com.mobile.lapdv.mymusic.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by lap on 17/05/2018.
 */

public class PermisisonUtils {

    private static final int PERMISSION_WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 200;

    public static boolean checkPermission(Context context) {
        int result = ContextCompat.checkSelfPermission(context, WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(context, READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED && result1 ==
                PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermission(Context context) {
        ActivityCompat.requestPermissions((Activity) context,
                new String[]{ACCESS_FINE_LOCATION, WRITE_EXTERNAL_STORAGE},
                PERMISSION_WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
    }
}
