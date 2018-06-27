package com.mobile.lapdv.mymusic.data.source;

import com.mobile.lapdv.mymusic.data.model.Track;

import java.util.List;

/**
 * Created by lap on 14/05/2018.
 */

public interface FavoriteDataSource {

    interface LocalDataSource {
        void getSongList(OnFetchDataListener<Track> listener);

        void addFavorite(Track track);

        void addTrackOffline(Track track);

        void removeFavorite(Track track);
    }

    interface OnFetchDataListener<T> {
        void onFetchDataSuccess(List<T> data);

        void onFetchDataFailure(String error);
    }
}
