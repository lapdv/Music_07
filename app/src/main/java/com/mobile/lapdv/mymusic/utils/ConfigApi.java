package com.mobile.lapdv.mymusic.utils;

import android.support.annotation.StringDef;

import com.mobile.lapdv.mymusic.BuildConfig;
import com.mobile.lapdv.mymusic.data.model.Track;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by lap on 08/05/2018.
 */

public class ConfigApi {

    public static final String BASE_URL = "https://api-v2.soundcloud.com/charts";
    public static final String BASE_URL_SEARCH = "http://api.soundcloud.com/tracks";
    public static final String GET_KIND_TOP = "?kind=top";
    public static final String GET_FILLTER = "?filter=public";
    public static final String CONFIG_CLIENT_ID = "&client_id=";
    public static final String LIMIT_DEFAULT = "&limit=20";
    public static final String CONFIG_PARAM_SEARCH = "&q=";
    private static final String STREAM = "/stream";
    private static final String CLIENT_ID = "?client_id=";
    private static final String DOWNLOAD = "download/";

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({Api.API_ALL_MUSIC,
            Api.API_ALL_AUDIO,
            Api.API_ALTERNATIVER_ROCK,
            Api.API_AMBIENT,
            Api.API_CLASSICAL,
            Api.API_COUNTRY})
    public @interface Api {
        String API_ALL_MUSIC = "all-music";
        String API_ALL_AUDIO = "all-audio";
        String API_ALTERNATIVER_ROCK = "alternativerock";
        String API_AMBIENT = "ambient";
        String API_CLASSICAL = "classical";
        String API_COUNTRY = "country";
        String API_LIMIT_DEFAULT = "20";
        String API_OFFSET_DEFAULT = "20";
    }

    @Api
    public static List<String> LIST_TRACK_GENRES =
            new LinkedList<>(Arrays.asList(Api.API_ALL_MUSIC,
                    Api.API_ALL_AUDIO,
                    Api.API_ALTERNATIVER_ROCK,
                    Api.API_AMBIENT,
                    Api.API_CLASSICAL,
                    Api.API_COUNTRY));

    public static String getUrl(String genres, String limit, String offset) {
        StringBuilder stringBuilder = new StringBuilder(ConfigApi.BASE_URL)
                .append(ConfigApi.GET_KIND_TOP)
                .append("&genre=soundcloud:genres:")
                .append(genres)
                .append("&client_id=")
                .append(BuildConfig.API_KEY)
                .append("&limit=")
                .append(limit)
                .append("&offset=")
                .append(offset);
        return stringBuilder.toString();
    }

    public static String getUriStream(String uri) {
        StringBuilder stringBuilder = new StringBuilder(uri)
                .append(STREAM)
                .append(CLIENT_ID)
                .append(BuildConfig.API_KEY);
        return stringBuilder.toString();
    }

    public static String getUrlDownload(String uri) {
        return EmptyUtils.isNotEmpty(uri) ? uri.replace(STREAM, DOWNLOAD) : "";
    }
}
