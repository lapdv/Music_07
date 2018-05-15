package com.mobile.lapdv.mymusic.data.local;

import android.content.Context;

import com.mobile.lapdv.mymusic.data.local.database.MusicDB;
import com.mobile.lapdv.mymusic.data.model.Track;
import com.mobile.lapdv.mymusic.data.source.SearchDataSource;

/**
 * Created by lap on 15/05/2018.
 */

public class SearchLocalDataSource implements SearchDataSource.LocalDataSource {

    private static SearchLocalDataSource sSearchLocalDataSource;
    private MusicDB mMusicDB;

    private SearchLocalDataSource(Context context) {
        mMusicDB = MusicDB.getInstance(context);
    }

    public static SearchLocalDataSource getInstance(Context context) {
        if (null == sSearchLocalDataSource) {
            sSearchLocalDataSource = new SearchLocalDataSource(context);
        }
        return sSearchLocalDataSource;
    }

    @Override
    public void addFavorite(Track track) {
        if (mMusicDB != null) {
            mMusicDB.insertTrackFavorite(track);
        }
    }
}
