package com.mobile.lapdv.mymusic.screen.home;

import com.mobile.lapdv.mymusic.base.mvp.BaseMvpPresenter;
import com.mobile.lapdv.mymusic.base.mvp.BaseView;
import com.mobile.lapdv.mymusic.data.model.Genre;

import java.util.List;

/**
 * Created by lap on 09/05/2018.
 */

public interface HomeContract {
    /**
     * All the action of home view
     */
    interface View extends BaseView{
        void getSongsSuccess(List<Genre> genresModels);

        void getSongsFailure(String messages);
    }

    /**
     * All the logic of Home screen
     */
    interface Presenter extends BaseMvpPresenter<View> {
        void getSongs();
    }
}
