package com.hnkjrjxy.project2019down.util;

import com.google.gson.Gson;
import com.hnkjrjxy.project2019down.msg.Msg;
import com.hnkjrjxy.project2019down.msg.SendInfoMsg;
import com.zhangke.websocket.dispatcher.IResponseDispatcher;
import com.zhangke.websocket.dispatcher.ResponseDelivery;
import com.zhangke.websocket.response.ErrorResponse;

import org.java_websocket.framing.Framedata;

import java.nio.ByteBuffer;

public class AppResponseDispatcher implements IResponseDispatcher {

    Gson gson = new Gson();
    private static final String TAG = "AppResponseDispatcher";

    @Override
    public void onConnected(ResponseDelivery delivery) {

    }

    @Override
    public void onConnectFailed(Throwable cause, ResponseDelivery delivery) {

    }

    @Override
    public void onDisconnect(ResponseDelivery delivery) {

    }

    //接收到文本消息    ResponseDelivery 是数据发射器，我们可以用它将数据发送到各个接收点
    @Override
    public void onMessage(String message, ResponseDelivery delivery) {
        Msg msg = gson.fromJson(message,Msg.class);
        switch (msg.getMsg()){
            case "to":
                delivery.onMessage(message,SendInfoMsg.class);
                break;
            case "add":
                break;
            case "token":
                delivery.onMessage(message,Msg.class);
                break;
        }
    }

    //接收到二进制消息
    @Override
    public void onMessage(ByteBuffer byteBuffer, ResponseDelivery delivery) {

    }

    @Override
    public void onPing(Framedata framedata, ResponseDelivery delivery) {

    }

    @Override
    public void onPong(Framedata framedata, ResponseDelivery delivery) {

    }

    //数据发送失败
    @Override
    public void onSendDataError(ErrorResponse error, ResponseDelivery delivery) {

    }
}
