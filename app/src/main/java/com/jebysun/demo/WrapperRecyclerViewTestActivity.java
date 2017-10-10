package com.jebysun.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jebysun.recyclerviewwrapper.OnLoadingListener;
import com.jebysun.recyclerviewwrapper.OnRefreshListener;
import com.jebysun.recyclerviewwrapper.RecyclerViewWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JebySun on 2017/10/9.
 * email:jebysun@126.com
 */

public class WrapperRecyclerViewTestActivity extends AppCompatActivity {

    private RecyclerView mRecycler;
    private RecyclerViewWrapper mRecyclerWrapper;
    private List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_wrapper);

        mRecycler = (RecyclerView) findViewById(R.id.recycler);

        // 一般的RecylcerView使用
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.addItemDecoration(new LinearLayoutItemDecoration(40));
        mRecycler.setAdapter(new RecyclerAdapter());




        // Wrapper使用开始，注意要在设置setAdapter之后。
        mRecyclerWrapper = new RecyclerViewWrapper(mRecycler);

        View headerView = LayoutInflater.from(this).inflate(R.layout.layout_header, null);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidUtil.toastShort(WrapperRecyclerViewTestActivity.this, "=====onClick======");
            }
        });
        // 设置HeaderView TODO 大小有问题
        mRecyclerWrapper.setHeaderView(headerView);
        // 设置HeaderView 大小无问题
        mRecyclerWrapper.setHeaderView(R.layout.layout_header);

        TextView emptyView = new TextView(this);
        emptyView.setText("列表为空");
        emptyView.setGravity(Gravity.CENTER);
        emptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidUtil.toastShort(WrapperRecyclerViewTestActivity.this, "重新加载数据");
            }
        });
        // 设置空列表视图
        mRecyclerWrapper.setEmptyView(emptyView);
//        mRecyclerWrapper.setEmptyView(R.layout.layout_empty);

        // 下拉刷新
        mRecyclerWrapper.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // TODO 刷新
                mRecycler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AndroidUtil.toastShort(WrapperRecyclerViewTestActivity.this, "刷新完成");
                        mRecyclerWrapper.refreshComplete();
                    }
                }, 1000);
            }
        });

        // 加载更多
        mRecyclerWrapper.setOnLoadingListener(new OnLoadingListener() {
            @Override
            public void onLoading() {
                // TODO 加载
                mRecycler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AndroidUtil.toastShort(WrapperRecyclerViewTestActivity.this, "加载完成");
                        mRecyclerWrapper.loadingComplete();
                    }
                }, 1000);
            }
        });




        // 数据模拟
        testData();

        mRecyclerWrapper.notifyDataSetChanged();
    }

    private void testData() {
        for (int i=0; i<40; i++) {
            mList.add("杨国富麻辣烫" + i);
        }
    }




    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

        private LayoutInflater mInflater;

        public RecyclerAdapter() {
            mInflater = LayoutInflater.from(WrapperRecyclerViewTestActivity.this);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.item_single_text, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.mTitleTv.setText(mList.get(position));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private TextView mTitleTv;

            public ViewHolder(View itemView) {
                super(itemView);
                mTitleTv = (TextView) itemView.findViewById(R.id.tv_title);
            }
        }

    }


}
