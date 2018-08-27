package com.example.macbookpro.easyweather.model;

import java.util.List;

public class GoodsBean {
    public int er_code;
    public String er_msg;
    public Second data;
    public  class  Second{
        public int total;
        public List<Third> list;
        public  class Third{
            public String goods_pic;    //商品图片
            public String goods_title;        //商品标题
            public  double goods_price;            //商品售价
            public double commission ;       //佣金
            public double coupon_price;      //优惠券价格
            public int  coupon_number;      //剩余优惠券数量
            public int  coupon_over;     // 已领券数量

        }
    }
}
