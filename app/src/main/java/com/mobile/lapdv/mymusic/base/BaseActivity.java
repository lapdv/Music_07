package com.mobile.lapdv.mymusic.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mobile.lapdv.mymusic.base.mvp.BaseView;

/**
 * Created by lap on 02/05/2018.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener,BaseView {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();
        initData();
    }

    protected abstract void initView();

    protected abstract void initData();

    public abstract int getLayoutId();

    public abstract int getContainerId();

    /**
     * open activity other
     */
    public void openActivity(Class<? extends Activity> pClass) {
        openActivity(pClass, null);
    }

    public void openActivity(Class<? extends Activity> pClass, boolean isFinish) {
        openActivity(pClass);
        if (isFinish) {
            finish();
        }
    }

    public void openActivity(Class<? extends Activity> pClass, Bundle bundle) {
        Intent intent = new Intent(this, pClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void gotoFragment(BaseFragment fragment, Bundle bundle) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragment.setArguments(bundle);
        fragmentTransaction.replace(getContainerId(), fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    public BaseFragment getFragmentCurrent() {
        return (BaseFragment) getSupportFragmentManager().findFragmentById(getContainerId());
    }

}
