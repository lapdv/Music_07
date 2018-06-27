package com.mobile.lapdv.mymusic.screen.search.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.mobile.lapdv.mymusic.R;
import com.mobile.lapdv.mymusic.base.adapter.BaseRecyclerAdapter;
import com.mobile.lapdv.mymusic.base.adapter.BaseViewHolder;
import com.mobile.lapdv.mymusic.callback.OnGetListDataListener;
import com.mobile.lapdv.mymusic.data.model.Track;
import com.mobile.lapdv.mymusic.utils.EmptyUtils;
import com.mobile.lapdv.mymusic.utils.GlideUtils;

/**
 * Created by lap on 15/05/2018.
 */

public class SearchAdapter extends BaseRecyclerAdapter<Track, SearchAdapter.SearchHolder> {

    private OnGetListDataListener<Track> mTrackOnRecyclerViewItemClick;
    private OnSearchListener mOnSearchListener;

    public void setOnSearchListener(OnSearchListener onSearchListener) {
        mOnSearchListener = onSearchListener;
    }

    public void setTrackOnItemClick(OnGetListDataListener<Track> trackOnRecyclerViewItemClick) {
        mTrackOnRecyclerViewItemClick = trackOnRecyclerViewItemClick;
    }

    public SearchAdapter(Context context) {
        super(context);
    }

    @Override
    public SearchHolder onCreateRecyclerViewHolder(ViewGroup parent, int viewType) {
        View view = getInflater().inflate(R.layout.item_search, parent, false);
        return new SearchHolder(view, mOnSearchListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
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

    public static class SearchHolder extends BaseViewHolder<Track>
            implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        private ImageView mImageTrack, mImageMore;
        private TextView mTitleTrack, mUserTrack;
        private PopupMenu mPopupMenu;
        private Track mTrack;
        private OnSearchListener mOnSearchListener;

        public SearchHolder(@NonNull View itemView, OnSearchListener listener) {
            super(itemView);
            mOnSearchListener = listener;
            mImageTrack = itemView.findViewById(R.id.image_track);
            mImageMore = itemView.findViewById(R.id.image_more);
            mImageMore.setOnClickListener(this);
            mTitleTrack = itemView.findViewById(R.id.text_track_title);
            mUserTrack = itemView.findViewById(R.id.text_track_user);
            mPopupMenu = new PopupMenu(itemView.getContext(), mImageMore);
            mPopupMenu.inflate(R.menu.custom_menu);
            mPopupMenu.setOnMenuItemClickListener(this);
        }

        @Override
        public void binData(final Track track, int position) {
            if (EmptyUtils.isNotEmpty(track)) {
                mTrack = track;
                GlideUtils.loadImage(this.getContext(), mImageTrack, track.getAvatarUrl());
                mTitleTrack.setText(track.getTitle());
                mUserTrack.setText(track.getUsername());
            }
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.image_more && mPopupMenu != null) {
                mPopupMenu.show();
            }
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_dowload:
                    if (EmptyUtils.isNotEmpty(mTrack) && mOnSearchListener != null) {
                        mOnSearchListener.onDowload(mTrack);
                    }
                    break;
                case R.id.action_favorite:
                    if (EmptyUtils.isNotEmpty(mTrack) && mOnSearchListener != null) {
                        mOnSearchListener.onAddFavorite(mTrack);
                    }
            }
            return true;
        }
    }

    public interface OnSearchListener {
        void onAddFavorite(Track track);

        void onDowload(Track track);
    }
}
