package com.example.myapp.myapp.data.bean;

/**
 * Created by yexing on 2018/10/15.
 */

public class FortuneResponse {


    /**
     * msg : success
     * result : {"conclusion":"时来运转，事事如意，功成名就，富贵自来 吉"}
     * retCode : 200
     */

    private String msg;
    private ResultBean result;
    private int retCode;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public static class ResultBean {
        /**
         * conclusion : 时来运转，事事如意，功成名就，富贵自来 吉
         */

        private String conclusion;

        public String getConclusion() {
            return conclusion;
        }

        public void setConclusion(String conclusion) {
            this.conclusion = conclusion;
        }
    }
}
