package com.hnkjrjxy.project2019down.msg;

/**
 * 发送好友申请
 */
public class AddFMsg extends Msg {
    //好友昵称
    String name;
    //好友用户id
    int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
