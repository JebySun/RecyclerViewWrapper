package com.jebysun.recyclerviewwrapper.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.jebysun.recyclerviewwrapper.LoadingProcessCallback;
import com.jebysun.recyclerviewwrapper.OnLoadingListener;
import com.jebysun.recyclerviewwrapper.OnRefreshListener;
import com.jebysun.recyclerviewwrapper.R;
import com.jebysun.recyclerviewwrapper.RefreshProcessCallback;
import com.jebysun.recyclerviewwrapper.util.AndroidUtil;

/**
 * RecyclerView拓展容器，包含RecyclerView，下拉刷新视图，上拉加载更多视图，空视图。
 * Created by JebySun on 2017/10/10.
 * email:jebysun@126.com
 */

public class ContainerFrameLayout extends FrameLayout {
    private static final String TAG = "ContainerFrameLayout";

    private Context mContext;

    private RecyclerView mRecycler;
    private View mRefreshView;
    private View mLoadingView;
    private RefreshProcessCallback mRefreshCallback;
    private LoadingProcessCallback mLoadingCallback;
    private View mEmptyView;

    private int lastTouchY;
    private int pullStartY;
    private int pullHeight;

    private OnRefreshListener mOnRefreshListener;
    private OnLoadingListener mOnLoadingListener;

    public ContainerFrameLayout(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e(TAG, "onSizeChanged");

        mRefreshView = getChildAt(0);
        mRecycler = (RecyclerView) getChildAt(1);
        mLoadingView = getChildAt(2);
        mEmptyView = findViewById(R.id.empty_view_id);

        mRefreshCallback = (RefreshProcessCallback) mRefreshView;
        mLoadingCallback = (LoadingProcessCallback) mLoadingView;

        // 加载更多
        mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                RecyclerView.SCROLL_STATE_IDLE     // 停止滚动
//                RecyclerView.SCROLL_STATE_DRAGGING // 被拖拽滑动
//                RecyclerView.SCROLL_STATE_SETTLING // 惯性滚动
                if (recyclerView.canScrollVertically(1) == false) {
                    // TODO 添加动画
                    AndroidUtil.setMargins(mLoadingView, 0, 0, 0, 0);
                    AndroidUtil.setMargins(mRecycler, 0, 0, 0, mLoadingView.getHeight());
                    recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount()-1);

                    mLoadingCallback.onLoading();
                    mOnLoadingListener.onLoading();
                }
            }
        });
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        int touchY = (int) event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            lastTouchY = touchY;
        }

        // 列表不能向下继续滚动（已滑到顶部），且触摸方向为向下，理解意图为下拉刷新，则拦截本次触摸事件
        if (mRecycler.canScrollVertically(-1) == false && lastTouchY < touchY) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            // 接收不到ACTION_DOWN事件
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "ACTION_DOWN "+event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                if (pullStartY == 0) {
                    pullStartY = (int) event.getY();
                }
                Log.e(TAG, "ACTION_MOVE "+event.getY());
                pullHeight = (int) event.getY() - pullStartY;
                // 正在下拉
                AndroidUtil.setMargins(mRefreshView, 0, -mRefreshView.getHeight() + pullHeight, 0, 0);
                AndroidUtil.setMargins(mRecycler, 0, pullHeight, 0, 0);
                if (mEmptyView != null) {
                    AndroidUtil.setMargins(mEmptyView, 0, pullHeight, 0, 0);
                }

                if (pullHeight > 0 && pullHeight < mRefreshView.getHeight()) {
                    mRefreshCallback.onPull(pullHeight);
                    // 下拉高度已达到刷新临界值
                } else if (pullHeight > 0 && pullHeight >= mRefreshView.getHeight()) {
                    mRefreshCallback.onReleaseToRefresh();
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "ACTION_UP "+event.getY());
                pullStartY = 0;
                // 如果下拉距离不够，则回弹; 否则，进行刷新。
                if (pullHeight < mRefreshView.getHeight()) {
                    // TODO 添加动画
                    AndroidUtil.setMargins(mRefreshView, 0, -mRefreshView.getHeight(), 0, 0);
                    AndroidUtil.setMargins(mRecycler, 0, 0, 0, 0);
                    if (mEmptyView != null) {
                        AndroidUtil.setMargins(mEmptyView, 0, 0, 0, 0);
                    }
                    mRefreshCallback.onRelease();
                } else {
                    // TODO 添加动画 下拉过多，回到合适位置再刷新
                    AndroidUtil.setMargins(mRefreshView, 0, 0, 0, 0);
                    AndroidUtil.setMargins(mRecycler, 0, mRefreshView.getHeight(), 0, 0);
                    mRefreshCallback.onRefresh();
                    mOnRefreshListener.onRefresh();
                }
                break;
            default:
                break;
        }
        return true;
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        mOnRefreshListener = listener;
    }

    public void setOnLoadingListener(OnLoadingListener listener) {
        mOnLoadingListener = listener;
    }





}
