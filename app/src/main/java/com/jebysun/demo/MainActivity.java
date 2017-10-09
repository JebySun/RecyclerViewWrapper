package com.jebysun.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jebysun.demo.nested.NestedScrollViewTestActivity;
import com.jebysun.demo.wrapper.WrapperRecyclerViewTestActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTvNested;
    private TextView mTvWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvNested = (TextView) findViewById(R.id.tv_nested);
        mTvWrapper = (TextView) findViewById(R.id.tv_wrapper);
        mTvNested.setOnClickListener(this);
        mTvWrapper.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == mTvNested) {
            startActivity(new Intent(this, NestedScrollViewTestActivity.class));
        } else if (v == mTvWrapper) {
            startActivity(new Intent(this, WrapperRecyclerViewTestActivity.class));
        }
    }
}
