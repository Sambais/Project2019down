package com.hnkjrjxy.project2019down.msg;

/**
 * 返回客户端的消息
 */
public class ReturnMsg extends Msg {
    Object msg;

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }
}
