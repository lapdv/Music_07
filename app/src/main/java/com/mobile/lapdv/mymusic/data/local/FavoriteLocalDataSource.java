package com.mobile.lapdv.mymusic.data.local;

import android.content.Context;

import com.mobile.lapdv.mymusic.data.local.database.MusicDB;
import com.mobile.lapdv.mymusic.data.model.Track;
import com.mobile.lapdv.mymusic.data.source.FavoriteDataSource;

/**
 * Created by lap on 14/05/2018.
 */

public class FavoriteLocalDataSource implements FavoriteDataSource.LocalDataSource {

    private static FavoriteLocalDataSource sFavoriteLocalDataSource;
    private MusicDB mMusicDB;

    public static FavoriteLocalDataSource getInstance(Context context) {
        if (sFavoriteLocalDataSource == null) {
            sFavoriteLocalDataSource = new FavoriteLocalDataSource(context);
        }
        return sFavoriteLocalDataSource;
    }

    private FavoriteLocalDataSource(Context context) {
        mMusicDB = MusicDB.getInstance(context);
    }

    @Override
    public void getSongList(FavoriteDataSource.OnFetchDataListener<Track> listener) {
        listener.onFetchDataSuccess(mMusicDB.getFavoriteList());
    }

    @Override
    public void addFavorite(Track track) {
        if (mMusicDB != null) {
            mMusicDB.insertTrackFavorite(track);
        }
    }

    @Override
    public void removeFavorite(Track track) {
        if (mMusicDB != null) {
            mMusicDB.delete(track);
        }
    }
}
