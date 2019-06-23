package com.hnkjrjxy.project2019down.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hnkjrjxy.project2019down.R;

public class SendPostActivity extends Activity {
    private ImageView post_back;
    private TextView post_send;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newpost);
        initView();
    }

    private void initView() {
        post_back = (ImageView) findViewById(R.id.post_back);
        post_send = (TextView) findViewById(R.id.post_send);

        post_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
