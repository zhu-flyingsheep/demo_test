package com.example.myapplication.states;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by wangzhu on 2017/3/29.
 * email:zhu.wang@utsoft.cn
 * func:
 */

public class Single implements Istate {
    public Context context;

    public Single(Context context) {
        this.context = context;
    }

    @Override
    public void looking_for_girlfriend() {
        Toast.makeText(context, "单身可以找对象啊", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void fuck_with_girlfriend() {
        Toast.makeText(context, "单身只能自己撸啊撸", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void eating_samethins() {
        Toast.makeText(context, "单身狗吃饭日常", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void is_alone() {
        Toast.makeText(context, "不是一个人，是单身狗", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void start() {
        eating_samethins();
        is_alone();
        looking_for_girlfriend();
        fuck_with_girlfriend();
    }
}
