package com.hasee.rbwapplication.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.hasee.rbwapplication.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class TableAdapter extends BaseAdapter{
    private Context mContext;
    private List<TableRow> tabelRows;//总共的数据
    private List<TableRow> filterTabelRows;//过滤的数据
    private List<TableRow> myTableRows;//传递给Adapter的数据
    private MyFilter myFilter;
    private int keyIndex = 0;//过滤索引位

    public TableAdapter(Context context, List<TableRow> tabelRows) {
        this.mContext = context;
        this.tabelRows = tabelRows;//总数据
        filterTabelRows = new ArrayList<>();//过滤的数据
        this.myTableRows = new ArrayList<>();//传递给Adapter的数据
        myTableRows.addAll(this.tabelRows);
    }

    @Override
    public int getCount() {
        return myTableRows.size();
    }

    @Override
    public Object getItem(int i) {
        return myTableRows.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TableRow tabelRow = myTableRows.get(i);
        TableRowView tabelRowView = new TableRowView(mContext, tabelRow, i);
        return tabelRowView;
    }

    public MyFilter getFilter() {
        if(myFilter == null){
            myFilter = new MyFilter();
        }
        return myFilter;
    }


    /*
     * 实现表格行的样式
     * */
    class TableRowView extends LinearLayout {

        public TableRowView(Context context, TableRow tabelRow, int position) {
            super(context);
            this.setOrientation(LinearLayout.HORIZONTAL);//单元格的布局方向
            for (int i = 0; i < tabelRow.getSize(); i++) {//逐个将单元格添加到行
                TableCell cell = tabelRow.getCell(i);
                LayoutParams layoutParams = new LayoutParams(cell.width, cell.height);//按照单元格指定大小设置空间
//                layoutParams.setMargins(0, 0, 1, 1);//预留空隙制造边框
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

        public TableCell getCell(int index) {//返回一行中某个具体单元格

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

    /**
     * 过滤器
     */
    public class MyFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            filterTabelRows.clear();
            Iterator<TableRow> iterator = tabelRows.iterator();//从总数据中过滤数据
            while(iterator.hasNext()){
                TableRow tableRow = iterator.next();
                if(tableRow.getCell(keyIndex).value.contains(constraint)){
                    filterTabelRows.add(tableRow);
                }
            }
            results.values = filterTabelRows;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            myTableRows = (List<TableRow>) results.values;
            if(myTableRows.size() > 0){
                notifyDataSetChanged();
            }else{
                notifyDataSetInvalidated();
            }
        }
    }
}
