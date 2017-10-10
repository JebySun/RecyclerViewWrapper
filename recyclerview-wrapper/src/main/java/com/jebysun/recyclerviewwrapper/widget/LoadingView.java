package com.jebysun.recyclerviewwrapper.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.jebysun.recyclerviewwrapper.LoadingProcessCallback;

/**
 * Created by JebySun on 2017/10/10.
 * email:jebysun@126.com
 */

public class LoadingView extends TextView implements LoadingProcessCallback {


    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onLoading() {
        this.setText("正在加载...哈哈哈");
    }

    @Override
    public void onComplete() {
        this.setText("加载成功");
    }
}
