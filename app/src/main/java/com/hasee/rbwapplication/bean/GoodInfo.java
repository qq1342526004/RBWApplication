package com.hasee.rbwapplication.bean;

import java.io.Serializable;

/**
 * Created by fangju on 2018/11/23
 * 商品信息
 */
public class GoodInfo implements Serializable {
    private String inboundBarCode;//条码
    private int inventoryBoxAmount;//箱数
    private int inventorySparePartAmount;//件数
    private String inventoryStaffer;//盘点人
    private String wareHouseInventoryInfoRemark;//盘点信息备注

    public String getInboundBarCode() {
        return inboundBarCode;
    }

    public void setInboundBarCode(String inboundBarCode) {
        this.inboundBarCode = inboundBarCode;
    }

    public int getInventoryBoxAmount() {
        return inventoryBoxAmount;
    }

    public void setInventoryBoxAmount(int inventoryBoxAmount) {
        this.inventoryBoxAmount = inventoryBoxAmount;
    }

    public int getInventorySparePartAmount() {
        return inventorySparePartAmount;
    }

    public void setInventorySparePartAmount(int inventorySparePartAmount) {
        this.inventorySparePartAmount = inventorySparePartAmount;
    }

    public String getInventoryStaffer() {
        return inventoryStaffer;
    }

    public void setInventoryStaffer(String inventoryStaffer) {
        this.inventoryStaffer = inventoryStaffer;
    }

    public String getWareHouseInventoryInfoRemark() {
        return wareHouseInventoryInfoRemark;
    }

    public void setWareHouseInventoryInfoRemark(String wareHouseInventoryInfoRemark) {
        this.wareHouseInventoryInfoRemark = wareHouseInventoryInfoRemark;
    }
}
