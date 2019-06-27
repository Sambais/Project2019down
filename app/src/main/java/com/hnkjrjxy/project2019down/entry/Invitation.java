package com.hnkjrjxy.project2019down.entry;

import java.util.List;

public class Invitation {


    /**
     * msg : S
     * data : [{"code":0,"info":{"id":55,"description":"沙发","uid":28,"channelId":5,"time":"2019-06-26 22:58:00","sendname":"Sam"},"invitationImages":[{"id":55,"imagePath":"D:\\javaweb\\projectwarehouse/projectImg/20190626/f8240c9a15d34978994a5abc48cfa6ed.png"}]},{"code":0,"info":{"id":56,"description":"二楼","uid":28,"channelId":6,"time":"2019-06-26 22:59:00","sendname":"Sam"},"invitationImages":[{"id":56,"imagePath":"D:\\javaweb\\projectwarehouse/projectImg/20190626/ce4d7e854f484459b6790e3228d6f43d.png"}]},{"code":0,"info":{"id":57,"description":"问","uid":28,"channelId":5,"time":"2019-06-27 00:20:00","sendname":"Sam"},"invitationImages":[{"id":57,"imagePath":"D:\\javaweb\\projectwarehouse/projectImg/20190627/ea7d918a71ca4548b109e35015b33163.jpg"}]},{"code":0,"info":{"id":58,"description":"问","uid":28,"channelId":5,"time":"2019-06-27 00:21:00","sendname":"Sam"},"invitationImages":[{"id":58,"imagePath":"D:\\javaweb\\projectwarehouse/projectImg/20190627/136470fc86c444f7b59f8f81360ab587.jpg"}]}]
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
         * code : 0
         * info : {"id":55,"description":"沙发","uid":28,"channelId":5,"time":"2019-06-26 22:58:00","sendname":"Sam"}
         * invitationImages : [{"id":55,"imagePath":"D:\\javaweb\\projectwarehouse/projectImg/20190626/f8240c9a15d34978994a5abc48cfa6ed.png"}]
         */

        private int code;
        private InfoBean info;
        private List<InvitationImagesBean> invitationImages;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public List<InvitationImagesBean> getInvitationImages() {
            return invitationImages;
        }

        public void setInvitationImages(List<InvitationImagesBean> invitationImages) {
            this.invitationImages = invitationImages;
        }

        public static class InfoBean {
            /**
             * id : 55
             * description : 沙发
             * uid : 28
             * channelId : 5
             * time : 2019-06-26 22:58:00
             * sendname : Sam
             */

            private int id;
            private String description;
            private int uid;
            private int channelId;
            private String time;
            private String sendname;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public int getChannelId() {
                return channelId;
            }

            public void setChannelId(int channelId) {
                this.channelId = channelId;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getSendname() {
                return sendname;
            }

            public void setSendname(String sendname) {
                this.sendname = sendname;
            }
        }

        public static class InvitationImagesBean {
            /**
             * id : 55
             * imagePath : D:\javaweb\projectwarehouse/projectImg/20190626/f8240c9a15d34978994a5abc48cfa6ed.png
             */

            private int id;
            private String imagePath;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImagePath() {
                return imagePath;
            }

            public void setImagePath(String imagePath) {
                this.imagePath = imagePath;
            }
        }
    }
}
