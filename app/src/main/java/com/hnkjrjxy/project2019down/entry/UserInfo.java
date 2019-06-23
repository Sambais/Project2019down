package com.hnkjrjxy.project2019down.entry;

import java.util.List;

public class UserInfo {


    /**
     * msg : S
     * data : [{"id":17,"phone":"123456","token":"1561129728120"},{"id":17,"username":"123456","pwd":"123123","state":1,"age":"00后","sex":"女"}]
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
         * id : 17
         * phone : 123456
         * token : 1561129728120
         * username : 123456
         * pwd : 123123
         * state : 1
         * age : 00后
         * sex : 女
         */

        private int id;
        private String phone;
        private String token;
        private String username;
        private int state;
        private String age;
        private String sex;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }
    }
}
