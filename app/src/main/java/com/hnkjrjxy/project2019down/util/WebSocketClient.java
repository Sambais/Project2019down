package com.hnkjrjxy.project2019down.util;

import android.util.Log;
import android.widget.Toast;

import com.zhangke.websocket.SocketListener;
import com.zhangke.websocket.WebSocketHandler;
import com.zhangke.websocket.WebSocketManager;
import com.zhangke.websocket.WebSocketSetting;
import com.zhangke.websocket.response.ErrorResponse;

import org.java_websocket.framing.Framedata;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class WebSocketClient {
    WebSocketSetting webSocketSetting = new WebSocketSetting();

    public WebSocketClient() {
        //连接地址
        webSocketSetting.setConnectUrl("http://192.168.43.27:8080");
        //设置心跳间隔
        webSocketSetting.setConnectionLostTimeout(60);
        //断开连接后的重连次数（不会影响性能）
        webSocketSetting.setReconnectFrequency(40);
        //将客户端与服务器的连接从htt协议升级到websocket协议
        Map<String,String> headers = new HashMap<>();
        headers.put("Upgrade","WebSocket");
        headers.put("Connection","Upgrade");
        headers.put("Sec-WebSocket-Key","7wgaspE0Tl7/66o4Dov2kw==");
        headers.put("Sec-WebSocket-Version","13");
        //设置header
//        webSocketSetting.setHttpHeaders(headers);
        //设置消息分发器，接收到数据后先进入该类中处理，处理完再发送到下游
        webSocketSetting.setResponseProcessDispatcher(new AppResponseDispatcher());
        //接收到数据后是否放入子线程处理，只有设置了 ResponseProcessDispatcher 才有意义
        webSocketSetting.setProcessDataOnBackground(true);
        //网络状态发生变化后是否重连，
        //需要调用 WebSocketHandler.registerNetworkChangedReceiver(context) 方法注册网络监听广播
        webSocketSetting.setReconnectWithNetworkChanged(true);
        //通过 init 方法初始化默认的 WebSocketManager 对象
        WebSocketManager manager = WebSocketHandler.init(webSocketSetting);
        //启动连接
        manager.start();
        manager.addListener(new MySocketListener());
    }

    class MySocketListener implements SocketListener {

        @Override
        public void onConnected() {
            ToastUtil.toToast("已连接");
            Log.i("WEBSOCKET","!!!!!!!");
        }

        @Override
        public void onConnectFailed(Throwable e) {

        }

        @Override
        public void onDisconnect() {

        }

        //数据发送失败
        @Override
        public void onSendDataError(ErrorResponse errorResponse) {

        }

        //接受到文本消息
        @Override
        public <T> void onMessage(String message, T data) {
            ToastUtil.toToast(message);
            Log.i("WEBSOCKET","!!!!!!!"+message);
        }

        //接收到二进制消息
        @Override
        public <T> void onMessage(ByteBuffer bytes, T data) {

        }

        //接收到ping帧消息   客户端发送ping帧
        @Override
        public void onPing(Framedata framedata) {

        }

        //接收到pong帧消息   服务器响应pong帧
        @Override
        public void onPong(Framedata framedata) {

        }
    }

}
