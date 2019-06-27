package com.hnkjrjxy.project2019down.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hnkjrjxy.project2019down.R;

public class LookPhoto extends Activity {
    private ImageView lookphoto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lookphoto);
        initWindows();
        initView();
    }

    private void initView() {
        Intent intent=getIntent();
        Log.i("LookPhoto", "initView: "+intent.getStringExtra("myuri"));
        lookphoto = (ImageView) findViewById(R.id.lookphoto);
        Glide.with(LookPhoto.this).load(intent.getStringExtra("myuri"))
                .placeholder(R.mipmap.huazhi)
                //当加载图片失败时，通过error(Drawable drawable)方法设置加载失败后的图片显示：
                .error(R.mipmap.ic_launcher)
                //使用centerCrop是利用图片图填充ImageView设置的大小，如果ImageView的Height是match_parent则图片就会被拉伸填充
                .centerCrop()
                //使用centerCrop是利用图片图填充ImageView设置的大小，如果ImageView的Height是match_parent则图片就会被拉伸填充
                .fitCenter()
                // 缓存所有版本的图像（默认行为）
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                //淡出淡出效果
                .into(lookphoto);
        lookphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    private void initWindows() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // 设置状态栏底色白色
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(Color.parseColor("#fafafa"));

            // 设置状态栏字体黑色
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
}
