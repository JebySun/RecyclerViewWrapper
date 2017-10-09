package com.jebysun.recyclerviewwrapper;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    private LinearLayout mLinearLayout;
    private FrameLayout mFrameLayout;
    private RecyclerView mRecyclerView;
    private View mEmptyView;
    private OnRefreshListener mOnRefreshListener;
    private OnLoadListener mOnLoadListener;
    private RefreshProgressCallback mRefreshProgressCallback;
    private LoadProgressCallback mLoadProgressCallback;

    private RecyclerView.Adapter mAdapter;
    private RecyclerViewAdapterWrapper mAdapterWrapper;

    private boolean recyclerviewAdded = true;

    public RecyclerViewWrapper(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mContext = mRecyclerView.getContext();
        mAdapter = mRecyclerView.getAdapter();
        mAdapterWrapper = new RecyclerViewAdapterWrapper(mAdapter);
        mRecyclerView.setAdapter(mAdapterWrapper);
        layout();

        //TODO 下拉刷新，这里不推荐设置OnTouchListener，考虑从上级布局拦截实现。
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.e("onTouch:"+mRecyclerView.getScrollState(), event.getY()+"-"+event.getRawY());
                        break;
                    case MotionEvent.ACTION_UP:
                }
                return false;
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.e("onScrolled:"+recyclerView.getScrollState(), dy+"");
            }
        });
    }

    private void layout() {
        mLinearLayout = new LinearLayout(mContext);
        mLinearLayout.setOrientation(LinearLayout.VERTICAL);
        ViewGroup parentView = (ViewGroup) mRecyclerView.getParent();
        parentView.removeView(mRecyclerView);
        parentView.addView(mLinearLayout, mRecyclerView.getLayoutParams());

        // 下拉刷新视图
        TextView tvRefresh = new TextView(mContext);
        tvRefresh.setBackgroundColor(Color.GREEN);
//        tvRefresh.setVisibility(View.GONE);
        tvRefresh.setPadding(50, 50, 50, 50);
        tvRefresh.setText("下拉刷新");
        tvRefresh.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams headerLayoutParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLinearLayout.addView(tvRefresh, headerLayoutParam);

        //RecyclerView容器
        mFrameLayout = new FrameLayout(mContext);
        LinearLayout.LayoutParams frameLayoutParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        frameLayoutParam.weight = 10.0F;
        mLinearLayout.addView(mFrameLayout, frameLayoutParam);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mFrameLayout.addView(mRecyclerView, layoutParams);

        // 上拉加载视图
        TextView tvLoadMore = new TextView(mContext);
        tvLoadMore.setBackgroundColor(Color.BLUE);
        tvLoadMore.setVisibility(View.GONE);
        tvLoadMore.setPadding(50, 50, 50, 50);
        tvLoadMore.setText("上拉加载");
        tvLoadMore.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams loadmoreLayoutParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLinearLayout.addView(tvLoadMore, loadmoreLayoutParam);
    }

    /**
     * 设置自定义下拉刷新视图
     * @param callback
     */
    public void setRefreshLayout(RefreshProgressCallback callback) {
        mRefreshProgressCallback = callback;
    }

    /**
     * 设置自定义加载更多视图
     * @param callback
     */
    public void setLoadLayout(LoadProgressCallback callback) {
        mLoadProgressCallback = callback;
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
    }

    public void setEmptyView(int layoutResId) {

    }

    /**
     * 下拉刷新
     * @param listener
     */
    public void setOnRefreshListener(OnRefreshListener listener) {
        mOnRefreshListener = listener;
    }

    /**
     * 上拉加载更多
     * @param listener
     */
    public void setOnLoadListener(OnLoadListener listener) {
        mOnLoadListener = listener;
    }



    public void notifyDataSetChanged() {
        if (mAdapter.getItemCount() == 0) {
            showEmptyView();
        } else {
            showRecyclerView();
        }
        mAdapter.notifyDataSetChanged();
    }

    private void showEmptyView() {
        if (recyclerviewAdded) {
            recyclerviewAdded = false;
            mFrameLayout.removeView(mRecyclerView);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mFrameLayout.addView(mEmptyView, layoutParams);
        }
    }

    private void showRecyclerView() {
        if (!recyclerviewAdded) {
            recyclerviewAdded = true;
            mFrameLayout.removeView(mEmptyView);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mFrameLayout.addView(mRecyclerView, layoutParams);
        }
    }




}
