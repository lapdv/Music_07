package com.mobile.lapdv.mymusic.screen.main.presenter;

import com.mobile.lapdv.mymusic.base.mvp.BaseMvpPresenter;
import com.mobile.lapdv.mymusic.screen.main.view.MainView;

/**
 * Created by lap on 04/05/2018.
 */

public interface IMainPresenter extends BaseMvpPresenter<MainView> {
    void loadData();
}
