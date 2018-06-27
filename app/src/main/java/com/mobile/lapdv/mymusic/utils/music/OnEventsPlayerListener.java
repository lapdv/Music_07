package com.mobile.lapdv.mymusic.utils.music;

import com.mobile.lapdv.mymusic.data.model.Track;

/**
 * Created by lap on 16/05/2018.
 */

public interface OnEventsPlayerListener {
    void onChange(Track music);

    void onPlayerStart();

    void onPlayerPause();

    void onPublish(int progress);

    void onBufferingUpdate(int percent);

    void getDuration(int duration);
}
