package com.hasee.rbwapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hasee.rbwapplication.R;
import com.hasee.rbwapplication.bean.GoodInfo;

import java.util.List;

/**
 * Created by fangju on 2018/11/23
 */
public class GoodListViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<GoodInfo> goodInfoList;

    public GoodListViewAdapter(Context mContext,List<GoodInfo> goodInfoList){
        this.mContext = mContext;
        this.goodInfoList = goodInfoList;
    }

    @Override
    public int getCount() {
        return goodInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return goodInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        ViewHolder viewHolder = null;
        if(convertView == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.goodlistview_item,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.itemLayout = (LinearLayout)view.findViewById(R.id.item_layout);
            viewHolder.barCodeTv = (TextView)view.findViewById(R.id.good_listView_item_barCode_tv);
            viewHolder.xiangShuTv = (TextView)view.findViewById(R.id.good_listView_item_xiangShu_tv);
            viewHolder.jianShuTv = (TextView)view.findViewById(R.id.good_listView_item_jianShu_tv);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        if(position%2 ==0){
            viewHolder.itemLayout.setBackgroundResource(R.color.oddNumber_bg);
        }else{
            viewHolder.itemLayout.setBackgroundResource(R.color.evenNumber_bg);
        }
        viewHolder.barCodeTv.setText(goodInfoList.get(position).getBarCode());
        viewHolder.xiangShuTv.setText(goodInfoList.get(position).getXiangShu());
        viewHolder.jianShuTv.setText(goodInfoList.get(position).getJianShu());
        return view;
    }

    class ViewHolder{
        LinearLayout itemLayout;
        TextView barCodeTv;//条码
        TextView xiangShuTv;//箱数
        TextView jianShuTv;//件数
    }

    /**
     * 更新子项
     * @param view
     * @param itemIndex
     */
    public void updateView(View view, int itemIndex) {
        if(view == null){
            return ;
        }
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.barCodeTv = (TextView)view.findViewById(R.id.good_listView_item_barCode_tv);
        viewHolder.barCodeTv.setText(goodInfoList.get(itemIndex).getBarCode());
        viewHolder.xiangShuTv = (TextView)view.findViewById(R.id.good_listView_item_xiangShu_tv);
        viewHolder.xiangShuTv.setText(goodInfoList.get(itemIndex).getXiangShu());
        viewHolder.jianShuTv = (TextView)view.findViewById(R.id.good_listView_item_jianShu_tv);
        viewHolder.jianShuTv.setText(goodInfoList.get(itemIndex).getJianShu());
    }

}
