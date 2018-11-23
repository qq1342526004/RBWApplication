package com.hasee.rbwapplication.util;

import android.os.Message;

import com.alibaba.fastjson.JSONObject;
import com.hasee.rbwapplication.bean.ResponseInfo;

/**
 * Created by fangju on 2018/11/23
 */
public class MyThread extends Thread {

    public static final int RESPONSE_SUCCESS = 0;//响应成功
    public static final int RESPONSE_FAILED = -1;//响应失败
    public static final int CONNECT_FAILED = -101;//连接失败

    private MyHandler handler;//处理结果
    private JSONObject jsonObject;
    private String action;

    public MyThread(MyHandler handler, JSONObject jsonObject, String action){
        this.handler = handler;
        this.jsonObject = jsonObject;
        this.action = action;
    }

    @Override
    public void run() {
        ResponseInfo response = HttpRequestClient.getData(jsonObject,action);
        if(response != null){
            Message msg = handler.obtainMessage();
            msg.what = response.getStatus();
            msg.obj = response;
            handler.sendMessage(msg);
        }else{
            handler.sendEmptyMessage(CONNECT_FAILED);
        }
    }
}
