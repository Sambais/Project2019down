package com.hnkjrjxy.project2019down.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.hnkjrjxy.project2019down.R;
import com.hnkjrjxy.project2019down.util.Http;
import com.hnkjrjxy.project2019down.util.MyGlideEngine;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.util.List;

public class SendPostActivity extends Activity {
    private ImageView post_back;
    private TextView post_send;
    private EditText add_text;
    private ImageView addimage;
    private static final int REQUEST_CODE_CHOOSE = 23;

    private List<Uri> result;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newpost);
        initView();
    }

    private void initView() {
        post_back = (ImageView) findViewById(R.id.post_back);
        post_send = (TextView) findViewById(R.id.post_send);
        add_text = (EditText) findViewById(R.id.add_text);
        addimage = (ImageView) findViewById(R.id.addimage);

        post_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Matisse.from(SendPostActivity.this)
                        .choose(MimeType.allOf())//图片类型
                        .countable(true)//true:选中后显示数字;false:选中后显示对号
                        .maxSelectable(5)//可选的最大数
                        .capture(true)//选择照片时，是否显示拍照
                        .captureStrategy(new CaptureStrategy(true, "com.example.xx.fileprovider"))//参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                        .imageEngine(new MyGlideEngine())//图片加载引擎
                        .forResult(REQUEST_CODE_CHOOSE);//
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            result = Matisse.obtainResult(data);
            File f = new File(result.get(0).getPath());

//            Http.Post(this,"https://192.168.43.27:8080/upload/setFileUpload",);
            add_text.setText(result.toString());
            Glide.with(this)
                    .asBitmap() // some .jpeg files are actually gif
                    .load(result.get(0))
                    .apply(new RequestOptions() {{
                        override(Target.SIZE_ORIGINAL);
                    }})
                    .into(addimage);
        }
    }

}
