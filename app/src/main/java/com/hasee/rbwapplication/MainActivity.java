package com.hasee.rbwapplication;

import android.content.Context;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hasee.rbwapplication.adapter.GoodListViewAdapter;
import com.hasee.rbwapplication.bean.GoodInfo;
import com.hasee.rbwapplication.dialog.InputDialog;
import com.hasee.rbwapplication.dialog.UpdateDialog;
import com.hasee.rbwapplication.util.HandlerData;
import com.hasee.rbwapplication.util.LogUtil;
import com.hasee.rbwapplication.util.MyHandler;
import com.hasee.rbwapplication.util.MyThread;
import com.hasee.rbwapplication.util.ToastUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyListener{
    private static final String TAG = "MainActivity";
    private Context mContext;//MainActivity上下文
    private TextView saveButton;//保存按钮
    private TextView chayibiaoButton;//差异表按钮
    private EditText editText;//输入框
    private ListView listView;//显示列表
    private List<GoodInfo> goodInfoList = new ArrayList<>();//存放商品信息的List
    private GoodListViewAdapter goodListViewAdapter;//商品ListView适配器
    private InputDialog inputDialog;//输入Dialog
    private UpdateDialog updateDialog;//修改Dialog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
        editText = (EditText)findViewById(R.id.mainactivity_et);
        editText.setOnEditorActionListener(onEditorActionListener);
        saveButton = (TextView)findViewById(R.id.mainactivity_save_button);
        saveButton.setOnClickListener(onClickListener);
        chayibiaoButton = (TextView)findViewById(R.id.mainactivity_chayibiao_button);
        chayibiaoButton.setOnClickListener(onClickListener);
        listView = (ListView)findViewById(R.id.mainactivity_listView);
        goodListViewAdapter = new GoodListViewAdapter(MainActivity.this,goodInfoList);
        listView.setAdapter(goodListViewAdapter);
        listView.setOnItemClickListener(onItemClickListener);
    }

    /**
     * 按钮点击事件监听器
     */
    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.mainactivity_save_button://保存按钮
                    if(goodInfoList.size() > 0){
                        JSONArray jsonArray = new JSONArray();
                        String action = "action.do";
                        for (int i = 0; i < goodInfoList.size(); i++) {
                            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(goodInfoList.get(i));
                            jsonArray.add(jsonObject);
                        }
                        HandlerData.send(handler,jsonArray);
//                        LogUtil.d(TAG,jsonArray.toString());
//                        new MyThread(handler, HandlerData.send(jsonArray), action).start();
                    }
                    break;
                case R.id.mainactivity_chayibiao_button://差异表按钮
                    break;
            }
        }
    };
    /**
     * ListView点击事件监听器
     */
    public AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("Gooditem", goodInfoList.get(position));
            updateDialog = new UpdateDialog();
            updateDialog.setArguments(bundle);
            updateDialog.show(getSupportFragmentManager(), "update_dialog");
        }
    };

    /**
     * 输入框回车监听
     */
    private TextView.OnEditorActionListener onEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                String barCodeTxt = editText.getText().toString().trim();
                if(!TextUtils.isEmpty(barCodeTxt)){
                    Bundle bundle = new Bundle();
                    bundle.putString("barCodeTxt",barCodeTxt);
                    inputDialog = new InputDialog();
                    inputDialog.setCancelable(false);
                    inputDialog.setArguments(bundle);
                    inputDialog.show(getSupportFragmentManager(), "input_dialog");
//                ToastUtil.getInstance(mContext).showShortToast(barCodeTxt);
                    editText.setText("");
                }
            }
            return false;
        }
    };

    @Override
    public void sendMessage(String message) {
        JSONArray jsonArray = JSONArray.parseArray(message);
        switch (jsonArray.getIntValue(0)){
            case 0: {//返回
                if (inputDialog != null && inputDialog.isVisible()) {
                    inputDialog.dismiss();
                    inputDialog = null;
                }
                if (updateDialog != null && updateDialog.isVisible()) {
                    updateDialog.dismiss();
                    updateDialog = null;
                }
                break;
            }
            case 1: {//确定输入
                String goodItem = jsonArray.getString(1);
                GoodInfo goodInfo = JSONObject.parseObject(goodItem, GoodInfo.class);
                goodInfoList.add(goodInfo);
                goodListViewAdapter.notifyDataSetChanged();
                inputDialog.dismiss();
                break;
            }
            case 2: {//修改
                LogUtil.d(TAG,jsonArray.getJSONObject(1).toString());
                new MyThread(handler, jsonArray.getJSONObject(1), "action.do").start();
                updateDialog.dismiss();
                break;
            }
        }
    }

    /**
     * 处理服务器返回的数据
     */
    public MyHandler handler = new MyHandler(mContext){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){

            }
        }
    };
}
