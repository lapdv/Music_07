package com.mobile.lapdv.mymusic.application;

import android.app.Application;

import com.mobile.lapdv.mymusic.utils.PreferencesUtils;

/**
 * Created by lap on 18/05/2018.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PreferencesUtils.init(this);
    }
}
