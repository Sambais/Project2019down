package com.hnkjrjxy.project2019down.entry;

import java.util.List;

public class UserInfo {

    /**
     * msg : S
     * data : [{"id":17,"phone":"123456","token":"1561129728120"},{"id":17,"username":"123456","pwd":"123123","email":"","state":1}]
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
         * email :
         * state : 1
         */

        private int id;
        private String phone;
        private String token;
        private String username;
        private String pwd;
        private String email;
        private int state;

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

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }
}
