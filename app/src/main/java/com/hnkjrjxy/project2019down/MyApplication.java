package com.hnkjrjxy.project2019down;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

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
    public static String[] pindao1={"吐槽", "暗恋" , "开心", "烦恼" , "迷茫" , "柠檬精"};
    public static String[] pindao1_p={"complain", "anlian" , "happy", "unhappy" , "dull" , "lemon"};
    public static String[] pindao2={"沙雕", "关于我" , "求助" , "安利" , "治愈" , "表情包" , "脑洞" , "学习"};
    public static String[] pindao2_p={"shadiao", "me" , "help" , "share" , "cure" , "biaoqingbao" , "naodong" , "study"};
    public static String[] pindao3={"游戏", "追剧" , "爱豆" , "二次元", "摄影" , "绘画"};
    public static String[] pindao3_p={"game", "teleplay" , "aidou" , "erciyuan", "photopragh" , "drawing"};
    public static ArrayList allpindao;
    public static ArrayList allpindao_p;
    public static ArrayList hotpindao;
    public static ArrayList hotpindao_p;

    //以下部分暂时不再使用
    public static String[] list4;
    public static String[] list5;

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

    @Override
    public void onCreate() {
        super.onCreate();
        disableAPIDialog();
        tabtitle=new ArrayList();
        allpindao=new ArrayList();
        allpindao_p=new ArrayList();
        hotpindao=new ArrayList();
        hotpindao_p=new ArrayList();
        context = getApplicationContext();
        sharedPreferences = getSharedPreferences("project2019",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        if(sharedPreferences.getInt("id",0) != 0){
            setIsLogin(true);
        }

        String[][] pin1={pindao1,pindao2,pindao3};
        String[][] pin2={pindao1_p,pindao2_p,pindao3_p};
        for (int i = 0; i <pin1.length ; i++) {
            for (int j = 0; j <pin1[i].length ; j++) {
                allpindao.add(pin1[i][j]);
            }
            for (int j = 0; j <pin2[i].length ; j++) {
                allpindao_p.add(pin2[i][j]);
            }
        }
        Log.i("AOPO", "onCreate: =========="+allpindao);

        ArrayList hot = (ArrayList) allpindao.clone();
        ArrayList hot_p = (ArrayList) allpindao_p.clone();
        for (int i = 0; i < allpindao_p.size()-12; i++) {
            int num = (int) (Math.random()*hot.size()-1);
            hot.remove(num);
            hot_p.remove(num);
        }
        hotpindao = (ArrayList) hot.clone();
        hotpindao_p = (ArrayList) hot_p.clone();
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
