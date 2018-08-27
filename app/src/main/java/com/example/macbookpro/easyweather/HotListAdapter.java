package com.example.macbookpro.easyweather;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.view.LayoutInflater;
import android.widget.TextView;


import com.example.macbookpro.easyweather.model.HotSearchBean;

import java.util.List;

public class HotListAdapter extends BaseAdapter {
    public List<HotSearchBean.Response>  list;

    private Context context;
    public HotListAdapter(List<HotSearchBean.Response> list, Context context)
    {
        this.context=context;
        this.list=list;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder=null;
            // 若无可重用的 view 则进行加载
            if(convertView == null){
                viewHolder = new ViewHolder();

                convertView= LayoutInflater.from(context).inflate(R.layout.activity_hot_search,null,false);
                // 初始化 ViewHolder 方便重用

                //viewHolder.hotword = (TextView) convertView.findViewById(R.id.flow);

                convertView.setTag(viewHolder);
            }else{ // 否则进行重用
                viewHolder = (ViewHolder)convertView.getTag();
            }
            // 设置内容(Note Bean 需要自定义)
            viewHolder.hotword.setText(list.get(position).word);

            return convertView;


    }
    class ViewHolder{
        TextView hotword;

    }
}
