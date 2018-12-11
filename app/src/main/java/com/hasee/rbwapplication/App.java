package com.hasee.rbwapplication;

import android.app.Application;
import android.content.SharedPreferences;

/**
 * Created by fangju on 2018/11/23
 */
public class App extends Application {
    private static App instance;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        preferences = getSharedPreferences("APPInfo",MODE_PRIVATE);
        editor = getSharedPreferences("APPInfo",MODE_PRIVATE).edit();
    }

    public String[] getPreferences() {
        String ipAddress = preferences.getString("ipAddress","");
        String userName = preferences.getString("userName","");
        String passWord = preferences.getString("passWord","");
        String[] strings = new String[]{ipAddress,userName,passWord};
        return strings;
    }

    public void setPreferences(String ipAddress,String userName,String passWord){
        editor.putString("ipAddress",ipAddress);
        editor.putString("userName",userName);
        editor.putString("passWord",passWord);
        editor.apply();
    }

}
