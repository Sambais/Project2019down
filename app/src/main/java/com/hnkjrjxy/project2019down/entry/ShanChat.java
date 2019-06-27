package com.hnkjrjxy.project2019down.entry;

import java.util.List;

public class ShanChat {

    /**
     * msg : S
     * data : [{"id":28,"username":"Sam","asme":"青春就像一只容器，装满了不安躁动青涩与偶尔的疯狂。"},{"id":29,"username":"啦啦啦","asme":"不去期望，失去了不会伤心，得到了便是惊喜。"},{"id":28,"username":"Sam","asme":"青春就像一只容器，装满了不安躁动青涩与偶尔的疯狂。"},{"id":29,"username":"啦啦啦","asme":"不去期望，失去了不会伤心，得到了便是惊喜。"},{"id":28,"username":"Sam","asme":"青春就像一只容器，装满了不安躁动青涩与偶尔的疯狂。"},{"id":29,"username":"啦啦啦","asme":"不去期望，失去了不会伤心，得到了便是惊喜。"},{"id":28,"username":"Sam","asme":"青春就像一只容器，装满了不安躁动青涩与偶尔的疯狂。"},{"id":29,"username":"啦啦啦","asme":"不去期望，失去了不会伤心，得到了便是惊喜。"},{"id":28,"username":"Sam","asme":"青春就像一只容器，装满了不安躁动青涩与偶尔的疯狂。"},{"id":29,"username":"啦啦啦","asme":"不去期望，失去了不会伤心，得到了便是惊喜。"},{"id":28,"username":"Sam","asme":"青春就像一只容器，装满了不安躁动青涩与偶尔的疯狂。"},{"id":29,"username":"啦啦啦","asme":"不去期望，失去了不会伤心，得到了便是惊喜。"}]
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
         * id : 28
         * username : Sam
         * asme : 青春就像一只容器，装满了不安躁动青涩与偶尔的疯狂。
         */

        private int id;
        private String username;
        private String asme;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAsme() {
            return asme;
        }

        public void setAsme(String asme) {
            this.asme = asme;
        }
    }
}
