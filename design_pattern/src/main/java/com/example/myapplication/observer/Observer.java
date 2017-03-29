package com.example.myapplication.observer;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by wangzhu on 2017/3/29.
 * email:zhu.wang@utsoft.cn
 * func:
 */

public class Observer implements Iobserver {
    private String name;
    private Context context;

    public Observer(String name, Context context) {
        this.name = name;
        this.context = context;
    }

    @Override
    public void upadte(String info) {
        Toast.makeText(context, this.name + "得到消息  " + info, Toast.LENGTH_SHORT).show();
    }

}
