package com.example.macbookpro.easyweather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ButtonListActivity extends AppCompatActivity{
    private Button mBtnRecyclerview, mBtnlistview, mBtnflowlayout, mBtnokhttp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_list);

        mBtnRecyclerview= this.<Button>findViewById(R.id.Recyclerview1);
        mBtnlistview= this.<Button>findViewById(R.id.Listview1);
        mBtnflowlayout= this.<Button>findViewById(R.id.hotsearch);
        mBtnokhttp= this.<Button>findViewById(R.id.get);

        mBtnRecyclerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ButtonListActivity.this, IndexActivity.class);
                startActivity(intent);
                //Toast.makeText(ButtonListActivity.this,"被点击了",Toast.LENGTH_LONG).show();
            }
        });

        mBtnokhttp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ButtonListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        mBtnflowlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ButtonListActivity.this, HotSearchActivity.class);
                startActivity(intent);
            }
        });
        mBtnlistview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ButtonListActivity.this, GoodsListActivity.class);
                startActivity(intent);
            }
        });

//    @Override
//    public void onClick(View v) {
//        Intent intent = null;
//
//        switch (v.getId()) {
//            case R.id.Recyclerview1:
//                intent = new Intent(ButtonListActivity.this,IndexActivity.class);
//                break;
//            case R.id.Listview1:
//                intent = new Intent(ButtonListActivity.this,GoodsListActivity.class);
//                break;
//            case R.id.hotsearch:
//                intent = new Intent(ButtonListActivity.this,HotSearchActivity.class);
//                break;
//            case R.id.get:
//                intent = new Intent(ButtonListActivity.this,MainActivity.class);
//                break;
//        }
//        startActivity(intent);
//    }


    }
}
