package com.hnkjrjxy.project2019down.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.android.volley.Response;
import com.google.gson.Gson;
import com.hnkjrjxy.project2019down.MainActivity;
import com.hnkjrjxy.project2019down.MyApplication;
import com.hnkjrjxy.project2019down.R;
import com.hnkjrjxy.project2019down.entry.Top;
import com.hnkjrjxy.project2019down.entry.TopClass;
import com.hnkjrjxy.project2019down.util.Http;

import org.json.JSONObject;

import java.util.ArrayList;

public class SplsahActivity extends Activity {

    private ArrayList tabtitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initWindows();
        getTop();
        getChannel();
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplsahActivity.this,MainActivity.class));
                finish();
            }
        },3000);
    }


    private void getChannel() {
        //http://hnkj3172.mynatapp.cc:80/Init/Channel
        Http.Get(SplsahActivity.this, "Init/Channel", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject object) {
                Gson gson=new Gson();
                Top top=gson.fromJson(object.toString(),Top.class);
                for (int i = 0; i <top.getData().get(0).size() ; i++) {
                    if (top.getData().get(0).get(i).getChannel_id()==1){
                        MyApplication.list2.add(top.getData().get(0).get(i));
                    } else if (top.getData().get(0).get(i).getChannel_id()==2) {
                        MyApplication.list3.add(top.getData().get(0).get(i));
                    }else {
                        MyApplication.list4.add(top.getData().get(0).get(i));
                    }
                }
                Log.i("SSS", "onResponse: "+MyApplication.list2);
            }
        });
    }


    private void getTop() {
        Http.Get(SplsahActivity.this, "Init/ChannelClass", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject object) {
                Gson gson=new Gson();
                TopClass topClass=gson.fromJson(object.toString(),TopClass.class);
                //数据拿到准备更新适配器
                tabtitle=new ArrayList();
                tabtitle.add("收藏");
               tabtitle.add("收藏");
                tabtitle.add(topClass.getData().get(0).getName()+"");
                tabtitle.add(topClass.getData().get(1).getName()+"");
                tabtitle.add(topClass.getData().get(2).getName()+"");
                MyApplication.setTabtitle(tabtitle);
                Log.i("SSS", "onResponse: "+topClass.getData().get(0).getName());
                Log.i("SSS", "onResponse: "+topClass.getData().get(1).getName());
            }
        });
    }

    private void initWindows() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // 设置状态栏底色白色
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(Color.WHITE);

            // 设置状态栏字体黑色
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
}
