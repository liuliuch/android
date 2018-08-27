package com.example.macbookpro.easyweather;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.macbookpro.easyweather.model.GoodsBean;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GoodsListActivity extends AppCompatActivity {
    private OkHttpClient client = new OkHttpClient();
    private ListView listView;
    private Handler handler;

    private List<GoodsBean.Second.Third> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_goods_list);

        ImageView img1= (ImageView) findViewById(R.id.index_search);
        ImageView mImggohome = this.<ImageView>findViewById(R.id.go_home);

        img1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GoodsListActivity.this,HotSearchActivity.class);
                startActivity(intent);

            }

        });

        mImggohome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GoodsListActivity.this,IndexActivity.class);
                startActivity(intent);

            }

        });


        listView = (ListView) findViewById(R.id.list_goods);
        if (!Utils.isNetworkAvailable(GoodsListActivity.this)) {
            Toast.makeText(GoodsListActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
        }
        loadData();
        handler = new MyHandler();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(GoodsListActivity.this, GoodsListActivity.class);
                String goods_pic = GoodsListBaseAdapter.list.get(position).goods_pic;
                intent.putExtra("url", goods_pic);
                startActivity(intent);
                Log.i("Simon", goods_pic);
            }
        });
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.i(">>>>>>>", Thread.currentThread().getName());
            list = (List<GoodsBean.Second.Third>) msg.obj;
            listView.setAdapter(new GoodsListBaseAdapter(list, GoodsListActivity.this));
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
                        //在主线程更新UI
//                                runOnUiThread(new Runnable() {
//                                        @Override
//                                         public void run() {
//                                       listView.setAdapter(new NewsListBaseAdapter(list,MainActivity.this));
//                                    }
//                                });
                        Message msg = handler.obtainMessage();
                        msg.obj = list;
                        handler.sendMessage(msg);//sendMessage()方法，在主线程或者Worker Thread线程中发送，都是可以的，都可以被取到
//                            listView.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    listView.setAdapter(new NewsListBaseAdapter(list,MainActivity.this));
//                                }
//                            });
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
