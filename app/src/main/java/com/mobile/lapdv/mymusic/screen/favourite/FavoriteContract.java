package com.mobile.lapdv.mymusic.screen.favourite;

import com.mobile.lapdv.mymusic.base.mvp.BaseMvpPresenter;
import com.mobile.lapdv.mymusic.base.mvp.BaseView;
import com.mobile.lapdv.mymusic.data.model.Track;

import java.util.List;

/**
 * Created by lap on 14/05/2018.
 */

public interface FavoriteContract {
    /**
     * All the action of favorite view
     */
    interface View extends BaseView {
        void getSongsSuccess(List<Track> trackList);

        void getSongsFailure(String messages);
    }

    /**
     * All the logic of favorite screen
     */
    interface Presenter extends BaseMvpPresenter<View> {
        void getSongs();

        void removeFavorite(Track track);
    }
}
