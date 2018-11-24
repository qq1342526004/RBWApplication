package com.hasee.rbwapplication.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.hasee.rbwapplication.R;

import java.util.List;


public class TableAdapter extends BaseAdapter {
    private Context mContext;
    private List<TableRow> tabelRows;

    public TableAdapter(Context context, List<TableRow> tabelRows) {
        this.mContext = context;
        this.tabelRows = tabelRows;
    }

    @Override
    public int getCount() {
        return tabelRows.size();
    }

    @Override
    public Object getItem(int i) {
        return tabelRows.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TableRow tabelRow = tabelRows.get(i);
        TableRowView tabelRowView = new TableRowView(mContext, tabelRow, i);
        return tabelRowView;
    }


    /*
     * 实现表格行的样式
     * */
    class TableRowView extends LinearLayout {

        public TableRowView(Context context, TableRow tabelRow, int position) {
            super(context);
            this.setOrientation(LinearLayout.HORIZONTAL);//单元格的布局方向
            for (int i = 0; i < tabelRow.getSize(); i++) {//逐个将单元格添加到行
                TableCell cell = tabelRow.getCellValue(i);
                LayoutParams layoutParams = new LayoutParams(cell.width, cell.height);//按照单元格指定大小设置空间
                layoutParams.setMargins(0, 0, 1, 1);//预留空隙制造边框
                //设置单元格中的数据以及排版
                TextView cellTextView = new TextView(context);
                cellTextView.setLines(1);
                cellTextView.setGravity(Gravity.CENTER);
                cellTextView.setText(cell.value);
                if(position%2 ==0){
                    cellTextView.setBackgroundResource(R.color.oddNumber_bg);
                }else{
                    cellTextView.setBackgroundResource(R.color.evenNumber_bg);
                }
                addView(cellTextView, layoutParams);
            }
        }
    }

    /*
     * 实现表格的行单元
     * */
    public static class TableRow {
        private TableCell[] cells;

        public TableRow(TableCell[] cells) {
            this.cells = cells;
        }

        public int getSize() {//获取一行中单元格的个数
            return cells.length;
        }

        public TableCell getCellValue(int index) {//返回一行中某个具体单元格

            if (index >= cells.length) {
                return null;
            }
            return cells[index];
        }

    }


    /*
     * 实现表格的格单元
     * */
    public static class TableCell {
        private String value;//单元格中的值
        private int width;//单元格的宽度
        private int height;//单元格的高度

        public TableCell(String value, int width, int height) {
            this.value = value;
            this.width = width;
            this.height = height;
        }

        public String getValue() {
            return value;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }
}
