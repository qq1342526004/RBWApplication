package com.hasee.rbwapplication.util;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.hasee.rbwapplication.App;
import com.hasee.rbwapplication.bean.ResponseInfo;


import cz.msebera.android.httpclient.Consts;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;
import cz.msebera.android.httpclient.util.EntityUtils;

/**
 * Created by fangju on 2018/11/23
 */
public class HttpRequestClient {
    /*服务器默认信息*/
    private static String ipAddress = "192.168.1.115";
    private static String port = "8089";
    private static String projectName = "RuiBangWuLiu";
    private static String baseUrl = "http://"+ipAddress+":"+port+"/"+projectName+"/";
    //HttpClient实例
    private static HttpClient client = null;

    /**
     * 更新服务器信息
     * @param ipAddress
     */
    public static void refresh(String ipAddress,String userName,String passWord) {
        baseUrl = "http://"+ipAddress+":"+port+"/"+projectName+"/";
        App.getInstance().setPreferences(ipAddress,userName,passWord);
    }


    /**
     * 单例模式保持session会话
     * @return
     */
    public static HttpClient getClient() {
        if(client == null){
            client = HttpClientBuilder.create().build();
        }
        return client;
    }

    /**
     * 发送数据至服务器
     * @param jsonObject
     * @param action
     * @return 请求结果
     */
    public static ResponseInfo getData(JSONObject jsonObject, String action){
        HttpClient client = getClient();
        HttpPost post = null;
        Log.d("net", "getData: "+baseUrl+action);
        try {
            post = new HttpPost(baseUrl+action);
            StringEntity entity = new StringEntity(jsonObject.toString(),"utf-8");
            entity.setContentType("application/json");
            entity.setContentEncoding("UTF-8");
            post.setEntity(entity);
            //返回的result为一个JSONObect型字符串
            String result = EntityUtils.toString(client.execute(post).getEntity(),Consts.UTF_8);
            ResponseInfo response = JSONObject.parseObject(result,ResponseInfo.class);
            return response;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            post.releaseConnection();
        }
        return null;
    }

}
