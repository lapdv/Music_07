package com.mobile.lapdv.mymusic.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mobile.lapdv.mymusic.base.mvp.BaseView;
import com.mobile.lapdv.mymusic.data.model.Track;
import com.mobile.lapdv.mymusic.screen.playmusic.PlayMusicActivity;
import com.mobile.lapdv.mymusic.screen.track.TrackMoreActivity;
import com.mobile.lapdv.mymusic.service.Actions;
import com.mobile.lapdv.mymusic.service.PlayMusicService;
import com.mobile.lapdv.mymusic.utils.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lap on 02/05/2018.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener, BaseView {
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

    public void gotoPlayMusicActivity(List<Track> list, int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Track.class.getName(),
                (ArrayList<? extends Parcelable>) list);
        bundle.putInt(Constant.POSITION, position);
        Intent intent = new Intent(this, PlayMusicService.class);
        intent.setAction(Actions.ACTION_START_FORE_GROUND);
        intent.putExtras(bundle);
        startService(intent);
        openActivity(PlayMusicActivity.class);
    }
}
