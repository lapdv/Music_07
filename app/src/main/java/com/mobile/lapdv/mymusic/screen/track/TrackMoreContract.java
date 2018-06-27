package com.mobile.lapdv.mymusic.screen.track;

import com.mobile.lapdv.mymusic.base.mvp.BaseMvpPresenter;
import com.mobile.lapdv.mymusic.base.mvp.BaseView;
import com.mobile.lapdv.mymusic.data.model.Track;

import java.util.List;

/**
 * Created by lap on 16/05/2018.
 */

public interface TrackMoreContract {
    interface View extends BaseView {
        void loadMore(List<Track> trackList);

        void getSearchFailure(String messages);
    }

    interface Presenter extends BaseMvpPresenter<TrackMoreContract.View> {
        void loadMore(String type, int limit, int offset);
    }
}
