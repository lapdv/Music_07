package com.mobile.lapdv.mymusic.screen.playmusic;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.lapdv.mymusic.R;
import com.mobile.lapdv.mymusic.base.BaseActivity;
import com.mobile.lapdv.mymusic.data.model.Genre;
import com.mobile.lapdv.mymusic.data.model.Track;
import com.mobile.lapdv.mymusic.utils.EmptyUtils;
import com.mobile.lapdv.mymusic.utils.GlideUtils;
import com.mobile.lapdv.mymusic.widget.ToolBarApp;

/**
 * Created by lap on 10/05/2018.
 */

public class PlayMusicActivity extends BaseActivity {
    private ToolBarApp mToolBar;
    private ImageView mImageTrack;
    private TextView mTextTitleTrack;

    @Override
    protected void initView() {
        mToolBar = findViewById(R.id.tool_bar);
        mImageTrack = findViewById(R.id.image_track);
        mTextTitleTrack = findViewById(R.id.text_tilte_track);
        initToolBar();
    }

    private void initToolBar() {
        mToolBar.setIconToolBarLeft(getResources().getDrawable(R.drawable.ic_back));
        mToolBar.setIconToolBarRight(getResources().getDrawable(R.drawable.ic_more));
        mToolBar.setOnClickItemIconToolBar(new ToolBarApp.OnClickItemToolBar() {
            @Override
            public void onItemRight() {
                //TODO
            }

            @Override
            public void onItemLeft() {
                onBackPressed();
            }
        });
    }

    @Override
    protected void initData() {
        //TODO get data track from service & bindview bindViewData(track);
    }

    private void bindViewData(Track track) {
        if (EmptyUtils.isNotEmpty(track)) {
            GlideUtils.loadImage(track.getAvatarUrl(), mImageTrack);
            mTextTitleTrack.setText(!TextUtils.isEmpty(track.getTitle())
                    ? track.getTitle() : "");
            mToolBar.setTextCenterToolbar(!TextUtils.isEmpty(track.getUsername())
                    ? track.getUsername() : "");
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_play_music;
    }

    @Override
    public int getContainerId() {
        return 0;
    }

    @Override
    public void onClick(View view) {
    }
}
