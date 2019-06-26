package com.hnkjrjxy.project2019down.msg;

/**
 * 返回客户端的消息
 */
public class ReturnMsg extends Msg {
    String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
