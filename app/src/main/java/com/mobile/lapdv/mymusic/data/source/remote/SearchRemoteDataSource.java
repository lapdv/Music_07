package com.mobile.lapdv.mymusic.data.source.remote;

import com.mobile.lapdv.mymusic.BuildConfig;
import com.mobile.lapdv.mymusic.data.model.Track;
import com.mobile.lapdv.mymusic.data.source.SearchDataSource;
import com.mobile.lapdv.mymusic.data.source.remote.network.SearchTrackAsynTask;
import com.mobile.lapdv.mymusic.utils.ConfigApi;

/**
 * Created by lap on 15/05/2018.
 */

public class SearchRemoteDataSource implements SearchDataSource.RemoteDataSource {

    private static SearchRemoteDataSource sSearchRemoteDataSource;

    private SearchRemoteDataSource() {
    }

    public static SearchRemoteDataSource getInstance() {
        if (null == sSearchRemoteDataSource) {
            sSearchRemoteDataSource = new SearchRemoteDataSource();
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
}
