package com.mobile.lapdv.mymusic.utils;

import android.support.annotation.StringDef;

import com.mobile.lapdv.mymusic.BuildConfig;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by lap on 08/05/2018.
 */

public class ConfigApi {

    private static String BASE_URL = "https://api-v2.soundcloud.com/charts";
    private static String GET_KIND_TOP = "?kind=top";

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
}
