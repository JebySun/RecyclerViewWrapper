package com.jebysun.demo.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jebysun.demo.LinearLayoutItemDecoration;
import com.jebysun.demo.R;

import java.util.ArrayList;
import java.util.List;

public class FrameTestActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    private TextView textViewRefresh;
    private TextView textViewLoading;

    private RecyclerView mRecycler;
    private RecyclerAdapter mAdapter;
    private List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_frame);

        frameLayout = (FrameLayout) findViewById(R.id.layout_frame);
        textViewRefresh = (TextView) findViewById(R.id.tv_refresh_bar);
        textViewLoading = (TextView) findViewById(R.id.tv_loading_bar);
        mRecycler = (RecyclerView) findViewById(R.id.recycler);


        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.addItemDecoration(new LinearLayoutItemDecoration(20));
        mAdapter = new RecyclerAdapter();
        mRecycler.setAdapter(mAdapter);

        testData();
        mAdapter.notifyDataSetChanged();
    }

    private void testData() {
        for (int i=0; i<40; i++) {
            mList.add("杨国富麻辣烫" + i);
        }
    }




    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

        private LayoutInflater mInflater;

        public RecyclerAdapter() {
            mInflater = LayoutInflater.from(FrameTestActivity.this);
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
