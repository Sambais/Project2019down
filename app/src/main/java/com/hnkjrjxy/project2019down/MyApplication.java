package com.hnkjrjxy.project2019down;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.hnkjrjxy.project2019down.util.WebSocketClient;

public class MyApplication extends Application {
    public static Context context;
    public static String token;
    public static int id;
    public static boolean isLogin = false;
    public static SharedPreferences sharedPreferences;

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        MyApplication.id = id;
    }

    public static boolean isIsLogin() {
        return isLogin;
    }

    public static void setIsLogin(boolean isLogin) {
        MyApplication.isLogin = isLogin;
    }

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
        sharedPreferences = getSharedPreferences("project2019",MODE_PRIVATE);
        if(sharedPreferences.getInt("id",0) != 0){
            id = sharedPreferences.getInt("id",0);
            setIsLogin(true);
        }
    }
}
