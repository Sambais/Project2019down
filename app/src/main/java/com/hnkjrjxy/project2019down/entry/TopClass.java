package com.hnkjrjxy.project2019down.entry;

import java.util.List;

public class TopClass {

    /**
     * msg : S
     * data : [{"channel_id":1,"name":"情绪"},{"channel_id":2,"name":"社交"},{"channel_id":3,"name":"爱好"}]
     */

    private String msg;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * channel_id : 1
         * name : 情绪
         */

        private int channel_id;
        private String name;

        public int getChannel_id() {
            return channel_id;
        }

        public void setChannel_id(int channel_id) {
            this.channel_id = channel_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
