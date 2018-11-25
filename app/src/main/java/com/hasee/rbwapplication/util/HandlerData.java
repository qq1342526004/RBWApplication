package com.hasee.rbwapplication.util;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by fangju on 2018/11/21
 * Email:1342526004@qq.com
 */
public class HandlerData {
    private static String action = "action.do";
    /**
     * 登陆时的数据
     * @param userName
     * @param passWord
     * @return
     */
    public static void login(MyHandler handler, String userName, String passWord){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("module","userLogin");
        jsonObject.put("operation","employeeLogin");
        jsonObject.put("type","app");
        jsonObject.put("username",userName);
        jsonObject.put("password",passWord);
        new MyThread(handler, jsonObject,action).start();
    }

    /**
     * 发送数据
     * @param jsonArray 发送的数据（[{},{}...]）
     * @return
     */
    public static void send(MyHandler handler, JSONArray jsonArray){
        JSONObject jsonObject = new JSONObject();//发送给服务器的数据
        jsonObject.put("module", "test");
        jsonObject.put("operation", "submit");
        jsonObject.put("type", "app");
        jsonObject.put("datas", jsonArray);
        new MyThread(handler, jsonObject, action).start();
    }

    /**
     * 查询所有数据
     */
    public static void queryAll(MyHandler handler){
        JSONObject jsonObject = new JSONObject();//发送给服务器的数据
        jsonObject.put("module", "test");
        jsonObject.put("operation", "query");
        jsonObject.put("type", "app");
        new MyThread(handler,jsonObject,action).start();
    }

    /**
     * 查询单个 数据
     */
    public static void querySingle(MyHandler handler, JSONObject itemObject){
        JSONObject jsonObject = new JSONObject();//发送给服务器的数据
        jsonObject.put("module", "test");
        jsonObject.put("operation", "bartest");
        jsonObject.put("type", "app");
        jsonObject.put("rely",itemObject);
        new MyThread(handler,jsonObject,action).start();
    }

    /**
     * 修改数据
     * @param jsonArray
     * @return
     */
    public static JSONObject updateData(JSONArray jsonArray){
        JSONObject jsonObject = new JSONObject();//发送给服务器的数据
        jsonObject.put("module", "test");
        jsonObject.put("operation", "update");
        jsonObject.put("type", "app");
        jsonObject.put("datas",jsonArray);
        return jsonObject;
    }

    /**
     * 添加数据
     * @param barCode
     * @param xiangShu
     * @param jianShu
     * @return
     */
    public static String addData(String barCode, String xiangShu, String jianShu){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("barCode",barCode);
        jsonObject.put("xiangShu",xiangShu);
        jsonObject.put("jianShu",jianShu);
        return jsonObject.toString();
    }
}
