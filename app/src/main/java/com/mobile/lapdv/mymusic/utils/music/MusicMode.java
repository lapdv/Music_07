package com.mobile.lapdv.mymusic.utils.music;

import android.annotation.SuppressLint;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by lap on 16/05/2018.
 */

public class MusicMode {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({MusicMode.PlayMode.SINGLE,
            MusicMode.PlayMode.LOOP,
            MusicMode.PlayMode.RANDOM})
    public @interface PlayMode {
        int SINGLE = 0;
        int LOOP = 1;
        int RANDOM = 2;
    }

    @SuppressLint("SupportAnnotationUsage")
    @PlayMode
    public static int valueOf(int value) {
        switch (value) {
            case PlayMode.RANDOM:
                return PlayMode.RANDOM;
            case PlayMode.LOOP:
                return PlayMode.LOOP;
            default:
                return PlayMode.SINGLE;
        }
    }

}
