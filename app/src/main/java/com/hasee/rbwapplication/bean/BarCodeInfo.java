package com.hasee.rbwapplication.bean;

/**
 * Created by fangju on 2018/11/23
 * 条码相关信息
 */
public class BarCodeInfo {
    private String gysName;//供应商
    private String bujianName;//部件名称
    private String jianNumber;//件号
    private String piciName;//批次
    private int pancunNumber;//盘存量
    private int systemNumber;//系统

    public String getGysName() {
        return gysName;
    }

    public void setGysName(String gysName) {
        this.gysName = gysName;
    }

    public String getBujianName() {
        return bujianName;
    }

    public void setBujianName(String bujianName) {
        this.bujianName = bujianName;
    }

    public String getJianNumber() {
        return jianNumber;
    }

    public void setJianNumber(String jianNumber) {
        this.jianNumber = jianNumber;
    }

    public String getPiciName() {
        return piciName;
    }

    public void setPiciName(String piciName) {
        this.piciName = piciName;
    }

    public int getPancunNumber() {
        return pancunNumber;
    }

    public void setPancunNumber(int pancunNumber) {
        this.pancunNumber = pancunNumber;
    }

    public int getSystemNumber() {
        return systemNumber;
    }

    public void setSystemNumber(int systemNumber) {
        this.systemNumber = systemNumber;
    }
}
