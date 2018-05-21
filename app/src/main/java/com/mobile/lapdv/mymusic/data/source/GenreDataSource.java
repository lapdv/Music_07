package com.mobile.lapdv.mymusic.data.source;

import com.mobile.lapdv.mymusic.data.model.Genre;
import com.mobile.lapdv.mymusic.data.model.Track;
import com.mobile.lapdv.mymusic.screen.track.TrackMorePresenter;

import java.util.List;

/**
 * Created by lap on 09/05/2018.
 */

public interface GenreDataSource {

    interface RemoteDataSource {
        void getSongsRemote(OnFetchDataListener<Genre> listener);

        void getSongsMoreRemote(String type, int limit, int offset,OnFetchDataListener<Genre> listene);
    }

    interface OnFetchDataListener<T> {
        void onFetchDataSuccess(List<T> data);

        void onFetchDataFailure(String error);
    }
}
