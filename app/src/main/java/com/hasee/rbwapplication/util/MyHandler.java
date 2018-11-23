package com.hasee.rbwapplication.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.hasee.rbwapplication.R;
import com.hasee.rbwapplication.bean.ResponseInfo;

/**
 * Created by fangju on 2018/11/23
 */
public class MyHandler extends Handler {
    private Context mContext;

    public MyHandler(Context mContext){
        this.mContext = mContext;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        ResponseInfo response = (ResponseInfo) msg.obj;
        switch (msg.what){
            case MyThread.RESPONSE_SUCCESS: {
                break;
            }
            case MyThread.CONNECT_FAILED: {
                Toast.makeText(mContext, R.string.response_error, Toast.LENGTH_LONG).show();
                break;
            }
            default:
                Toast.makeText(mContext, response.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}