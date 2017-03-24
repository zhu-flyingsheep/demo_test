package com.utsoft.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.chad.baserecyclerviewadapterhelper.HomeActivity;
import com.chad.baserecyclerviewadapterhelper.WelcomeActivity;
import com.rxjava2.android.samples.ui.networking.NetworkingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangzhu on 2017/3/3.
 * func: 三房库测试入口
 */

public class MainActivity extends Activity {
    @BindView(R.id.btn_logger)//不建议使用注解，其实并不能省很多代码
            Button btn_logger;

    private Button tbn_realm;
    private Button btn_rxandroid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity_ut);
        ButterKnife.bind(this);//setContentView 方法之后

        this.tbn_realm = (Button) findViewById(R.id.tbn_realm);
        this.tbn_realm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "普通点击事件", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, RealmActivity.class);
                startActivity(intent);
            }
        });
        this.findViewById(R.id.btn_rxandroid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RxjavaActivity.class);
                startActivity(intent);
            }
        });
        this.findViewById(R.id.btn_retrofit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RetrofitActivity.class));
            }
        });
        this.findViewById(R.id.btn_rx_retrofit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NetworkingActivity.class));
            }
        });
        this.findViewById(R.id.btn_ry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
            }
        });
        this.findViewById(R.id.btn_tinker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TinkerActivity.class));
            }
        });
    }

    @OnClick(R.id.btn_logger)
    public void goto_logger() {
        Toast.makeText(this, "注解点击事件", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, LoggerActivity.class);
        startActivity(intent);
    }


}
