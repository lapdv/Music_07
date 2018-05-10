package com.mobile.lapdv.mymusic.widget;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.mobile.lapdv.mymusic.R;
import com.mobile.lapdv.mymusic.callback.OnRecyclerViewItemClick;
import com.mobile.lapdv.mymusic.data.model.Track;
import com.mobile.lapdv.mymusic.screen.home.adapter.TrackAdapter;
import com.mobile.lapdv.mymusic.utils.EmptyUtils;

import java.util.List;

/**
 * Created by lap on 07/05/2018.
 */

public class CustomTrackRecyclerView extends ConstraintLayout {

    private RecyclerView mRecyclerViewSong;
    private OnClickItemRecyclerView mOnClickItemRecyclerView;

    public void setOnClickItemRecyclerView(OnClickItemRecyclerView onClickItemRecyclerView) {
        mOnClickItemRecyclerView = onClickItemRecyclerView;
    }

    public CustomTrackRecyclerView(Context context) {
        super(context);
    }

    public CustomTrackRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomTrackRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View layout = LayoutInflater.from(context)
                .inflate(R.layout.custom_track_recyclerview, this);
        mRecyclerViewSong = layout.findViewById(R.id.recycler_track);
        mRecyclerViewSong.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
    }

    public void setAdapter(List<Track> mData) {
        if (EmptyUtils.isNotEmpty(mData)) {
            TrackAdapter trackAdapter = new TrackAdapter(getContext());
            trackAdapter.setData(mData);
            mRecyclerViewSong.setAdapter(trackAdapter);
            trackAdapter.setOnRecyclerViewItemClick(new OnRecyclerViewItemClick<Track>() {
                @Override
                public void onItemClick(Track trackModel, int position) {
                    if (mOnClickItemRecyclerView != null) {
                        mOnClickItemRecyclerView.onItemClick(trackModel, position);
                    }
                }
            });
        }
    }

    interface OnClickItemRecyclerView<T> {
        void onItemClick(T t, int position);
    }
}
