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
        preferences = getSharedPreferences("IpAddress",MODE_PRIVATE);
        editor = getSharedPreferences("IpAddress",MODE_PRIVATE).edit();
    }

    public String getPreferences() {
        String ipAddress = preferences.getString("IpAddress","");
        return ipAddress;
    }

    public void setPreferences(String ipAddress){
        editor.putString("IpAddress",ipAddress);
        editor.apply();
    }

}
