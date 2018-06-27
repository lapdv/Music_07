package com.mobile.lapdv.mymusic.screen.track.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mobile.lapdv.mymusic.R;
import com.mobile.lapdv.mymusic.base.adapter.BaseRecyclerAdapter;
import com.mobile.lapdv.mymusic.base.adapter.BaseViewHolder;
import com.mobile.lapdv.mymusic.callback.OnGetListDataListener;
import com.mobile.lapdv.mymusic.data.model.Track;
import com.mobile.lapdv.mymusic.utils.Constant;
import com.mobile.lapdv.mymusic.utils.EmptyUtils;
import com.mobile.lapdv.mymusic.utils.GlideUtils;
import com.mobile.lapdv.mymusic.widget.SquareImageView;

/**
 * Created by lap on 15/05/2018.
 */

public class TrackMoreAdapter extends BaseRecyclerAdapter<Track, TrackMoreAdapter.TrackMoreHolder> {

    public TrackMoreAdapter(Context context) {
        super(context);
    }

    private OnGetListDataListener<Track> mTrackOnRecyclerViewItemClick;

    public void setTrackOnItemClick(OnGetListDataListener<Track> trackOnRecyclerViewItemClick) {
        mTrackOnRecyclerViewItemClick = trackOnRecyclerViewItemClick;
    }

    @Override
    public TrackMoreHolder onCreateRecyclerViewHolder(ViewGroup parent, int viewType) {
        View view = getInflater().inflate(R.layout.item_track_more, parent, false);
        return new TrackMoreHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,final int position) {
        super.onBindViewHolder(holder, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTrackOnRecyclerViewItemClick != null) {
                    mTrackOnRecyclerViewItemClick.onItemClick(mData, position);
                }
            }
        });
    }

    public class TrackMoreHolder extends BaseViewHolder<Track> {
        private SquareImageView mImageTrack;
        private TextView mTitleTrack, mUserTrack;

        public TrackMoreHolder(@NonNull View itemView) {
            super(itemView);
            mImageTrack = itemView.findViewById(R.id.image_track);
            mTitleTrack = itemView.findViewById(R.id.text_track_title);
            mUserTrack = itemView.findViewById(R.id.text_track_user);
        }

        @Override
        public void binData(Track track, int position) {
            if (EmptyUtils.isNotEmpty(track)) {
                GlideUtils.loadImage(this.getContext(), mImageTrack, track.getAvatarUrl());
                mTitleTrack.setText(track.getTitle());
                mUserTrack.setText(track.getUsername());
            }
        }
    }
}
