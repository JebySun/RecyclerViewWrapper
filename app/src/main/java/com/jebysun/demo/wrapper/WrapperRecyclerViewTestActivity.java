package com.jebysun.demo.wrapper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jebysun.demo.LinearLayoutItemDecoration;
import com.jebysun.demo.R;
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
    private RecyclerAdapter mAdapter;
    private List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_wrapper);

        mRecycler = (RecyclerView) findViewById(R.id.recycler);

        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.addItemDecoration(new LinearLayoutItemDecoration(20));
        mAdapter = new RecyclerAdapter();
        mRecycler.setAdapter(mAdapter);




        //Wrapper使用开始
        mRecyclerWrapper = new RecyclerViewWrapper(mRecycler);

        //设置HeaderView
        mRecyclerWrapper.setHeaderView(R.layout.layout_header);

        //设置空列表视图
        TextView emptyView = new TextView(this);
        emptyView.setText("列表为空");
        emptyView.setGravity(Gravity.CENTER);
        mRecyclerWrapper.setEmptyView(emptyView);


        //数据模拟
        testData();

        mRecyclerWrapper.notifyDataSetChanged();
    }

    private void testData() {
        mList.add("杨国富");
        mList.add("身价二个是");
        mList.add("哥儿");
        mList.add("世界各个");
        mList.add("算二娘map个");
        mList.add("杨国富");
        mList.add("身价二个是");
        mList.add("哥儿");
        mList.add("世界各个");
        mList.add("算二娘map个");
        mList.add("杨国富");
        mList.add("身价二个是");
        mList.add("哥儿");
        mList.add("世界各个");
        mList.add("算二娘map个");
        mList.add("杨国富");
        mList.add("身价二个是");
        mList.add("哥儿");
        mList.add("世界各个");
        mList.add("算二娘map个");
        mList.add("杨国富");
        mList.add("身价二个是");
        mList.add("哥儿");
        mList.add("世界各个");
        mList.add("算二娘map个");
        mList.add("杨国富");
        mList.add("身价二个是");
        mList.add("哥儿");
        mList.add("世界各个");
        mList.add("算二娘map个");

    }




    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

        private LayoutInflater mInflater;

        public RecyclerAdapter() {
            mInflater = LayoutInflater.from(WrapperRecyclerViewTestActivity.this);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.item_test, parent, false);
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
