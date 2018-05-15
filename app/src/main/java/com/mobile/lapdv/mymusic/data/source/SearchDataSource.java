package com.mobile.lapdv.mymusic.data.source;

import com.mobile.lapdv.mymusic.data.model.Track;

import java.util.List;

/**
 * Created by lap on 15/05/2018.
 */

public interface SearchDataSource {

    interface RemoteDataSource {
        void getSongRemote(OnFetchDataListener<Track> listener, String url);

        void addFavorite(Track track);
    }

    interface OnFetchDataListener<T> {
        void onFetchDataSuccess(List<T> data);

        void onFetchDataFailure(String error);
    }
}
