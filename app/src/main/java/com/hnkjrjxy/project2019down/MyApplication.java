package com.hnkjrjxy.project2019down;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import com.hnkjrjxy.project2019down.entry.Top;
import com.hnkjrjxy.project2019down.entry.UserInfo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class MyApplication extends Application {
    public static Context context;
    public static String token;
    public static boolean isLogin = false;
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    public static UserInfo userInfo;
    public static ArrayList tabtitle;
    public static ArrayList<Top.DataBean> list1;
    public static ArrayList<Top.DataBean> list2;
    public static ArrayList<Top.DataBean> list3;
    public static ArrayList<Top.DataBean> list4;
    public static ArrayList<Top.DataBean> list5;

    public static ArrayList getTabtitle() {
        return tabtitle;
    }

    public static void setTabtitle(ArrayList tabtitle) {
        MyApplication.tabtitle = tabtitle;
    }

    public static UserInfo getUserInfo() {
        return userInfo;
    }

    public static void setUserInfo(UserInfo userInfo) {
        MyApplication.userInfo = userInfo;
    }

    public static void setUserInfo(int id) {

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

    public static ArrayList getList1() {
        return list1;
    }

    public static void setList1(ArrayList list1) {
        MyApplication.list1 = list1;
    }

    public static ArrayList getList2() {
        return list2;
    }

    public static void setList2(ArrayList list2) {
        MyApplication.list2 = list2;
    }

    public static ArrayList getList3() {
        return list3;
    }

    public static void setList3(ArrayList list3) {
        MyApplication.list3 = list3;
    }

    public static ArrayList getList4() {
        return list4;
    }

    public static void setList4(ArrayList list4) {
        MyApplication.list4 = list4;
    }

    public static ArrayList getList5() {
        return list5;
    }

    public static void setList5(ArrayList list5) {
        MyApplication.list5 = list5;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        disableAPIDialog();
        tabtitle=new ArrayList();
        list1=new ArrayList();

        list2=new ArrayList();
        list3=new ArrayList();
        list4=new ArrayList();
        list5=new ArrayList();
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
