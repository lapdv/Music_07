package com.mobile.lapdv.mymusic.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.lapdv.mymusic.base.mvp.BaseView;

/**
 * Created by lap on 02/05/2018.
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener, BaseView {

    private View mFragmentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (null == mFragmentView) {
            mFragmentView = inflater.inflate(getLayoutId(), container, false);
        }
        initView();
        return mFragmentView;
    }

    protected abstract void initView();

    protected abstract int getLayoutId();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    protected abstract void initData();

    public BaseActivity getBaseActivity() {
        if (getActivity() instanceof BaseActivity) {
            return (BaseActivity) getActivity();
        }
        return null;
    }

    public void replaceFragment(BaseFragment fragment, Bundle mBundle) {
        FragmentTransaction fragmentTransaction =
                getBaseActivity().getSupportFragmentManager().beginTransaction();
        fragment.setArguments(mBundle);
        fragmentTransaction.replace(getContainerId(), fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public int getContainerId() {
        return 0;
    }

    @Override
    public void onDestroy() {
        if (null != mFragmentView) {
            ViewGroup viewGroup = (ViewGroup) mFragmentView.getParent();
            viewGroup.removeView(viewGroup);
        }
        super.onDestroy();
    }

}
