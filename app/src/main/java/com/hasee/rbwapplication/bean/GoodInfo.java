package com.hasee.rbwapplication.bean;

import java.io.Serializable;

/**
 * Created by fangju on 2018/11/23
 * 商品信息
 */
public class GoodInfo implements Serializable {
    private String barCode;//条码
    private String xiangShu;//箱数
    private String jianShu;//件数

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getXiangShu() {
        return xiangShu;
    }

    public void setXiangShu(String xiangShu) {
        this.xiangShu = xiangShu;
    }

    public String getJianShu() {
        return jianShu;
    }

    public void setJianShu(String jianShu) {
        this.jianShu = jianShu;
    }
}
