package com.jebysun.demo.test;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jebysun.demo.AndroidUtil;
import com.jebysun.demo.R;

/**
 * Created by JebySun on 2017/10/10.
 * email:jebysun@126.com
 */

public class MyFrameLayout extends FrameLayout {
    private static final String TAG = "MyFrameLayout";

    private RecyclerView mRecycler;
    private TextView textViewRefresh;
    private TextView textViewLoading;
    private int lastY;
    private int refreshBarMargin;
    private int pullStartY;

    public MyFrameLayout(Context context) {
        this(context, null);
    }
    public MyFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public MyFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        refreshBarMargin = AndroidUtil.dp2px(this.getContext(), 60);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        Log.e(TAG, "onFinishInflate");
        mRecycler = (RecyclerView) this.findViewById(R.id.recycler);
        textViewRefresh = (TextView) findViewById(R.id.tv_refresh_bar);
        textViewLoading = (TextView) findViewById(R.id.tv_loading_bar);


        mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                RecyclerView.SCROLL_STATE_IDLE     // 停止滚动
//                RecyclerView.SCROLL_STATE_DRAGGING // 被拖拽滑动
//                RecyclerView.SCROLL_STATE_SETTLING // 惯性滚动
                if (recyclerView.canScrollVertically(1) == false) {
                    setMargins(textViewLoading, 0, 0, 0, 0);
                    setMargins(mRecycler, 0, 0, 0, refreshBarMargin);
                }
            }
        });
    }


    public boolean onInterceptTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            lastY = y;
        }

        if ((mRecycler.canScrollVertically(-1) == false && lastY < y) || (mRecycler.canScrollVertically(1) == false && lastY > y)) {
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
                int pullHeight = (int) event.getY() - pullStartY;
                if (pullHeight > 0 && pullHeight<refreshBarMargin) {
                    setMargins(textViewRefresh, 0, -refreshBarMargin+pullHeight, 0, 0);
                    setMargins(mRecycler, 0, pullHeight, 0, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "ACTION_UP "+event.getY());
                pullStartY = 0;
                setMargins(textViewRefresh, 0, -refreshBarMargin, 0, 0);
                setMargins(mRecycler, 0, 0, 0, 0);
                break;
            default:
                break;
        }
        return true;
    }





    public void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }
}
