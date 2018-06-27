package com.mobile.lapdv.mymusic.utils;

import com.mobile.lapdv.mymusic.data.model.Track;

/**
 * Created by lap on 11/05/2018.
 */

public class Constant {

    public static final String ERROR_MESSAGE = "Not data";
    public static final int SPAN_TWO_COUNT = 2;
    public static final long POST_DELAY = 1000;
    public static final int DEFAULT_OFFSET = 10;
    public static final String POSITION = "POSITION";
    public static final int DEFAULT_LIMIT = 20;
    public static final int SPAN_ONE_COUNT = 1;
    public static final String BUNDLE_GENRE = "BUNDLE_GENRE";

    public static String replaceAvartarUrl(String url) {
        return EmptyUtils.isNotEmpty(url) ?
                url.replace(Track.TrackEntity.LARGE_IMAGE_SIZE,
                        Track.TrackEntity.BETTER_IMAGE_SIZE) : "";
    }
}
