package com.example.myapp.myapp.component.life.entity;

import java.util.List;

public class JokeBean {

    public int code;
    public String msg;
    public List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

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
         * type : video
         * text : 什么逻辑
         * username : 怪咖girl
         * uid : 12253796
         * header : http://qzapp.qlogo.cn/qzapp/100336987/8742C6C62C6F55038E1A25F3D075CF69/100
         * comment : 330
         * top_commentsVoiceuri : null
         * top_commentsContent : 这瓦片真厚，但还是不及楼主的脸皮厚。
         * top_commentsHeader : http://wimg.spriteapp.cn/profile/large/2015/10/25/562c124276f14_mini.jpg
         * top_commentsName : 彩云罩月
         * passtime : 2018-06-19 09:41:02
         * soureid : 28146523
         * up : 636
         * down : 209
         * forward : 20
         * image : null
         * gif : null
         * thumbnail : http://wimg.spriteapp.cn/picture/2018/0616/5b2533ef6a299__b.jpg
         * video : http://wvideo.spriteapp.cn/video/2018/0616/5b2533ef79ecd_wpd.mp4
         */

        public String type;
        public String text;
        public String username;
        public String uid;
        public String header;
        public String comment;
        public String top_commentsVoiceuri;
        public String top_commentsContent;
        public String top_commentsHeader;
        public String top_commentsName;
        public String passtime;
        public String soureid;
        public String up;
        public String down;
        public String forward;
        public String image;
        public String gif;
        public String thumbnail;
        public String video;

    }
}
