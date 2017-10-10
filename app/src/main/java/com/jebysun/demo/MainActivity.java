package com.jebysun.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jebysun.demo.test.FrameTestActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTvWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvWrapper = (TextView) findViewById(R.id.tv_wrapper);
        mTvWrapper.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == mTvWrapper) {
            startActivity(new Intent(this, WrapperRecyclerViewTestActivity.class));
//            startActivity(new Intent(this, FrameTestActivity.class));
        }
    }
}
