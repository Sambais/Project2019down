package com.hnkjrjxy.project2019down;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import com.hnkjrjxy.project2019down.entry.UserInfo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MyApplication extends Application {
    public static Context context;
    public static String token;
    public static boolean isLogin = false;
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    public static UserInfo userInfo;

    public static UserInfo getUserInfo() {
        return userInfo;
    }

    public static void setUserInfo(UserInfo userInfo) {
        MyApplication.userInfo = userInfo;
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
        disableAPIDialog();
        context = getApplicationContext();
        sharedPreferences = getSharedPreferences("project2019",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        if(sharedPreferences.getInt("id",0) != 0){
            setIsLogin(true);
        }
    }

    /**
     * 反射 禁止弹窗
     */
    private void disableAPIDialog(){
        if (Build.VERSION.SDK_INT < 28)return;
        try {
            Class clazz = Class.forName("android.app.ActivityThread");
            Method currentActivityThread = clazz.getDeclaredMethod("currentActivityThread");
            currentActivityThread.setAccessible(true);
            Object activityThread = currentActivityThread.invoke(null);
            Field mHiddenApiWarningShown = clazz.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
