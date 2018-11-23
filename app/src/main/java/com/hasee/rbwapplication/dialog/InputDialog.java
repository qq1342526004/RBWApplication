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
import com.hasee.rbwapplication.MyListener;
import com.hasee.rbwapplication.R;
import com.hasee.rbwapplication.util.HandlerData;


public class InputDialog extends DialogFragment {
     private TextView backTextView,submitTextView;
     private EditText barCodeEt, xiangShuEt, jianShuEt;
     private MyListener listener;
     private JSONArray jsonArray;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (MyListener) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.inputdialog_layout,container,false);
        savedInstanceState = this.getArguments();
        String barCodeTxt = savedInstanceState.getString("barCodeTxt");
        backTextView = (TextView)view.findViewById(R.id.inputDialog_back_tv);
        backTextView.setOnClickListener(onClickListener);
        submitTextView = (TextView)view.findViewById(R.id.inputDialog_submit_tv);
        submitTextView.setOnClickListener(onClickListener);
        barCodeEt = (EditText)view.findViewById(R.id.inputDialog_barCode_et);
        barCodeEt.setText(barCodeTxt);
        xiangShuEt = (EditText)view.findViewById(R.id.inputDialog_xiangShu_et);
        jianShuEt = (EditText)view.findViewById(R.id.inputDialog_jianShu_et);
        return view;
    }

    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.inputDialog_back_tv://返回
                    jsonArray = new JSONArray();
                    jsonArray.add(0);//返回命令
                    listener.sendMessage(jsonArray.toString());
                    break;
                case R.id.inputDialog_submit_tv://确定
                    if(isEmpty()){//输入框不为空
                        jsonArray = new JSONArray();
                        jsonArray.add(1);//输入确定命令
                        jsonArray.add(HandlerData.addData(barCodeEt.getText().toString(),
                                xiangShuEt.getText().toString(),
                                jianShuEt.getText().toString()));
                        listener.sendMessage(jsonArray.toString());
                    }
                    break;
            }
        }
    };

    private boolean isEmpty(){
        if("".equalsIgnoreCase(barCodeEt.getText().toString().trim())
                ||"".equalsIgnoreCase(xiangShuEt.getText().toString().trim())
                ||"".equalsIgnoreCase(jianShuEt.getText().toString().trim())){
            return false;
        }
        return true;
    }

}
