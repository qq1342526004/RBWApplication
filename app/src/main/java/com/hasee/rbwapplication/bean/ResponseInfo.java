package com.hasee.rbwapplication.bean;

/**
 * Created by fangju on 2018/11/23
 */
public class ResponseInfo {
    private int status;//状态信息
    private String message;//返回的命令消息
    private String data;//返回的具体结果

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
