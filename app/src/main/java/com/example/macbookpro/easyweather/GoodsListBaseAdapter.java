package com.example.macbookpro.easyweather;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.macbookpro.easyweather.model.GoodsBean;


import java.util.List;

import static com.example.macbookpro.easyweather.Arith.sub;

/**
 * Created by zhangcong on 2017/4/25.
 */

//adapter 适配数据
public class GoodsListBaseAdapter extends BaseAdapter {
    public static List<GoodsBean.Second.Third> list;
    private Context context;
    public GoodsListBaseAdapter(List<GoodsBean.Second.Third> list, Context context)
    {
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
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
        String imgUrl;
        if (convertView==null)
        {
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.goods_item,null,false);
            viewHolder.imageView= (ImageView) convertView.findViewById(R.id.goodImg);
            viewHolder.title= (TextView) convertView.findViewById(R.id.textTitle);
            viewHolder.goods_price= (TextView) convertView.findViewById(R.id.priceOld);
            viewHolder.coupon_over = convertView.findViewById(R.id.saleNum);
            viewHolder.coupon_price=convertView.findViewById(R.id.couponPrice);
            viewHolder.discount_price=convertView.findViewById(R.id.discountPrice);
//            viewHolder.time= (TextView) convertView.findViewById(R.id.tv_news_list_time);
            convertView.setTag(viewHolder);
        }
        viewHolder= (ViewHolder) convertView.getTag();
        imgUrl = list.get(position).goods_pic;
        if (imgUrl.startsWith("/"))
            {
            imgUrl="http:"+imgUrl;
            }
        Glide.with(context).load(imgUrl).into(viewHolder.imageView);//Glide 加载图片
        viewHolder.title.setText(list.get(position).goods_title);
 //       viewHolder.time.setText(list.get(position).date);
        viewHolder.goods_price.setText("¥"+list.get(position).goods_price);
        viewHolder.goods_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
        viewHolder.coupon_over.setText("已领："+list.get(position).coupon_over);
        viewHolder.coupon_price.setText("¥"+list.get(position).coupon_price);
        viewHolder.discount_price.setText("¥"+sub(list.get(position).goods_price,list.get(position).coupon_price));
        return convertView;
    }
    class ViewHolder
    {
        private ImageView imageView;
        private TextView title;
        private TextView goods_price;
        private TextView coupon_over;
        private TextView coupon_price;
        private TextView discount_price;

//        private TextView time;
    }
}
