package com.hnkjrjxy.project2019down;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.hnkjrjxy.project2019down.util.WebSocketClient;

public class MyService extends Service {

    private static final String TAG = "MyService";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: 服务器启动");
        WebSocketClient webSocketClient = new WebSocketClient();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
