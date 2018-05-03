package com.mobile.lapdv.mymusic.base.mvp;

/**
 * Created by lap on 02/05/2018.
 */

public interface BaseMvpPresenter<V extends BaseView> {

    V getView();

    void onAttach(V mvpView);

    void onDettach();

    void onStart();

    boolean isViewAttached();
}
