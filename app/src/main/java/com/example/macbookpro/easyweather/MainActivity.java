package com.example.macbookpro.easyweather;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.macbookpro.easyweather.UtilGET.*;

import java.io.IOException;

//import okhttp3.FormEncodingBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.RequestBody;
import okhttp3.HttpUrl;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editText1;
    private Button getButton;
    private Button postButton;
    private TextView textView;

    private String requestsUrl;

    final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText1 = (EditText) findViewById(R.id.edit_1);
        getButton = (Button) findViewById(R.id.getR);
        postButton = (Button) findViewById(R.id.postR);
        textView = (TextView) findViewById(R.id.Respones);

        getButton.setOnClickListener(this);
        postButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        requestsUrl = editText1.getText().toString();
        if (requestsUrl.indexOf("http://") == -1 && requestsUrl.indexOf("https://") == -1)
            requestsUrl = "http://" + requestsUrl;

        switch (v.getId()) {
            case R.id.getR:
                getRequest(requestsUrl);
                Toast.makeText(MainActivity.this, "get请求", Toast.LENGTH_SHORT).show();
                //get请求
                break;
            case R.id.postR:
                Log.d("post", "onClick: post" + requestsUrl);
                Toast.makeText(MainActivity.this, "post请求", Toast.LENGTH_SHORT).show();
                //post请求
                break;
        }

    }

    //get
    private void getRequest(String requestsUrl) {

        final Request request = new Request.Builder()
                .get()
                .tag(this)
                .url(requestsUrl)
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String responseDate = response.body().string();
                        JsonTool jsonTool=new JsonTool();
                        responseDate = jsonTool.stringToJSON(responseDate);
//                        textView.setText(response.body().string());
                        Log.i("WY", "打印GET响应的数据：" + responseDate);
                        showResponse(responseDate);
                    } else {
                        throw new IOException("Unexpected code " + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(response);
            }
        });
    }
    // post
   /* private void postRequest() {

        RequestBody formBody = new FormEncodingBuilder()
                .add("","")
                .build();

        final Request request = new Request.Builder()
                .url("http://www.wooyun.org")
                .post(formBody)
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        Log.i("WY","打印POST响应的数据：" + response.body().string());
                    } else {
                        throw new IOException("Unexpected code " + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();*/

    //}
}
