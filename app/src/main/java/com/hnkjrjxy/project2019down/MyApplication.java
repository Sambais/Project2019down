package com.hnkjrjxy.project2019down;

import android.app.Application;
import android.content.Context;

import com.hnkjrjxy.project2019down.util.WebSocketClient;

public class MyApplication extends Application {
    public static Context context;
    public static String token;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        MyApplication.context = context;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        MyApplication.token = token;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
