package com.example.myapp.myapp.data.bean;

/**
 * Created by yexing on 2018/8/10.
 */

public class WanAndroidBaseResponse {


    /**
     * data : null
     * errorCode : 0
     * errorMsg :
     */

    private int errorCode;
    private String errorMsg;


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
}
