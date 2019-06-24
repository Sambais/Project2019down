package com.hnkjrjxy.project2019down.entry;

import java.util.List;

public class MyHomeTop {

    /**
     * msg : S
     * data : [[{"channel_id":1,"name":"情绪"},{"channel_id":2,"name":"社交"},{"channel_id":3,"name":"爱好"}],[{"id":1,"name":"吐槽","icon_name":"complain","channel_id":1},{"id":2,"name":"暗恋 ","icon_name":"anlian","channel_id":1},{"id":3,"name":"开心","icon_name":"happy","channel_id":1},{"id":4,"name":"烦恼","icon_name":"unhappy","channel_id":1},{"id":5,"name":"迷茫","icon_name":"dull","channel_id":1},{"id":6,"name":"柠檬精","icon_name":"lemon","channel_id":1},{"id":7,"name":"沙雕","icon_name":"shadiao","channel_id":2},{"id":8,"name":"关于我 ","icon_name":"me","channel_id":2},{"id":9,"name":"求助","icon_name":"help","channel_id":2},{"id":10,"name":"安利","icon_name":"share","channel_id":2},{"id":11,"name":"治愈","icon_name":"crue","channel_id":2},{"id":12,"name":"表情包 ","icon_name":"biaoqingbao","channel_id":2},{"id":13,"name":"脑洞 ","icon_name":"lemon","channel_id":2},{"id":14,"name":"学习 ","icon_name":"lemon","channel_id":2},{"id":15,"name":"游戏","icon_name":"game","channel_id":3},{"id":16,"name":"追剧 ","icon_name":"teleplay","channel_id":3},{"id":17,"name":"爱豆","icon_name":"aidou","channel_id":3},{"id":18,"name":"二次元","icon_name":"erciyuan","channel_id":3},{"id":19,"name":"摄影","icon_name":"photopragh","channel_id":3},{"id":20,"name":"绘画 ","icon_name":"drawing","channel_id":3}],{"msg":"hot","data":[{"id":3,"name":"开心","icon_name":"happy","channel_id":1},{"id":4,"name":"烦恼","icon_name":"unhappy","channel_id":1},{"id":6,"name":"柠檬精","icon_name":"lemon","channel_id":1},{"id":8,"name":"关于我 ","icon_name":"me","channel_id":2},{"id":11,"name":"治愈","icon_name":"crue","channel_id":2},{"id":15,"name":"游戏","icon_name":"game","channel_id":3},{"id":16,"name":"追剧 ","icon_name":"teleplay","channel_id":3},{"id":17,"name":"爱豆","icon_name":"aidou","channel_id":3},{"id":18,"name":"二次元","icon_name":"erciyuan","channel_id":3},{"id":19,"name":"摄影","icon_name":"photopragh","channel_id":3},{"id":20,"name":"绘画 ","icon_name":"drawing","channel_id":3}]}]
     */

    private String msg;
    private List<List<DataBean>> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<List<DataBean>> getData() {
        return data;
    }

    public void setData(List<List<DataBean>> data) {
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
