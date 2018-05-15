package com.mobile.lapdv.mymusic.data.source;

import android.content.Context;

import com.mobile.lapdv.mymusic.data.model.Track;
import com.mobile.lapdv.mymusic.data.local.SearchRemoteDataSource;

/**
 * Created by lap on 15/05/2018.
 */

public class SearchRepository implements SearchDataSource.RemoteDataSource {

    private static SearchRepository sSearchRepository;
    private SearchDataSource.RemoteDataSource mRemoteDataSource;

    private SearchRepository(SearchDataSource.RemoteDataSource dataSource) {
        mRemoteDataSource = dataSource;
    }

    public static SearchRepository getInstance(Context context) {
        if (null == sSearchRepository) {
            sSearchRepository = new SearchRepository(SearchRemoteDataSource.getInstance(context));
        }
        return sSearchRepository;
    }

    @Override
    public void getSongRemote(SearchDataSource.OnFetchDataListener<Track> listener, String url) {
        mRemoteDataSource.getSongRemote(listener, url);
    }

    @Override
    public void addFavorite(Track track) {
        mRemoteDataSource.addFavorite(track);
    }
}
