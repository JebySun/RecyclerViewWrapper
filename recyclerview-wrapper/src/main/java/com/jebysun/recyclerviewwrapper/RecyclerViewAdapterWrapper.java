package com.jebysun.recyclerviewwrapper;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.zip.Inflater;

/**
 * Created by JebySun on 2017/10/9.
 * email:jebysun@126.com
 */

public class RecyclerViewAdapterWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM_TYPE_HEADER = -1;

    private RecyclerView.Adapter mAdapter;
    private View mHeaderView;
    private int mLayoutResId;

    public RecyclerViewAdapterWrapper(RecyclerView.Adapter adapter) {
        mAdapter = adapter;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_HEADER) {
            if (mLayoutResId != 0) {
                mHeaderView = LayoutInflater.from(parent.getContext()).inflate(mLayoutResId, parent, false);
            }
            return new HeaderViewHolder(mHeaderView);
        }
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == ITEM_TYPE_HEADER) {
            return;
        }
        position = position - ((mHeaderView == null || mLayoutResId == 0) ? 0 : 1);
        if (position < mAdapter.getItemCount()) {
            mAdapter.onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && (mHeaderView != null || mLayoutResId != 0)) {
            return ITEM_TYPE_HEADER;
        }
        return mAdapter.getItemViewType(position);
    }


    @Override
    public int getItemCount() {
        return mAdapter.getItemCount() + ((mHeaderView == null || mLayoutResId == 0) ? 0 : 1);
    }

    public void setHeaderView(View view) {
        mHeaderView = view;
        mLayoutResId = 0;
    }

    public void setHeaderView(int layoutResId) {
        mLayoutResId = layoutResId;
        mHeaderView = null;
    }



    private class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }



}
