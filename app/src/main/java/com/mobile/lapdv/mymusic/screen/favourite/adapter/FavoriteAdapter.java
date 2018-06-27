package com.mobile.lapdv.mymusic.screen.favourite.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.lapdv.mymusic.R;
import com.mobile.lapdv.mymusic.base.adapter.BaseRecyclerAdapter;
import com.mobile.lapdv.mymusic.base.adapter.BaseViewHolder;
import com.mobile.lapdv.mymusic.callback.OnGetListDataListener;
import com.mobile.lapdv.mymusic.data.model.Track;
import com.mobile.lapdv.mymusic.data.source.FavoriteRepository;
import com.mobile.lapdv.mymusic.utils.EmptyUtils;
import com.mobile.lapdv.mymusic.utils.GlideUtils;

import java.util.List;

/**
 * Created by lap on 14/05/2018.
 */

public class FavoriteAdapter extends BaseRecyclerAdapter<Track, FavoriteAdapter.FavoriteHolder> {

    public OnFavoriteListener mOnFavoriteListener;
    private OnGetListDataListener<Track> mTrackOnRecyclerViewItemClick;

    public void setTrackOnItemClick(OnGetListDataListener<Track> trackOnRecyclerViewItemClick) {
        mTrackOnRecyclerViewItemClick = trackOnRecyclerViewItemClick;
    }

    public void setOnFavoriteListener(OnFavoriteListener onFavoriteListener) {
        mOnFavoriteListener = onFavoriteListener;
    }

    public FavoriteAdapter(Context context) {
        super(context);
    }

    @Override
    public FavoriteHolder onCreateRecyclerViewHolder(ViewGroup parent, int viewType) {
        View view = getInflater().inflate(R.layout.item_favorite, parent, false);
        return new FavoriteHolder(view, mOnFavoriteListener);
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

    public static class FavoriteHolder extends BaseViewHolder<Track>
            implements PopupMenu.OnMenuItemClickListener
            , View.OnClickListener {
        private PopupMenu mPopupMenu;
        private ImageView mImageTrack, mImageMore;
        private TextView mTitleTrack, mUserTrack;
        private Track mTrack;
        private OnFavoriteListener mOnFavoriteListener;

        public FavoriteHolder(@NonNull View itemView, OnFavoriteListener onFavoriteListener) {
            super(itemView);
            mImageTrack = itemView.findViewById(R.id.image_track);
            mImageMore = itemView.findViewById(R.id.image_more);
            mImageMore.setOnClickListener(this);
            mTitleTrack = itemView.findViewById(R.id.text_track_title);
            mUserTrack = itemView.findViewById(R.id.text_track_user);
            mPopupMenu = new PopupMenu(itemView.getContext(), mImageMore);
            mPopupMenu.inflate(R.menu.menu_favorite);
            mPopupMenu.setOnMenuItemClickListener(this);
            mOnFavoriteListener = onFavoriteListener;

        }

        @Override
        public void binData(Track track, int position) {
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
                    if (EmptyUtils.isNotEmpty(mTrack) && mOnFavoriteListener != null) {
                        mOnFavoriteListener.onDowload(mTrack);
                    }
                    break;
                case R.id.action_remove_favorite:
                    if (EmptyUtils.isNotEmpty(mTrack) && mOnFavoriteListener != null) {
                        mOnFavoriteListener.onRemoveFavorite(mTrack);
                    }
                    break;
            }
            return true;
        }
    }

    public interface OnFavoriteListener {
        void onRemoveFavorite(Track track);

        void onDowload(Track track);
    }
}
