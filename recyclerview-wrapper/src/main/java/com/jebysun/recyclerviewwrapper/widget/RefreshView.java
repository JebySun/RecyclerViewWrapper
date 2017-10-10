package com.jebysun.recyclerviewwrapper.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.jebysun.recyclerviewwrapper.RefreshProcessCallback;

/**
 * Created by JebySun on 2017/10/10.
 * email:jebysun@126.com
 */

public class RefreshView extends TextView implements RefreshProcessCallback {


    public RefreshView(Context context) {
        this(context, null);
    }

    public RefreshView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void onPull(int scrolledY) {
        this.setText("继续下拉刷新"+scrolledY+":"+getHeight());
    }

    @Override
    public void onRelease() {
        this.setText("放弃刷新");
    }

    @Override
    public void onReleaseToRefresh() {
        this.setText("释放开始刷新");
    }

    @Override
    public void onRefresh() {
        this.setText("正在刷新...");
    }

    @Override
    public void onComplete() {
        this.setText("刷新完成");
    }
}
