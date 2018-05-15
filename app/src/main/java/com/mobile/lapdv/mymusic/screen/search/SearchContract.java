package com.mobile.lapdv.mymusic.screen.search;

import com.mobile.lapdv.mymusic.base.mvp.BaseMvpPresenter;
import com.mobile.lapdv.mymusic.base.mvp.BaseView;
import com.mobile.lapdv.mymusic.data.model.Track;

import java.util.List;

/**
 * Created by lap on 15/05/2018.
 */

public interface SearchContract {

    interface View extends BaseView {
        void getSearchSuccess(List<Track> trackList);

        void getSearchFailure(String messages);
    }

    interface Presenter extends BaseMvpPresenter<SearchContract.View> {
        void getSearch(String keyWord);

        void addFavorite(Track track);
    }
}
