package com.hnkjrjxy.project2019down.util;

import android.widget.Toast;

import com.hnkjrjxy.project2019down.MyApplication;

public class ToastUtil {

    public static void toToast(String msg){
        Toast.makeText(MyApplication.context, msg, Toast.LENGTH_SHORT).show();
    }

}
