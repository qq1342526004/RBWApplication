package com.hasee.rbwapplication.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hasee.rbwapplication.MyListener;
import com.hasee.rbwapplication.R;
import com.hasee.rbwapplication.bean.GoodInfo;
import com.hasee.rbwapplication.util.HandlerData;


public class UpdateDialog extends DialogFragment {
    private TextView backTextView,submitTextView;
    private EditText barCodeEt, xiangShuEt, jianShuEt;
    private MyListener listener;
    private JSONArray jsonArray;
    private GoodInfo preGoodInfo;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (MyListener)getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.updatedialog_layout,container,false);
        savedInstanceState = this.getArguments();
        preGoodInfo = (GoodInfo) savedInstanceState.getSerializable("Gooditem");
        barCodeEt = (EditText)view.findViewById(R.id.updateDialog_barCode_et);
        xiangShuEt = (EditText)view.findViewById(R.id.updateDialog_xiangShu_et);
        jianShuEt = (EditText)view.findViewById(R.id.updateDialog_jianShu_et);
        backTextView = (TextView)view.findViewById(R.id.updateDialog_back_tv);
        backTextView.setOnClickListener(onClickListener);
        submitTextView = (TextView)view.findViewById(R.id.updateDialog_submit_tv);
        submitTextView.setOnClickListener(onClickListener);
        barCodeEt.setText(preGoodInfo.getBarCode());
        xiangShuEt.setHint(preGoodInfo.getXiangShu());
        jianShuEt.setHint(preGoodInfo.getJianShu());
        return view;
    }

    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.updateDialog_back_tv:
                    jsonArray = new JSONArray();
                    jsonArray.add(0);//返回命令
                    listener.sendMessage(jsonArray.toString());
                    break;
                case R.id.updateDialog_submit_tv:
                    if(isEmpty()){//输入框不为空
                        JSONArray itemJSONArray = new JSONArray();
                        JSONObject itemObject = new JSONObject();//修改后的数据（set）
                        itemObject.put("barCode", barCodeEt.getText().toString().trim());
                        itemObject.put("xiangShu", xiangShuEt.getText().toString().trim());
                        itemObject.put("jianShu", jianShuEt.getText().toString().trim());
                        itemJSONArray.add(itemObject);//修改后的数据
                        itemJSONArray.add(JSONObject.toJSON(preGoodInfo));//原有的数据
                        jsonArray = new JSONArray();
                        jsonArray.add(2);
                        jsonArray.add(HandlerData.updateData(itemJSONArray));
                        listener.sendMessage(jsonArray.toString());
                    }
                    break;
            }
        }
    };

    private boolean isEmpty(){
        if("".equalsIgnoreCase(barCodeEt.getText().toString().trim())
                ||"".equalsIgnoreCase(xiangShuEt.getText().toString().trim())){
            return false;
        }
        return true;
    }
}
