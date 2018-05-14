package com.mobile.lapdv.mymusic.utils;

import com.mobile.lapdv.mymusic.data.model.Track;

/**
 * Created by lap on 11/05/2018.
 */

public class Constant {

    public static String replaceAvartarUrl(String url) {
        return EmptyUtils.isNotEmpty(url) ?
                url.replace(Track.TrackEntity.LARGE_IMAGE_SIZE,
                        Track.TrackEntity.BETTER_IMAGE_SIZE) : "";
    }
}
