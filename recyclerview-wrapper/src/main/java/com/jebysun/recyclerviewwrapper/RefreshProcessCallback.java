package com.jebysun.recyclerviewwrapper;

/**
 * 下拉刷新过程回调接口
 * Created by JebySun on 2017/10/9.
 * email:jebysun@126.com
 */

public interface RefreshProcessCallback {

    // 正在下拉, 还未到刷新临界点
    void onPull(int scrolledY);

    // 下拉一段距离后释放，未到刷新临界点。
    void onRelease();


    // 释放后开始刷新
    void onReleaseToRefresh();

    // 正在刷新
    void onRefresh();

    // 刷新完成
    void onComplete();


}
