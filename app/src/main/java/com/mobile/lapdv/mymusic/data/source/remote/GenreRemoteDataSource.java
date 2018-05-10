package com.mobile.lapdv.mymusic.data.source.remote;

import com.mobile.lapdv.mymusic.data.model.Genre;
import com.mobile.lapdv.mymusic.data.source.GenreDataSource;
import com.mobile.lapdv.mymusic.data.source.remote.network.HttpGetAsynTask;
import com.mobile.lapdv.mymusic.utils.ConfigApi;

/**
 * Created by lap on 09/05/2018.
 */

public class GenreRemoteDataSource implements GenreDataSource.RemoteDataSource {
    private static GenreRemoteDataSource sGenreRemoteDataSource;

    private GenreRemoteDataSource() {

    }

    public static GenreRemoteDataSource getInstance() {
        if (null == sGenreRemoteDataSource) {
            sGenreRemoteDataSource = new GenreRemoteDataSource();
        }
        return sGenreRemoteDataSource;
    }

    @Override
    public void getSongsRemote(GenreDataSource.OnFetchDataListener<Genre> listener) {
        for (int i = 0; i < ConfigApi.LIST_TRACK_GENRES.size(); i++) {
            String url = ConfigApi.getUrl(ConfigApi.LIST_TRACK_GENRES.get(i),
                    ConfigApi.Api.API_LIMIT_DEFAULT,
                    ConfigApi.Api.API_OFFSET_DEFAULT);
            new HttpGetAsynTask(i, listener).executeTask(url);
        }
    }
}
