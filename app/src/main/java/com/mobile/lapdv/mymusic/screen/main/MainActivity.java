package com.mobile.lapdv.mymusic.screen.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobile.lapdv.mymusic.R;
import com.mobile.lapdv.mymusic.base.BasePlayerActivity;
import com.mobile.lapdv.mymusic.data.model.Track;
import com.mobile.lapdv.mymusic.screen.favourite.FavoriteFragment;
import com.mobile.lapdv.mymusic.screen.home.HomeFragment;
import com.mobile.lapdv.mymusic.screen.playmusic.PlayMusicActivity;
import com.mobile.lapdv.mymusic.screen.rate.RateDialogFragment;
import com.mobile.lapdv.mymusic.screen.search.SearchActivity;
import com.mobile.lapdv.mymusic.service.PlayMusicService;
import com.mobile.lapdv.mymusic.utils.EmptyUtils;
import com.mobile.lapdv.mymusic.utils.GlideUtils;
import com.mobile.lapdv.mymusic.utils.PermisisonUtils;
import com.mobile.lapdv.mymusic.utils.music.AudioPlayer;
import com.mobile.lapdv.mymusic.utils.music.OnEventsPlayerListener;
import com.mobile.lapdv.mymusic.widget.MusicVisualizer;
import com.mobile.lapdv.mymusic.widget.ToolBarApp;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends BasePlayerActivity
        implements MainContract.View, OnEventsPlayerListener {
    private static final int PERMISSION_WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 200;
    private static final float END_SCALE = 0.7f;
    private MainContract.Presenter mMainPresenter;
    private ToolBarApp mToolBar;
    private DrawerLayout mDrawerLayout;
    private FrameLayout mFrameLayoutContent;
    private ImageView mImageTrack, mImageNext, mImagePlay, mImagePrevios;
    private TextView mTextTitle, mTextUser;
    private MusicVisualizer mMusicVisualizer;
    private NavigationView mNavigationView;
    private RelativeLayout mRelativeLayoutContent;
    private View mLayoutBottom;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void initView() {
        mMainPresenter = new MainPresenter();
        mMainPresenter.onAttach(this);
        mFrameLayoutContent = findViewById(R.id.frame_content);
        mToolBar = findViewById(R.id.tool_bar);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);
        mRelativeLayoutContent = findViewById(R.id.rl_content);
        mLayoutBottom = findViewById(R.id.layout_bottom_control);
        mLayoutBottom.setOnClickListener(this);
        mImageTrack = mLayoutBottom.findViewById(R.id.image_mini_track);
        mImagePlay = mLayoutBottom.findViewById(R.id.image_play_pause);
        mImagePlay.setOnClickListener(this);
        mImageNext = mLayoutBottom.findViewById(R.id.image_action_next);
        mImageNext.setOnClickListener(this);
        mImagePrevios = mLayoutBottom.findViewById(R.id.image_previous);
        mImagePrevios.setOnClickListener(this);
        mTextUser = mLayoutBottom.findViewById(R.id.text_track_user);
        mTextTitle = mLayoutBottom.findViewById(R.id.text_tilte_track);
        mMusicVisualizer = mLayoutBottom.findViewById(R.id.visualizer);
        initToolBar();
        initNavDrawer();
    }

    public void initToolBar() {
        mToolBar.setIconToolBarLeft(getResources().getDrawable(R.drawable.ic_menu));
        mToolBar.setIconToolBarRight(getResources().getDrawable(R.drawable.ic_search));
        mToolBar.setOnClickItemIconToolBar(new ToolBarApp.OnClickItemToolBar() {
            @Override
            public void onItemRight() {
                openActivity(SearchActivity.class);
            }

            @Override
            public void onItemLeft() {
                if (mDrawerLayout.isDrawerOpen(mNavigationView)) {
                    mDrawerLayout.closeDrawer(mNavigationView);
                } else {
                    mDrawerLayout.openDrawer(mNavigationView);
                }
            }
        });
    }

    public void initNavDrawer() {
        mDrawerLayout.setScrimColor(Color.TRANSPARENT);
        mDrawerLayout.addDrawerListener(
                new DrawerLayout.SimpleDrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        translationDrawer(drawerView, slideOffset);
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                    }
                }
        );
        handlerNavigationSelected();
    }

    private void translationDrawer(View drawerView, float slideOffset) {
        float diffScaledOffset = slideOffset * (1 - END_SCALE);
        float offsetScale = 1 - diffScaledOffset;
        mRelativeLayoutContent.setScaleX(offsetScale);
        mRelativeLayoutContent.setScaleY(offsetScale);
        float xOffset = drawerView.getWidth() * slideOffset;
        float xOffsetDiff =
                mRelativeLayoutContent.getWidth()
                        * diffScaledOffset / 2;
        final float xTranslation = xOffset - xOffsetDiff;
        mRelativeLayoutContent.setTranslationX(xTranslation);
    }

    private void handlerNavigationSelected() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView
                .OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_sub_home:
                        gotoFragment(new HomeFragment(), null);
                        break;
                    case R.id.nav_sub_favorite:
                        gotoFragment(new FavoriteFragment(), null);
                        break;
                    case R.id.nav_sub_music_offline:
                        showDialogAbout(getString(R.string.string_title_sorry)
                                , getString(R.string.string_title_message_feature));
                        break;
                    case R.id.nav_sub_about:
                        showDialogAbout(getString(R.string.string_about),
                                getString(R.string.string_message_about));
                        break;
                    case R.id.nav_sub_rate:
                        RateDialogFragment.show(getFragmentManager());
                        break;
                    default:
                        item.setChecked(false);
                        break;
                }
                mToolBar.setTextLeftToolbar(item.getTitle().toString());
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    private void showDialogAbout(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setIcon(R.drawable.ic_app)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    @Override
    protected void initData() {
        AudioPlayer.get().addOnPlayEventListener(this);
        mToolBar.setTextLeftToolbar(getString(R.string.string_title_home));
        if (!PermisisonUtils.checkPermission(this)) {
            PermisisonUtils.requestPermission(this);
        } else {
            gotoFragment(new HomeFragment(), null);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_WRITE_EXTERNAL_STORAGE_REQUEST_CODE:
                if (PermisisonUtils.checkPermission(MainActivity.this)) {
                    gotoFragment(new HomeFragment(), null);
                } else {
                    showMessageOKCancel(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                PermisisonUtils.requestPermission(MainActivity.this);
                                dialog.dismiss();
                            }
                        }
                    });
                }
                break;
        }
    }

    private void showMessageOKCancel(DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(R.string.string_message_access_permission)
                .setPositiveButton(R.string.string_btn_ok, okListener)
                .setCancelable(false)
                .create()
                .show();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public int getContainerId() {
        return R.id.frame_content;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_bottom_control:
                openActivity(PlayMusicActivity.class);
                break;
            case R.id.image_action_next:
                next();
                break;
            case R.id.image_previous:
                prev();
                break;
            case R.id.image_play_pause:
                playpause();
                mImagePlay.setImageResource(!AudioPlayer.get().isPausing() ?
                        R.drawable.ic_pause : R.drawable.ic_play);
                break;
        }
    }

    @Override
    protected void updateView(Track song) {
        if (EmptyUtils.isNotEmpty(song)) {
            mLayoutBottom.setVisibility(View.VISIBLE);
            mMusicVisualizer.setVisibility(!AudioPlayer.get().isPausing() ? View.VISIBLE : View.INVISIBLE);
            mMusicVisualizer.setColor(getResources().getColor(R.color.color_white));
            GlideUtils.loadImage(this, mImageTrack, song.getAvatarUrl());
            mTextTitle.setText(song.getTitle());
            mTextUser.setText(song.getUsername());
            mImagePlay.setImageResource(!AudioPlayer.get().isPausing() ?
                    R.drawable.ic_pause : R.drawable.ic_play);
        }
        super.updateView(song);
    }
}
