package com.hasee.rbwapplication;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hasee.rbwapplication.adapter.TableAdapter;
import com.hasee.rbwapplication.bean.ResponseInfo;
import com.hasee.rbwapplication.util.HandlerData;
import com.hasee.rbwapplication.util.MyHandler;
import com.hasee.rbwapplication.util.MyThread;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by fangju on 2018/11/24
 */
public class DifferenceActivity extends AppCompatActivity {
    private final int MAX_NUMBER = 20;//一页中的最大值
    private Context mContext;//上下文
    private ImageButton backButton;//返回按钮
    private EditText editText;//输入框
    private TextView submitButton;//确认按钮
    private LinearLayout tabelHeadLayout;//表头布局
    private TableAdapter.TableRow tableHeadRow;//表头
    private ListView listView;
    private TableAdapter tableAdapter;
    private List<TableAdapter.TableRow> tableRowList = new ArrayList<>();
    private String selectTxt;//查询的值

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.differenceactivity_layout);
        mContext = getApplicationContext();
        backButton = (ImageButton)findViewById(R.id.differenceactivity_back_button);
        backButton.setOnClickListener(onClickListener);
        editText = (EditText)findViewById(R.id.differenceactivity_et);
        submitButton = (TextView)findViewById(R.id.differenceactivity_submit_button);
        submitButton.setOnClickListener(onClickListener);
        listView = (ListView)findViewById(R.id.differenceactivity_listView);
        tableAdapter = new TableAdapter(mContext,tableRowList);
        listView.setAdapter(tableAdapter);
    }

    /**
     * 按钮点击事件
     */
    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.differenceactivity_submit_button:{
                    selectTxt = editText.getText().toString().trim();
                    //查询所有
                    HandlerData.queryAll(handler);
                    break;
                }
                case R.id.differenceactivity_back_button:{
                    finish();
                    break;
                }
            }
        }
    };
    /**
     * 处理UI
     */
    public MyHandler handler = new MyHandler(mContext){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ResponseInfo responseInfo = (ResponseInfo) msg.obj;
            switch (msg.what){
                case MyThread.RESPONSE_SUCCESS:{
                    if(TextUtils.isEmpty(responseInfo.getMessage())){
                        String message = responseInfo.getData();
                        selectShow(message,selectTxt);//显示查询数据
                        tableAdapter.notifyDataSetChanged();
                    }
                    break;
                }
            }
        }
    };

    /**
     * 显示查询的结果
     */
    public void selectShow(String message,String selectTxt){
        JSONArray jsonArray = JSONArray.parseArray(message);
        JSONObject firstObject = jsonArray.getJSONObject(0);
        Iterator<String> iterator = firstObject.keySet().iterator();//key
        List<String> keyList = new ArrayList<>();
        while (iterator.hasNext()){
            keyList.add(iterator.next());
        }
        int colSize = keyList.size();
        int width = this.getWindowManager().getDefaultDisplay().getWidth() / colSize;//每一个TextView的宽度
        int height = this.getWindowManager().getDefaultDisplay().getHeight() / MAX_NUMBER;//每一个TextView的高度
        TableAdapter.TableCell[] tabelCellsColName = new TableAdapter.TableCell[colSize];
        for (int i = 0; i < keyList.size(); i++) {
            tabelCellsColName[i] = new TableAdapter.TableCell(keyList.get(i),width+8*i,
                    height);
        }
        tabelHeadLayout = (LinearLayout)findViewById(R.id.differenceactivity_tabelHead_layout);
        tabelHeadLayout.removeAllViews();
        tableHeadRow = new TableAdapter.TableRow(tabelCellsColName);
        //把表头加入tabelNameLayout
        for (int i = 0; i < tableHeadRow.getSize(); i++) {//逐个将单元格添加到行
            TableAdapter.TableCell cell = tableHeadRow.getCellValue(i);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(cell.getWidth(),cell.getHeight());//按照单元格指定大小设置空间
            layoutParams.setMargins(0,0,1,1);//预留空隙制造边框
            //设置单元格中的数据以及排版
            TextView cellTextView = new TextView(mContext);
            cellTextView.setLines(1);
            cellTextView.setGravity(Gravity.CENTER);
            cellTextView.setText(cell.getValue());
            cellTextView.setTextSize(20);
            cellTextView.setTextColor(Color.WHITE);
            cellTextView.setBackgroundResource(R.color.table_bg);
            tabelHeadLayout.addView(cellTextView,layoutParams);
        }
        //清空List中所有数据
        tableRowList.clear();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject rowObject = jsonArray.getJSONObject(i);//行对象
            TableAdapter.TableCell[] cellRowObject = new TableAdapter.TableCell[colSize];//一行中单元格的个数;
            for (int j = 0; j < colSize; j++) {
                cellRowObject[j] = new TableAdapter.TableCell(rowObject.getString(keyList.get(j))
                        ,width+8*j,height);
            }
            tableRowList.add(new TableAdapter.TableRow(cellRowObject));
        }
    }
}
