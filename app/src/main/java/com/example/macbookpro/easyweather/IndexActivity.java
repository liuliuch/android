package com.example.macbookpro.easyweather;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import android.widget.ListView;
import android.widget.Toast;

import com.example.macbookpro.easyweather.model.GoodsBean;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
//import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class IndexActivity extends AppCompatActivity {
    private XRecyclerView mRecyclerView;
    private OkHttpClient client = new OkHttpClient();

    private Handler handler;
    private List<GoodsBean.Second.Third> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        mRecyclerView = findViewById(R.id.Recy_view);

        mRecyclerView.setPullRefreshEnabled(true);
        mRecyclerView.setLoadingMoreEnabled(true);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.SysProgress);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SysProgress);
        mRecyclerView.getDefaultFootView().setLoadingHint("接收中");
        mRecyclerView.getDefaultFootView().setNoMoreHint("接收完成");
        mRecyclerView.getDefaultRefreshHeaderView() // get default refresh header view
                .setRefreshTimeVisible(true);  // make refresh time visible,false means hiding


        if (!Utils.isNetworkAvailable(IndexActivity.this)) {
            Toast.makeText(IndexActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {

            @Override
            public void onRefresh() {
                loadData();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.refreshComplete();
                    }
                }, 2000);



               // mRecyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.refreshComplete();
                    }
                }, 2000);

            }
        });

        loadData();
        handler = new IndexActivity.MyHandler();


    }


    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.i(">>>>>>>", Thread.currentThread().getName());
            list = (List<GoodsBean.Second.Third>) msg.obj;
            mRecyclerView.setAdapter(new RecyclerviewAdapter(list, IndexActivity.this));
        }
    }


    private void loadData() {
        final Gson gson = new Gson();
        final Request request = new Request.Builder()
                .get()
                .url("http://openapi.qingtaoke.com/qingsoulist?sort=1&page=1&app_key=NCtx8w85&v=1.0&cat=0&min_price=1&max_price=100&new=0&is_ju=1&is_tqg=0")
                .build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response;
                try {
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String content = response.body().string();
                        GoodsBean goodsBean = gson.fromJson(content, GoodsBean.class);

                        final int reason = goodsBean.er_code;
                        final String er_msg = goodsBean.er_msg;

                        GoodsBean.Second second = goodsBean.data;
                        final List<GoodsBean.Second.Third> list = second.list;

                        Message msg = handler.obtainMessage();
                        msg.obj = list;
                        handler.sendMessage(msg);//sendMessage()方法，在主线程或者Worker Thread线程中发送，都是可以的，都可以被取到
//
                        String title = list.get(0).goods_title;
                        Log.i(">>>>>>>>>>>>>>>>>>", title);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}
