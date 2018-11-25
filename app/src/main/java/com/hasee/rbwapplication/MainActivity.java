package com.hasee.rbwapplication;

import android.content.Context;
import android.content.Intent;
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
import com.hasee.rbwapplication.bean.ResponseInfo;
import com.hasee.rbwapplication.dialog.InputDialog;
import com.hasee.rbwapplication.dialog.UpdateDialog;
import com.hasee.rbwapplication.util.ActivityCollector;
import com.hasee.rbwapplication.util.HandlerData;
import com.hasee.rbwapplication.util.LogUtil;
import com.hasee.rbwapplication.util.MyHandler;
import com.hasee.rbwapplication.util.MyThread;
import com.hasee.rbwapplication.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements MyListener {
    private static final String TAG = "MainActivity";
    private Context mContext;//上下文
    private TextView saveButton;//保存按钮
    private TextView chayibiaoButton;//差异表按钮
    private EditText editText;//输入框
    private ListView listView;//显示列表
    private List<GoodInfo> goodInfoList = new ArrayList<>();//存放商品信息的List
    private GoodListViewAdapter goodListViewAdapter;//商品ListView适配器
    private InputDialog inputDialog;//输入Dialog
    private UpdateDialog updateDialog;//修改Dialog
    private String barCodeTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
        editText = (EditText) findViewById(R.id.mainactivity_et);
        editText.setOnEditorActionListener(onEditorActionListener);
        saveButton = (TextView) findViewById(R.id.mainactivity_save_button);
        saveButton.setOnClickListener(onClickListener);
        chayibiaoButton = (TextView) findViewById(R.id.mainactivity_chayibiao_button);
        chayibiaoButton.setOnClickListener(onClickListener);
        listView = (ListView) findViewById(R.id.mainactivity_listView);
        goodListViewAdapter = new GoodListViewAdapter(MainActivity.this, goodInfoList);
        listView.setAdapter(goodListViewAdapter);
        listView.setOnItemClickListener(onItemClickListener);
    }

    /**
     * 返回键监听
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityCollector.finshAll();
    }

    /**
     * 按钮点击事件监听器
     */
    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.mainactivity_save_button://保存按钮
                    if (goodInfoList.size() > 0) {
                        JSONArray jsonArray = new JSONArray();
                        for (int i = 0; i < goodInfoList.size(); i++) {
                            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(goodInfoList.get(i));
                            jsonArray.add(jsonObject);
                        }
                        HandlerData.send(handler, jsonArray);
                    }
                    break;
                case R.id.mainactivity_chayibiao_button://差异表按钮
                    Intent intent = new Intent(MainActivity.this, DifferenceActivity.class);
                    startActivity(intent);
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
            bundle.putInt("currentPosition",position);
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
                barCodeTxt = editText.getText().toString().trim();
                if(!TextUtils.isEmpty(barCodeTxt)){
                    JSONObject itemObject = new JSONObject();
                    if(!TextUtils.isEmpty(barCodeTxt)){
                        itemObject.put("barCode",barCodeTxt);
                    }
                    HandlerData.querySingle(handler,itemObject);
                }
            }
            return false;
        }
    };

    /**
     * 接收DialogFragment发送的数据
     * @param message
     */
    @Override
    public void sendMessage(String message) {
        JSONArray jsonArray = JSONArray.parseArray(message);
        switch (jsonArray.getIntValue(0)) {
            case 0: {//返回
                if (inputDialog != null && inputDialog.isVisible()) {
                    inputDialog.dismiss();
                    inputDialog = null;
                }
                if (updateDialog != null && updateDialog.isVisible()) {
                    updateDialog.dismiss();
                    updateDialog = null;
                }
                editText.requestFocus();
                editText.findFocus();
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
                GoodInfo goodInfo = JSONObject.parseObject(jsonArray.getJSONObject(1).toJSONString(),
                        GoodInfo.class);
                int currentPosition = jsonArray.getInteger(2);
                goodInfoList.set(currentPosition,goodInfo);
                updateItemView(currentPosition);
                updateDialog.dismiss();
                break;
            }
        }
    }

    /**
     * 更新子控件
     * @param itemIndex
     */
    public void updateItemView(int itemIndex){
        //得到第一个可显示控件的位置
        int visiblePosition = listView.getFirstVisiblePosition();
        //只有当要更新的view在可见的位置时才更新，不可见时，跳过不更新
        if (itemIndex - visiblePosition >= 0) {
            //得到要更新的item的view
            View view = listView.getChildAt(itemIndex - visiblePosition);
            //调用adapter更新界面
            goodListViewAdapter.updateView(view, itemIndex);
        }
    }

    /**
     * 处理服务器返回的数据
     */
    public MyHandler handler = new MyHandler(MainActivity.this) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ResponseInfo responseInfo = (ResponseInfo) msg.obj;
//            LogUtil.d("TAG",responseInfo.getStatus()+":"+responseInfo.getMessage()+":"+responseInfo.getData());
            switch (msg.what) {
                case MyThread.RESPONSE_SUCCESS: {//操作成功
                    if(getResources().getString(R.string.data_isempty)
                            .equals(responseInfo.getMessage())){//数据为空!
                        Bundle bundle = new Bundle();
                        bundle.putString("barCodeTxt", barCodeTxt);
                        inputDialog = new InputDialog();
                        inputDialog.setCancelable(false);
                        inputDialog.setArguments(bundle);
                        inputDialog.show(getSupportFragmentManager(), "input_dialog");
                        editText.setText("");
                    }else{//数据发送成功
                        goodInfoList.clear();
                        goodListViewAdapter.notifyDataSetChanged();
                        ToastUtil.getInstance(MainActivity.this).showShortToast(
                                getResources().getString(R.string.handle_success));
                    }
                    break;
                }
                case MyThread.RESPONSE_FAILED:{//操作失败
                    editText.setText("");
                    ToastUtil.getInstance(mContext).showShortToast(responseInfo.getMessage());
                }
            }
        }
    };
}
