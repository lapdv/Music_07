package com.mobile.lapdv.mymusic.screen.main;

import com.mobile.lapdv.mymusic.base.mvp.BaseMvpPresenter;
import com.mobile.lapdv.mymusic.base.mvp.BaseView;

/**
 * Created by lap on 10/05/2018.
 */

public interface MainContract {
    /**
     * init action view here
     */
    interface View extends BaseView {
    }

    /**
     * handle logic of main screen
     */
    interface Presenter extends BaseMvpPresenter<View> {

    }
}
