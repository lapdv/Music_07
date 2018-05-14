package com.mobile.lapdv.mymusic.data.source;

import android.content.Context;

import com.mobile.lapdv.mymusic.data.model.Track;
import com.mobile.lapdv.mymusic.data.source.local.FavoriteLocalDataSource;

/**
 * Created by lap on 14/05/2018.
 */

public class FavoriteRepository implements FavoriteDataSource.LocalDataSource {
    private static FavoriteRepository sFavoriteRepository;

    private FavoriteDataSource.LocalDataSource mLocalDataSource;

    private FavoriteRepository(FavoriteDataSource.LocalDataSource localDataSource) {
        mLocalDataSource = localDataSource;
    }

    public static FavoriteRepository getInstance(Context context) {
        if (sFavoriteRepository == null) {
            sFavoriteRepository = new FavoriteRepository(FavoriteLocalDataSource.getInstance(context));
        }
        return sFavoriteRepository;
    }

    @Override
    public void getSongList(FavoriteDataSource.OnFetchDataListener<Track> listener) {
          mLocalDataSource.getSongList(listener);
    }

    @Override
    public boolean deleteFavorite(Track track) {
        return mLocalDataSource != null && mLocalDataSource.deleteFavorite(track);
    }

    @Override
    public void addFavorite(Track track) {
        if (mLocalDataSource != null) {
            mLocalDataSource.addFavorite(track);
        }
    }
}
