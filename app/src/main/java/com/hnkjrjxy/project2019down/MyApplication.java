package com.hnkjrjxy.project2019down;

import android.app.Application;
import android.content.Context;

import com.hnkjrjxy.project2019down.util.WebSocketClient;

public class MyApplication extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
