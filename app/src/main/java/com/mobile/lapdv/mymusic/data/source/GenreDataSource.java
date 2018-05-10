package com.mobile.lapdv.mymusic.data.source;

import com.mobile.lapdv.mymusic.data.model.Genre;

import java.util.List;

/**
 * Created by lap on 09/05/2018.
 */

public interface GenreDataSource {

    interface RemoteDataSource {
        void getSongsRemote(OnFetchDataListener<Genre> listener);
    }

    interface OnFetchDataListener<T> {
        void onFetchDataSuccess(List<T> data);

        void onFetchDataFailure(String error);
    }
}
