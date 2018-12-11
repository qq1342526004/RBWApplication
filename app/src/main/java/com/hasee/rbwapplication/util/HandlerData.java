package com.hasee.rbwapplication.util;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by fangju on 2018/11/21
 */
public class HandlerData {
//    private static String action = "app.do";
    private static String action = "action.do";
    private static String app_handle_module = "APP库房盘点操作";
    private static String login_module = "userLogin";

    /**
     * 登陆时的数据
     * @param userName
     * @param passWord
     * @return
     */
    public static void login(MyHandler handler, String userName, String passWord){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("module",login_module);
        jsonObject.put("operation","employeeLogin");
//        jsonObject.put("loginType","employeeLogin");
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
        jsonObject.put("module", app_handle_module);
        jsonObject.put("operation", "InventoryDataSubmit");//盘点数据提交
        jsonObject.put("type", "app");
        jsonObject.put("datas", jsonArray);
        new MyThread(handler, jsonObject, action).start();
    }

    /**
     * 查询差异表
     */
    public static void queryAll(MyHandler handler){
        JSONObject jsonObject = new JSONObject();//发送给服务器的数据
        jsonObject.put("module", app_handle_module);
        jsonObject.put("operation", "ZSCYDataView");//差异表
        jsonObject.put("type", "app");
        new MyThread(handler,jsonObject,action).start();
    }

    /**
     * 查询入库条码
     */
    public static void querySingle(MyHandler handler, JSONObject itemObject){
        JSONObject jsonObject = new JSONObject();//发送给服务器的数据
        jsonObject.put("module", app_handle_module);
        jsonObject.put("operation", "OLDBarCodeQuery");//查询入库条码
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
        jsonObject.put("module", app_handle_module);
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
    public static String addData(String barCode, int xiangShu, int jianShu){
        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("barCode",barCode);
//        jsonObject.put("xiangShu",xiangShu);
//        jsonObject.put("jianShu",jianShu);
        jsonObject.put("inboundBarCode",barCode);
        jsonObject.put("inventoryBoxAmount",xiangShu);
        jsonObject.put("inventorySparePartAmount",jianShu);
        return jsonObject.toString();
    }
}
