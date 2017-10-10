package com.jebysun.recyclerviewwrapper;

/**
 * 加载更多过程回调接口
 * Created by JebySun on 2017/10/9.
 * email:jebysun@126.com
 */

public interface LoadingProcessCallback {

    // 正在加载
    void onLoading();

    // 加载完成
    void onComplete();

}
