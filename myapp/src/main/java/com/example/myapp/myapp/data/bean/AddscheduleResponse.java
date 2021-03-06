package com.example.myapp.myapp.data.bean;

public class AddscheduleResponse {


    /**
     * data : {"completeDate":null,"completeDateStr":"","content":"{&quot;content&quot;:&quot;﹉vvvv&quot;,&quot;address&quot;:&quot;&quot;,&quot;people&quot;:&quot;&quot;,&quot;startTime&quot;:&quot;2018-08-01&quot;,&quot;endTime&quot;:&quot;2018-09-12&quot;}","date":1573458641852,"dateStr":"2019-11-11","id":17432,"priority":0,"status":0,"title":"日程","type":0,"userId":8834}
     * errorCode : 0
     * errorMsg :
     */

    private DataBean data;
    private int errorCode;  //0 代表成功
    private String errorMsg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public static class DataBean {
        /**
         * completeDate : null
         * completeDateStr :
         * content : {&quot;content&quot;:&quot;﹉vvvv&quot;,&quot;address&quot;:&quot;&quot;,&quot;people&quot;:&quot;&quot;,&quot;startTime&quot;:&quot;2018-08-01&quot;,&quot;endTime&quot;:&quot;2018-09-12&quot;}
         * date : 1573458641852
         * dateStr : 2019-11-11
         * id : 17432
         * priority : 0
         * status : 0
         * title : 日程
         * type : 0
         * userId : 8834
         */

        private String completeDate;
        private String completeDateStr;
        private String content;
        private long date;
        private String dateStr;
        private int id;
        private int priority;
        private int status;
        private String title;
        private int type;
        private int userId;

        public String getCompleteDate() {
            return completeDate;
        }

        public void setCompleteDate(String completeDate) {
            this.completeDate = completeDate;
        }

        public String getCompleteDateStr() {
            return completeDateStr;
        }

        public void setCompleteDateStr(String completeDateStr) {
            this.completeDateStr = completeDateStr;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public long getDate() {
            return date;
        }

        public void setDate(long date) {
            this.date = date;
        }

        public String getDateStr() {
            return dateStr;
        }

        public void setDateStr(String dateStr) {
            this.dateStr = dateStr;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
