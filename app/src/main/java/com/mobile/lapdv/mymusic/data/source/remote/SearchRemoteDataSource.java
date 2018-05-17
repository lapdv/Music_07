package com.mobile.lapdv.mymusic.data.source.remote;

import android.content.Context;

import com.mobile.lapdv.mymusic.BuildConfig;
import com.mobile.lapdv.mymusic.data.local.database.MusicDB;
import com.mobile.lapdv.mymusic.data.model.Track;
import com.mobile.lapdv.mymusic.data.source.SearchDataSource;
import com.mobile.lapdv.mymusic.data.source.remote.network.SearchTrackAsynTask;
import com.mobile.lapdv.mymusic.utils.ConfigApi;

/**
 * Created by lap on 15/05/2018.
 */

public class SearchRemoteDataSource implements SearchDataSource.RemoteDataSource {

    private static SearchRemoteDataSource sSearchRemoteDataSource;
    private MusicDB mMusicDB;

    private SearchRemoteDataSource(Context context) {
        mMusicDB = MusicDB.getInstance(context);
    }

    public static SearchRemoteDataSource getInstance(Context context) {
        if (null == sSearchRemoteDataSource) {
            sSearchRemoteDataSource = new SearchRemoteDataSource(context);
        }
        return sSearchRemoteDataSource;
    }

    @Override
    public void getSongRemote(SearchDataSource.OnFetchDataListener<Track> listener, String keyWord) {
        String url = new StringBuilder(ConfigApi.BASE_URL_SEARCH)
                .append(ConfigApi.GET_FILLTER)
                .append(ConfigApi.LIMIT_DEFAULT)
                .append(ConfigApi.CONFIG_CLIENT_ID)
                .append(BuildConfig.API_KEY)
                .append(ConfigApi.CONFIG_PARAM_SEARCH)
                .append(keyWord).toString();
        new SearchTrackAsynTask(listener).execute(url);
    }

    @Override
    public void addFavorite(Track track) {
        if (mMusicDB != null) {
            mMusicDB.insertTrackFavorite(track);
        }
    }
}
