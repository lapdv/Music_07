package com.mobile.lapdv.mymusic.base.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.mobile.lapdv.mymusic.R;
import com.mobile.lapdv.mymusic.callback.OnRecyclerViewItemClick;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lap on 02/05/2018.
 */

public abstract class BaseRecyclerAdapter<T, VH extends BaseViewHolder>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    protected List<T> mData = new ArrayList<>();
    private VH mHolder;
    private OnRecyclerViewItemClick<T> mOnRecyclerViewItemClick;
    private int VIEW_TYPE_ITTEM = 0;
    private int VIEW_TYPE_PROGRESS = 1;

    public void setOnRecyclerViewItemClick(OnRecyclerViewItemClick<T> onRecyclerViewItemClick) {
        this.mOnRecyclerViewItemClick = onRecyclerViewItemClick;
    }

    public BaseRecyclerAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITTEM) {
            return onCreateRecyclerViewHolder(parent, viewType);
        } else if (viewType == VIEW_TYPE_PROGRESS) {
            View view = getInflater().inflate(R.layout.item_loadding, parent, false);
            return new LoadMoreHolder(view);
        }
        return onCreateRecyclerViewHolder(parent, viewType);
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position) != null ? VIEW_TYPE_ITTEM : VIEW_TYPE_PROGRESS;
    }

    /**
     * Functionality of onCreateViewHolder has been moved here. User should override this when using
     * this class
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    public abstract VH onCreateRecyclerViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        if (holder == null) return;
        if (holder instanceof BaseViewHolder) {
            this.mHolder = (VH) holder;
            if (mData != null && mData.size() > position && mData.get(position) != null) {
                this.mHolder.binData(mData.get(position), position);
            }
            this.mHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnRecyclerViewItemClick != null) {
                        int position = holder.getAdapterPosition();
                        mOnRecyclerViewItemClick.onItemClick(mData.get(position), position);
                    }
                }
            });
        }
    }

    protected LayoutInflater getInflater() {
        return LayoutInflater.from(mContext);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(List<T> items) {
        mData.clear();
        if (items != null) {
            mData.addAll(items);
        }
        notifyDataSetChanged();
    }

    public void clearData() {
        mData.clear();
        notifyDataSetChanged();
    }

    public void addItemsAtFront(List<T> items) {
        if (items == null) {
            return;
        }
        mData.addAll(mData.size(), items);
        notifyItemRangeChanged(mData.size(), mData.size() + items.size());
    }

    public void addItems(List<T> items, boolean isRefresh) {
        if (items == null) {
            return;
        }

        if (isRefresh && mData != null) mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(T item) {
        if (item == null) {
            return;
        }
        mData.add(item);
        notifyDataSetChanged();
    }

    public void addItem(T item, int position) {
        if (item == null) {
            return;
        }
        mData.add(position, item);
        notifyDataSetChanged();
    }

    public void deleteItem(T item) {
        if (item == null) {
            return;
        }
        mData.remove(item);
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return mData;
    }

    public T getDataItem(int position) {
        if (mData == null || mData.isEmpty()) {
            return null;
        }
        return mData.get(position);
    }

    public void removeItemLoadMore(T t) {
        int index = mData.indexOf(t);
        if (index >= 0 && index < mData.size()) {
            mData.remove(index);
        }
        notifyItemRemoved(index);
    }

    public void addItemLoaMore(T item) {
        mData.add(item);
        notifyItemInserted(mData.size() - 1);
    }

    public class LoadMoreHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        public LoadMoreHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progress_loadding);
        }
    }
}
