package com.example.macbookpro.easyweather;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.macbookpro.easyweather.model.GoodsBean;

import java.util.List;

import static com.example.macbookpro.easyweather.Arith.sub;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder> {
    private  Context mContext;
    public static List<GoodsBean.Second.Third> list;
    public RecyclerviewAdapter(List<GoodsBean.Second.Third> list,Context context){
        this.mContext=context;
        this.list=list;

    }
//    传入布局
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder;
        holder = new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.goods_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerviewAdapter.ViewHolder holder, int position) {
        String imgUrl;

        imgUrl = list.get(position).goods_pic;
        if (imgUrl.startsWith("/"))
        {
            imgUrl="http:"+imgUrl;
        }
        Glide.with(mContext).load(imgUrl).into(holder.imageView);//Glide 加载图片
        holder.title.setText(list.get(position).goods_title);
        //       viewHolder.time.setText(list.get(position).date);
        holder.goods_price.setText("¥"+list.get(position).goods_price);
        holder.goods_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
        holder.coupon_over.setText("已领："+list.get(position).coupon_over);
        holder.coupon_price.setText("¥"+list.get(position).coupon_price);
        holder.discount_price.setText("¥"+sub(list.get(position).goods_price,list.get(position).coupon_price));


        //holder.title.setText("hello world!");

    }



    @Override
    public int getItemCount() {
        return 11;
        // return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView title;
        private TextView goods_price;
        private TextView coupon_over;
        private TextView coupon_price;
        private TextView discount_price;

        public ViewHolder(View view)
        {
            super(view);
            imageView= (ImageView) view.findViewById(R.id.goodImg);
            title= (TextView) view.findViewById(R.id.textTitle);
            goods_price= (TextView) view.findViewById(R.id.priceOld);
            coupon_over = view.findViewById(R.id.saleNum);
            coupon_price=view.findViewById(R.id.couponPrice);
            discount_price=view.findViewById(R.id.discountPrice);
        }
    }
}
