package com.jebysun.recyclerviewwrapper;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by JebySun on 2017/10/9.
 * email:jebysun@126.com
 */

public class RecyclerViewAdapterWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM_TYPE_HEADER = -100;

    private RecyclerView.Adapter mAdapter;
    private int mHeaderResId;

    public RecyclerViewAdapterWrapper(RecyclerView.Adapter adapter) {
        mAdapter = adapter;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_HEADER) {
            View headerView = LayoutInflater.from(parent.getContext()).inflate(mHeaderResId, parent, false);
            return new HeaderViewHolder(headerView);
        }
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == ITEM_TYPE_HEADER) {
            return;
        }
        position = position - (mHeaderResId == 0 ? 0 : 1);
        mAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && mHeaderResId != 0) {
            return ITEM_TYPE_HEADER;
        }
        return mAdapter.getItemViewType(position);
    }


    @Override
    public int getItemCount() {
        return mAdapter.getItemCount() + (mHeaderResId == 0 ? 0 : 1);
    }

    /**
     * TODO
     * @param view
     */
    public void setHeaderView(View view) {

    }

    public void setHeaderView(int layoutResId) {
        mHeaderResId = layoutResId;
    }



    private class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }



}
