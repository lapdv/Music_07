package com.mobile.lapdv.mymusic.base.mvp;

/**
 * Created by lap on 02/05/2018.
 */

public class BasePresenter<V extends BaseView> implements BaseMvpPresenter<V> {

    private V mMvpView;

    @Override
    public V getView() {
        return mMvpView;
    }

    @Override
    public void onAttach(V mvpView) {
        this.mMvpView = mvpView;
    }

    @Override
    public void onDettach() {
        if (null != mMvpView) {
            mMvpView = null;
        }
    }

    @Override
    public void onStart() {

    }

    @Override
    public boolean isViewAttached() {
        return mMvpView != null;
    }
}
