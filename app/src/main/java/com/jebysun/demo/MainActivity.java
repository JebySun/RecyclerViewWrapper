package com.jebysun.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jebysun.demo.test.FrameTestActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnWrapper = findViewById(R.id.btn_wrapper);
        mBtnWrapper.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == mBtnWrapper) {
            startActivity(new Intent(this, WrapperRecyclerViewTestActivity.class));
//            startActivity(new Intent(this, FrameTestActivity.class));
        }
    }
}
