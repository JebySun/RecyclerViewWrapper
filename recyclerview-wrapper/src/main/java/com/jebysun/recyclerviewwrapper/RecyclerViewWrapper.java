package com.jebysun.recyclerviewwrapper;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.jebysun.recyclerviewwrapper.util.AndroidUtil;
import com.jebysun.recyclerviewwrapper.widget.ContainerFrameLayout;
import com.jebysun.recyclerviewwrapper.widget.LoadingView;
import com.jebysun.recyclerviewwrapper.widget.RefreshView;

/**
 * RecyclerView装饰增强
 *
 * 功能列表：
 * 1.下拉刷新；
 * 2.上拉加载更多；
 * 3.自定义下拉刷新视图；
 * 4.自定义上拉加载视图；
 * 5.添加HeaderView;
 * 6.设置空视图；
 * 7.子项事件处理；
 * 8.子项内子控件事件处理；
 *
 * Created by JebySun on 2017/10/9.
 * email:jebysun@126.com
 */

public class RecyclerViewWrapper {

    private Context mContext;
    private ContainerFrameLayout mContainerLayout;
    private RecyclerView mRecyclerView;
    private View mRefreshView;
    private View mLoadingView;
    private View mEmptyView;
    private RefreshProcessCallback mRefreshCallback;
    private LoadingProcessCallback mLoadingCallback;

    private RecyclerView.Adapter mAdapter;
    private RecyclerViewAdapterWrapper mAdapterWrapper;

    public RecyclerViewWrapper(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mContext = mRecyclerView.getContext();
        mAdapter = mRecyclerView.getAdapter();
        mAdapterWrapper = new RecyclerViewAdapterWrapper(mAdapter);
        mRecyclerView.setAdapter(mAdapterWrapper);

        layout();
    }

    private void layout() {
        mContainerLayout = new ContainerFrameLayout(mContext);
        ViewGroup parentView = (ViewGroup) mRecyclerView.getParent();
        parentView.removeView(mRecyclerView);
        parentView.addView(mContainerLayout, mRecyclerView.getLayoutParams());

        // TODO 可使用自定义替换 下拉刷新视图
        mRefreshView = new RefreshView(mContext);
        mRefreshView.setBackgroundColor(Color.GREEN);
        ((RefreshView) mRefreshView).setText("下拉刷新");
        ((RefreshView) mRefreshView).setGravity(Gravity.CENTER);
        int refreshViewHeight = AndroidUtil.dp2px(mContext, 60);
        FrameLayout.LayoutParams refreshLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, refreshViewHeight);
        refreshLayoutParams.setMargins(0, -refreshViewHeight, 0, 0);
        mContainerLayout.addView(mRefreshView, refreshLayoutParams);

        // RecyclerView
        FrameLayout.LayoutParams recyclerLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mContainerLayout.addView(mRecyclerView, recyclerLayoutParams);

        // TODO 可使用自定义替换 上拉加载更多视图
        mLoadingView = new LoadingView(mContext);
        mLoadingView.setBackgroundColor(Color.GREEN);
        ((LoadingView) mLoadingView).setText("正在加载");
        ((LoadingView) mLoadingView).setGravity(Gravity.CENTER);
        int loadingViewHeight = AndroidUtil.dp2px(mContext, 60);
        FrameLayout.LayoutParams loadingLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, loadingViewHeight);
        loadingLayoutParams.gravity = Gravity.BOTTOM;
        loadingLayoutParams.setMargins(0, 0, 0, -loadingViewHeight);
        mContainerLayout.addView(mLoadingView, loadingLayoutParams);

        mRefreshCallback = (RefreshProcessCallback) mRefreshView;
        mLoadingCallback = (LoadingProcessCallback) mLoadingView;
    }

    /**
     * TODO
     * 设置自定义下拉刷新视图
     * @param callback
     */
    public void setRefreshLayout(RefreshProcessCallback callback) {
        mRefreshCallback = callback;
    }

    /**
     * TODO
     * 设置自定义加载更多视图
     * @param callback
     */
    public void setLoadingLayout(LoadingProcessCallback callback) {
        mLoadingCallback = callback;
    }

    /**
     * 添加顶部视图
     * @param view
     */
    public void setHeaderView(View view) {
        mAdapterWrapper.setHeaderView(view);
    }

    public void setHeaderView(int layoutResId) {
        mAdapterWrapper.setHeaderView(layoutResId);
    }



    /**
     * 设置列表无数据时显示的视图
     * @param view
     */
    public void setEmptyView(View view) {
        mEmptyView = view;
        if (mEmptyView.getId() == View.NO_ID) {
            mEmptyView.setId(R.id.empty_view_id);
        }
    }

    public void setEmptyView(int layoutResId) {
        View view = LayoutInflater.from(mContext).inflate(layoutResId, mContainerLayout, false);
        setEmptyView(view);
    }

    /**
     * 下拉刷新监听
     * @param listener
     */
    public void setOnRefreshListener(OnRefreshListener listener) {
//        mOnRefreshListener = listener;
        mContainerLayout.setOnRefreshListener(listener);
    }

    /**
     * 上拉加载监听
     * @param listener
     */
    public void setOnLoadingListener(OnLoadingListener listener) {
//        mOnLoadListener = listener;
        mContainerLayout.setOnLoadingListener(listener);
    }


    /**
     * 下拉刷新完成
     */
    public void refreshComplete() {
        mRefreshCallback.onComplete();

        mRefreshView.postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO 添加动画
                AndroidUtil.setMargins(mRefreshView, 0, -mRefreshView.getHeight(), 0, 0);
                AndroidUtil.setMargins(mRecyclerView, 0, 0, 0, 0);
                if (mEmptyView != null) {
                    AndroidUtil.setMargins(mEmptyView, 0, 0, 0, 0);
                }
            }
        }, 600);
    }

    /**
     * 加载更多完成
     */
    public void loadingComplete() {
        mLoadingCallback.onComplete();

        mLoadingView.postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO 添加动画
                AndroidUtil.setMargins(mLoadingView, 0, 0, 0, -mLoadingView.getHeight());
                AndroidUtil.setMargins(mRecyclerView, 0, 0, 0, 0);
            }
        }, 600);

    }





    public void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();

        if (mAdapter.getItemCount() == 0) {
            showEmptyView();
        } else {
            showRecyclerView();
        }
    }

    private void showEmptyView() {
        if (mEmptyView != null && mContainerLayout.findViewById(R.id.empty_view_id) == null) {
            mRecyclerView.setVisibility(View.GONE);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mContainerLayout.addView(mEmptyView, layoutParams);
        }
    }

    private void showRecyclerView() {
        if (mEmptyView != null && mContainerLayout.findViewById(R.id.empty_view_id) != null) {
            mRecyclerView.setVisibility(View.VISIBLE);
            mContainerLayout.removeView(mEmptyView);
        }
    }




}
