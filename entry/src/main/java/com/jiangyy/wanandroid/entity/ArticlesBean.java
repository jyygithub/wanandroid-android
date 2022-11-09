package com.jiangyy.wanandroid.entity;

public class ArticlesBean {

    private ArticlesPage data;
    private int errorCode;
    private String errorMsg;

    public ArticlesPage getData() {
        return data;
    }

    public void setData(ArticlesPage data) {
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
}
