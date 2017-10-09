package com.jebysun.demo;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by JebySun on 2017/9/21.
 * email:jebysun@126.com
 */

public class LinearLayoutItemDecoration extends RecyclerView.ItemDecoration {

    //分割线高度px
    private int dividerHeight;
    //分割线颜色
    private int dividerColor = -1;
    private Paint dividerPaint;

    public LinearLayoutItemDecoration(int dividerHeight) {
        //分割线高度
        this.dividerHeight = dividerHeight;
    }

    /**
     * 构造分割线
     * @param dividerHeight 分割线高度px
     * @param dividerColor 分割线颜色
     */
    public LinearLayoutItemDecoration(int dividerHeight, int dividerColor) {
        //分割线高度
        this.dividerHeight = dividerHeight;
        this.dividerColor = dividerColor;
        //画笔
        dividerPaint = new Paint();
        //设置分割线颜色
        dividerPaint.setColor(this.dividerColor);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (parent.getChildAdapterPosition(view) < state.getItemCount() - 1) {
            outRect.bottom = this.dividerHeight;
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (dividerColor == -1) {
            return;
        }

        //得到列表所有的条目
        int childCount = parent.getChildCount();
        //得到条目的宽和高
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        for (int i = 0; i < childCount - 1; i++) {
            View view = parent.getChildAt(i);
            //计算每一个条目的顶点和底部float值
            float top = view.getBottom();
            float bottom = view.getBottom() + dividerHeight;
            //重新绘制
            c.drawRect(left, top, right, bottom, dividerPaint);
        }
    }

}
