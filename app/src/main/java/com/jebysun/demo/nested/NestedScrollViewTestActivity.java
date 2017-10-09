package com.jebysun.demo.nested;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jebysun.demo.LinearLayoutItemDecoration;
import com.jebysun.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 注意：
 * NestedScrollView嵌套RecyclerView，虽然显示正常，但会导致一次性加载全部列表，在列表项较多时有性能影响。
 */
public class NestedScrollViewTestActivity extends AppCompatActivity {

    private RecyclerView mRecycler;
    private RecyclerAdapter mAdapter;
    private List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_without_nestedscrollview);
        setContentView(R.layout.activity_with_nestedscrollview);

        mRecycler = (RecyclerView) findViewById(R.id.recycler);

        testData();

        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.addItemDecoration(new LinearLayoutItemDecoration(20));
        mAdapter = new RecyclerAdapter();
        mRecycler.setAdapter(mAdapter);
        mRecycler.setNestedScrollingEnabled(false);
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
            mInflater = LayoutInflater.from(NestedScrollViewTestActivity.this);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.item_test, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            Log.e("onCreateViewHolder", viewType+"");
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.mTitleTv.setText(mList.get(position));
            Log.e("onBindViewHolder", position + mList.get(position));
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
