package com.mobile.lapdv.mymusic.screen.main;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.mobile.lapdv.mymusic.R;
import com.mobile.lapdv.mymusic.base.BaseActivity;
import com.mobile.lapdv.mymusic.screen.main.presenter.MainContract;
import com.mobile.lapdv.mymusic.screen.home.HomeFragment;
import com.mobile.lapdv.mymusic.screen.main.view.MainView;
import com.mobile.lapdv.mymusic.widget.ToolBarApp;

public class MainActivity extends BaseActivity implements MainView {

    private static final float END_SCALE = 0.7f;
    private MainContract mMainContract;
    private ToolBarApp mToolBar;
    private DrawerLayout mDrawerLayout;
    private FrameLayout mFrameLayoutContent;
    private NavigationView mNavigationView;
    private RelativeLayout mRelativeLayoutContent;

    @Override
    protected void initView() {
        mMainContract = new MainContract();
        mMainContract.onAttach(this);
        mFrameLayoutContent = findViewById(R.id.frame_content);
        mToolBar = findViewById(R.id.tool_bar);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);
        mRelativeLayoutContent = findViewById(R.id.rl_content);
        initToolBar();
        initNavDrawer();
    }

    public void initToolBar() {
        mToolBar.setIconToolBarLeft(getResources().getDrawable(R.drawable.ic_menu));
        mToolBar.setIconToolBarRight(getResources().getDrawable(R.drawable.ic_search));
        mToolBar.setOnClickItemIconToolBar(new ToolBarApp.OnClickItemToolBar() {
            @Override
            public void onItemRight() {
                //TODO open search activity
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
                        // TODO open favorite screen
                        break;
                    case R.id.nav_sub_music_offline:
                        // TODO open music offline screen
                        break;
                    case R.id.nav_sub_about:
                        // TODO open about
                        break;
                    case R.id.nav_sub_rate:
                        // TODO open rate
                        break;
                    default:
                        item.setChecked(false);
                        break;
                }
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        if (mMainContract.isViewAttached()) {
            //TODO init data
            gotoFragment(new HomeFragment(), null);
        }
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
    }
}
